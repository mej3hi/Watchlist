package com.example.watchlist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.fragment.tvshows.TvDetailsFragment;
import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * TvShowsAdapter is used to create list of tv shows.
 */
public class TvShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<TvShow> tvShowList;
    private List<Genre> genreList;
    private Context context;
    private FragmentManager fm;

    private boolean isLoadingAdded = false;

    public TvShowsAdapter(Context context, FragmentManager fm) {
        this.context = context;
        this.fm = fm;
        this.tvShowList = new ArrayList<>();
        this.genreList = new ArrayList<>();
    }

    public List<TvShow> getTvShowList() {
        return tvShowList;
    }

    public void setTvShowList(List<TvShow> tvShowList) {
        this.tvShowList = tvShowList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.tv_shows_recycler_view, parent, false);
        viewHolder = new TvShowsVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final TvShow tvShow = tvShowList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                TvShowsVH tvShowsVH = (TvShowsVH) holder;

                tvShowsVH.name.setText(tvShow.getName());
                tvShowsVH.rating.setText(String.format("Rating: %s", ConvertValue.toOneDecimal(tvShow.getVoteAverage())));
                tvShowsVH.genre.setText(makeGenreFromId(tvShow.getGenreIds()));
                tvShowsVH.posterImg.setSmallImg(tvShow.getPosterPath());
                tvShowsVH.tvShowDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        TvDetailsFragment tvDetailsFragment = new TvDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putLong("tvId",tvShow.getId());
                        tvDetailsFragment.setArguments(bundle);
                        fm.beginTransaction().replace(R.id.main_container, tvDetailsFragment, tvDetailsFragment.getTag()).addToBackStack(null).commit();


                    }
                });

                break;
            case LOADING:
                //Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return tvShowList == null ? 0 : tvShowList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == tvShowList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(TvShow tvShow) {
        tvShowList.add(tvShow);
        notifyItemInserted(tvShowList.size() - 1);
    }

    public void addAll(List<TvShow> List) {
        for (TvShow tvShow : List) {
            add(tvShow);
        }
    }

    public void remove(TvShow tvShow) {
        int position = tvShowList.indexOf(tvShow);
        if (position > -1) {
            tvShowList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;

        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new TvShow());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = tvShowList.size() - 1;
        TvShow item = getItem(position);

        if (item != null) {
            tvShowList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public TvShow getItem(int position) {
        return tvShowList.get(position);
    }


    public void addAllGenre(List<Genre> list) {
        genreList.addAll(list);
    }

    public void isGenreListEmpty(){
        genreList.isEmpty();
    }

    public void clearGenreList(){
        genreList.clear();
    }

    private String makeGenreFromId(List<Integer> listId){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Integer id : listId) {
            for (Genre genre : genreList){
                if(genre.getId() == id){
                    if(i != 0){
                        sb.append(", ");
                    }
                    sb.append(genre.getName());
                    i ++;
                    break;
                }
            }
        }
        return sb.toString();
    }

   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    private class TvShowsVH extends RecyclerView.ViewHolder {
        private TextView rating;
        private TextView name;
        private TextView genre;
        private ImageHandler posterImg;
        private RelativeLayout tvShowDetail;

        private TvShowsVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_tv_shows_textView);
            rating = (TextView) itemView.findViewById(R.id.rating_tv_shows_textView);
            posterImg = new ImageHandler(context,(ImageView) itemView.findViewById(R.id.poster_tv_shows_imageView));
            genre = (TextView) itemView.findViewById(R.id.genre_tv_shows_textView);
            tvShowDetail = (RelativeLayout) itemView.findViewById(R.id.tv_shows_recycler);
        }
    }


    private class LoadingVH extends RecyclerView.ViewHolder {

        private LoadingVH(View itemView) {
            super(itemView);
        }
    }



}
