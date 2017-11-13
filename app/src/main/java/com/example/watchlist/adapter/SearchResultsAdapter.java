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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
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

    public SearchResultsAdapter(Context context, FragmentManager fm) {
        this.context = context;
        this.fm = fm;
        this.searchResultsList = new ArrayList<>();
        this.genreList = new ArrayList<>();
    }

    public List<SearchResults> getResultsList() {
        return searchResultsList;
    }

    public void setResultsList(List<SearchResults> tvShowList) {
        this.searchResultsList = searchResultsList;
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
                viewHolder = new SearchResultsAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.search_results_recycler_view, parent, false);
        viewHolder = new SearchResultsAdapter.SearchResultsVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final SearchResults results = searchResultsList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                SearchResultsAdapter.SearchResultsVH resultsVH = (SearchResultsAdapter.SearchResultsVH) holder;

                resultsVH.name.setText(results.getName());
                resultsVH.rating.setText("Rating: " + ConvertValue.toOneDecimal(results.getRating()));

                List<Integer> genersId = results.getGenreIds();

                StringBuilder sb = new StringBuilder();
                int i = 0;
                for (Integer id : genersId) {
                    i++;
                    for (Genre genre : genreList) {
                        if (genre.getId() == id) {
                            sb.append(genre.getName());
                            if (genersId.size() > i) {
                                sb.append(", ");
                            }
                            break;
                        }
                    }

                }

                resultsVH.genre.setText(sb.toString());
                Picasso.with(context).load("http://image.tmdb.org/t/p/w92" + results.getPosterPath()).into(resultsVH.poster);
                resultsVH.resultDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Objects.equals(results.getMediaType(), "tv")) {
                            TvDetailsFragment fragment = new TvDetailsFragment();
                            Bundle bundle = new Bundle();
                            bundle.putLong("tvId", results.getId());
                            fragment.setArguments(bundle);
                            fm.beginTransaction().replace(R.id.main_container, fragment, fragment.getTag()).addToBackStack(null).commit();

                        } else if (Objects.equals(results.getMediaType(), "movie")) {
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

    @Override
    public int getItemCount() {
        return searchResultsList == null ? 0 : searchResultsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == searchResultsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(SearchResults results) {
        searchResultsList.add(results);
        notifyItemInserted(searchResultsList.size() - 1);
    }

    public void addAll(List<SearchResults> list) {
        for (SearchResults results : list) {
            add(results);
        }
    }

    public void addAllMovies(List<Movie> list) {
        for (Movie movie : list) {
            SearchResults result = new SearchResults(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getVoteAverage(),
                    movie.getPosterPath(),
                    movie.getGenreIds(),
                    "movie");
            add(result);

        }
    }

    public void addAllTv(List<TvShow> list) {
        for (TvShow tv : list) {
            SearchResults result = new SearchResults(
                    tv.getId(),
                    tv.getName(),
                    tv.getVoteAverage(),
                    tv.getPosterPath(),
                    tv.getGenreIds(),
                    "tv");
            add(result);

        }
    }

    public void remove(SearchResults results) {
        int position = searchResultsList.indexOf(results);
        if (position > -1) {
            searchResultsList.remove(position);
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
        add(new SearchResults());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = searchResultsList.size() - 1;
        SearchResults item = getItem(position);

        if (item != null) {
            searchResultsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public SearchResults getItem(int position) {
        return searchResultsList.get(position);
    }


    public void addAllGenre(List<Genre> list) {
        genreList.addAll(list);
    }

    public void isGenreListEmpty() {
        genreList.isEmpty();
    }

    public void clearGenreList() {
        genreList.clear();
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class SearchResultsVH extends RecyclerView.ViewHolder {
        private TextView rating;
        private TextView name;
        private TextView genre;
        private ImageView poster;
        private RelativeLayout resultDetail;

        public SearchResultsVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_search_results_textView);
            rating = (TextView) itemView.findViewById(R.id.rating_search_results_textView);
            poster = (ImageView) itemView.findViewById(R.id.poster_search_results_imageView);
            genre = (TextView) itemView.findViewById(R.id.genre_search_results_textView);
            resultDetail = (RelativeLayout) itemView.findViewById(R.id.search_results_recycler);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}