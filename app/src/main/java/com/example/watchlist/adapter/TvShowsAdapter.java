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
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
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

    /**
     * Is the constructor for the TvShowsAdapter
     * @param context Context is context
     * @param fm Fm is FragmentManager
     */
    public TvShowsAdapter(Context context, FragmentManager fm) {
        this.context = context;
        this.fm = fm;
        this.tvShowList = new ArrayList<>();
        this.genreList = new ArrayList<>();
    }

    /**
     * It get the tvShowList
     * @return It return list of tv shows
     */
    public List<TvShow> getTvShowList() {
        return tvShowList;
    }

    /**
     * Is set list of tv shows to tvShowList
     * @param tvShowList TvShowList is list of Tv shows
     */
    public void setTvShowList(List<TvShow> tvShowList) {
        this.tvShowList = tvShowList;
    }

    /**
     * Add new view that is either tv shows view or loading footer view
     * @param parent Parent is the viewGroup
     * @param viewType What view type to display
     * @return It return the new view
     */
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

    /**
     * It get the view for the tv shows
     * @param parent Parent is the viewGroup
     * @param inflater Inflater is LayoutInflater
     * @return It return tv shows view
     */
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.tv_shows_recycler_view, parent, false);
        viewHolder = new TvShowsVH(v1);
        return viewHolder;
    }

    /**
     * It display the tv shows at the specified position and add
     * onClick listener that open tv shows details fragment
     * for that tv show
     * @param holder Holder is the viewHolder
     * @param position The position of the item within the adapter's data set.
     */
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
    /**
     * It get item count
     * @return Return 0 if null else the size of tvShowList
     */
    @Override
    public int getItemCount() {
        return tvShowList == null ? 0 : tvShowList.size();
    }

    /**
     * It get the item view type in that position
     * @param position The position is from 0 - tvShowList size.
     * @return It return LOADING if true else ITEM
     */
    @Override
    public int getItemViewType(int position) {
        return (position == tvShowList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    /**
     * It add movie to movieList
     * @param tvShow Tv Show is the tv Show object
     */
    public void add(TvShow tvShow) {
        tvShowList.add(tvShow);
        notifyItemInserted(tvShowList.size() - 1);
    }
    /**
     * It add list of tv shows to tvShowList
     * @param list List is list of tv shows.
     */
    public void addAll(List<TvShow> list) {
        for (TvShow tvShow : list) {
            add(tvShow);
        }
    }

    /**
     * Check whether the tvShowList is empty
     * @return Return true if it is empty else false
     */
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    /**
     * It add the loading footer
     */
    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new TvShow());
    }

    /**
     * It remove the loading footer
     */
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = tvShowList.size() - 1;
        TvShow item = getItem(position);

        if (item != null) {
            tvShowList.remove(position);
            notifyItemRemoved(position);
        }
    }
    /**
     * It get the tv show in that position
     * @param position The position is from 0 - tvShowList size.
     * @return It return a tv show object.
     */
    public TvShow getItem(int position) {
        return tvShowList.get(position);
    }

    /**
     * It add list of genre to genreList
     * @param list Is list of genre
     */
    public void addAllGenre(List<Genre> list) {
        genreList.addAll(list);
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
     * TvShowsVH content ViewHolder
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

    /**
     * Is the loading footer ViewHolder
     */
    private class LoadingVH extends RecyclerView.ViewHolder {
        private LoadingVH(View itemView) {
            super(itemView);
        }
    }



}
