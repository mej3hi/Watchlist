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
import com.example.watchlist.fragment.movie.MovieDetailsFragment;
import com.example.watchlist.fragment.tvshows.TvDetailsFragment;
import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.SearchResults;
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
 * SearchResultsAdapter is used to create list of movies and tv shows for the search
 * results.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<SearchResults> searchResultsList;
    private List<Genre> genreList;
    private Context context;
    private FragmentManager fm;

    private boolean isLoadingAdded = false;

    /**
     * Is the constructor for the MoviesAdapter
     * @param context Context is context
     * @param fm Fm is FragmentManager
     */
    public SearchResultsAdapter(Context context, FragmentManager fm) {
        this.context = context;
        this.fm = fm;
        this.searchResultsList = new ArrayList<>();
        this.genreList = new ArrayList<>();
    }

    /**
     * It get the searchResultsList
     * @return It return list of SearchResults
     */
    public List<SearchResults> getResultsList() {
        return searchResultsList;
    }

    /**
     * Is set list of movie to movieList
     * @param searchResultsList SearchResultsList is list of search results
     */
    public void setResultsList(List<SearchResults> searchResultsList) {
        this.searchResultsList = searchResultsList;
    }

    /**
     * Add new view that is either search results view or loading footer view
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
                viewHolder = new SearchResultsAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    /**
     * It get the view for the search results
     * @param parent Parent is the viewGroup
     * @param inflater Inflater is LayoutInflater
     * @return It return search results view
     */
    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.search_results_recycler_view, parent, false);
        viewHolder = new SearchResultsAdapter.SearchResultsVH(v1);
        return viewHolder;
    }

    /**
     * It display the search result at the specified position and add
     * onClick listener that either open movie details or tv show details fragment
     * @param holder Holder is the viewHolder
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final SearchResults results = searchResultsList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                SearchResultsAdapter.SearchResultsVH resultsVH = (SearchResultsAdapter.SearchResultsVH) holder;

                resultsVH.name.setText(results.getName());
                resultsVH.rating.setText(String.format("Rating: %s", ConvertValue.toOneDecimal(results.getRating())));
                resultsVH.genre.setText(ConvertValue.genreFromId(results.getGenreIds(),genreList));
                resultsVH.mediaType.setText(results.getMediaType());
                resultsVH.posterImg.setSmallImg(results.getPosterPath());
                resultsVH.resultDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Objects.equals(results.getMediaType(), "TV SHOW")) {
                            TvDetailsFragment fragment = new TvDetailsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putLong("tvId", results.getId());
                            fragment.setArguments(bundle);
                            fm.beginTransaction().replace(R.id.main_container, fragment, fragment.getTag()).addToBackStack(null).commit();

                        } else if (Objects.equals(results.getMediaType(), "MOVIE")) {
                            MovieDetailsFragment fragment = new MovieDetailsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putLong("movieId", results.getId());
                            fragment.setArguments(bundle);
                            fm.beginTransaction().replace(R.id.main_container, fragment, fragment.getTag()).addToBackStack(null).commit();

                        }


                    }
                });

                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    /**
     * It get item count
     * @return Return 0 if null else the size of searchResultsList
     */
    @Override
    public int getItemCount() {
        return searchResultsList == null ? 0 : searchResultsList.size();
    }

    /**
     * It get the item view type in that position
     * @param position The position is from 0 - searchResultsList size.
     * @return It return LOADING if true else ITEM
     */
    @Override
    public int getItemViewType(int position) {
        return (position == searchResultsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    /**
     * It add search results to searchResultsList
     * @param results Results is the Search results object
     */
    public void add(SearchResults results) {
        searchResultsList.add(results);
        notifyItemInserted(searchResultsList.size() - 1);
    }

    /**
     * It add list of movie to searchResultsList
     * @param list List is list of movie.
     */
    public void addAllMovies(List<Movie> list) {
        for (Movie movie : list) {
            SearchResults result = new SearchResults(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getVoteAverage(),
                    movie.getPosterPath(),
                    movie.getGenreIds(),
                    "MOVIE");
            add(result);

        }
    }

    /**
     * It add list of tv show to searchResultsList
     * @param list List is list of tv show.
     */
    public void addAllTv(List<TvShow> list) {
        for (TvShow tv : list) {
            SearchResults result = new SearchResults(
                    tv.getId(),
                    tv.getName(),
                    tv.getVoteAverage(),
                    tv.getPosterPath(),
                    tv.getGenreIds(),
                    "TV SHOW");
            add(result);

        }
    }

    /**
     * Check whether the searchResultsList is empty
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
        add(new SearchResults());
    }

    /**
     * It remove the loading footer
     */
    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = searchResultsList.size() - 1;
        SearchResults item = getItem(position);

        if (item != null) {
            searchResultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * It get the search results in that position
     * @param position The position is from 0 - searchResultsList size.
     * @return It return a search results object.
     */
    public SearchResults getItem(int position) {
        return searchResultsList.get(position);
    }

    /**
     * It add list of genre to genreList
     * @param list Is list of genre
     */
    public void addAllGenre(List<Genre> list) {
        genreList.addAll(list);
    }



   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * SearchResultsVH content ViewHolder
     */
    private class SearchResultsVH extends RecyclerView.ViewHolder {
        private TextView rating;
        private TextView name;
        private TextView genre;
        private TextView mediaType;
        private ImageHandler posterImg;
        private RelativeLayout resultDetail;

        private SearchResultsVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_search_results_textView);
            rating = (TextView) itemView.findViewById(R.id.rating_search_results_textView);
            posterImg = new ImageHandler(context,(ImageView) itemView.findViewById(R.id.poster_search_results_imageView));
            genre = (TextView) itemView.findViewById(R.id.genre_search_results_textView);
            mediaType = (TextView) itemView.findViewById(R.id.mediaType_search_results_textView);
            resultDetail = (RelativeLayout) itemView.findViewById(R.id.search_results_recycler);
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