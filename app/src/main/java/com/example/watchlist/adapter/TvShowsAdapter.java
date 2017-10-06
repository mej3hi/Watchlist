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
import com.squareup.picasso.Picasso;

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
                tvShowsVH.rating.setText("Rating: "+ ConvertValue.toOneDecimal(tvShow.getVoteAverage()) );

                List<Integer> genersId = tvShow.getGenreIds();
                StringBuilder sb = new StringBuilder();

                int i = 0;
                for (Integer id : genersId) {
                    i ++;
                    for (Genre genre : genreList){
                        if(genre.getId() == id){
                            sb.append(genre.getName());
                            if (genersId.size() > i){
                                sb.append(", ");
                            }
                            break;
                        }
                    }

                }

                tvShowsVH.genre.setText(sb.toString());
                Picasso.with(context).load("http://image.tmdb.org/t/p/w92"+tvShow.getPosterPath()).into(tvShowsVH.backdrop);
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


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class TvShowsVH extends RecyclerView.ViewHolder {
        private TextView rating;
        private TextView name;
        private TextView genre;
        private ImageView backdrop;
        private RelativeLayout tvShowDetail;

        public TvShowsVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_tv_shows_textView);
            rating = (TextView) itemView.findViewById(R.id.rating_tv_shows_textView);
            backdrop = (ImageView) itemView.findViewById(R.id.backdrop_tv_shows_imageView);
            genre = (TextView) itemView.findViewById(R.id.genre_tv_shows_textView);
            tvShowDetail = (RelativeLayout) itemView.findViewById(R.id.tv_shows_recycler);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }



}
