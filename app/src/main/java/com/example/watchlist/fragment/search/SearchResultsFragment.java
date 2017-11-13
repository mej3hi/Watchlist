package com.example.watchlist.fragment.search;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.watchlist.R;
import com.example.watchlist.adapter.SearchResultsAdapter;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqMovies;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.BackgroundPoster;
import com.example.watchlist.utils.Pagination;
import com.example.watchlist.utils.PaginationScrollListener;
import com.example.watchlist.utils.PopUpMsg;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultsFragment extends Fragment {
    private static final String TAG = "SearchResultsFragment";

    private Context context;
    private Pagination moviePagination;
    private Pagination tvPagination;

    private ImageView poster;
    private SearchResultsAdapter resultsAdapter;

    private boolean searchTv;
    private boolean searchMovie;
    private String searchQuery;


    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_results, container, false);

        context = getContext();

        poster = (ImageView) v.findViewById(R.id.search_results_imageView);

        RecyclerView searchRecycler = (RecyclerView) v.findViewById(R.id.search_results_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        searchRecycler.setLayoutManager(layoutManager);
        if(resultsAdapter == null || tvPagination == null || moviePagination == null){
            initialize();
        }
        searchRecycler.setAdapter(resultsAdapter);
        searchRecycler.addOnScrollListener(setPageScrollListener(layoutManager));


        if(getArguments() != null){
            searchQuery = getArguments().getString("searchQuery");
            searchTv = getArguments().getBoolean("searchTv");
            searchMovie = getArguments().getBoolean("searchMovie");
        }

        return v;
    }

    public void onStart() {
        super.onStart();
        if(resultsAdapter.isEmpty()){
            reqSearchResults();
        }else{
            BackgroundPoster.setRandomBackPosterSearch(resultsAdapter.getResultsList(),context,poster);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void initialize(){
        tvPagination = new Pagination();
        moviePagination = new Pagination();
        tvPagination.setTotalPages(1);
        moviePagination.setTotalPages(1);
        resultsAdapter = new SearchResultsAdapter(context,getActivity().getSupportFragmentManager());
    }

    /**
     * It adds a pagination scroll listener that ask for more data
     * if it is not the last page and not loading.
     * @param layoutManager LayoutManager contains the LinearLayoutManager.
     * @return It return the PaginationScrollListener.
     */
    private PaginationScrollListener setPageScrollListener(LinearLayoutManager layoutManager){
        return new  PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                tvPagination.setLoading(searchTv);
                moviePagination.setLoading(searchMovie);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reqSearchResults();
                    }
                },500);
            }

            @Override
            public boolean isLastPage() {
                return tvPagination.isLastPage() && moviePagination.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return tvPagination.isLoading() && moviePagination.isLoading();
            }
        };

    }


    /**
     * Sends Http Request that request search for tv and movie.
     */
    private void reqSearchResults(){

        if(NetworkChecker.isOnline(context)) {
            if(tvPagination.getCurrentPage() <=tvPagination.getTotalPages() && searchTv){
                tvPagination.setLoading(true);
                ReqTvShows.SearchTvShows(tvPagination.getCurrentPage(),searchQuery,resSearchTvResults());
            }
            if(moviePagination.getCurrentPage() <= moviePagination.getTotalPages() && searchMovie){
                moviePagination.setLoading(true);
                ReqMovies.SearchMovies(moviePagination.getCurrentPage(),searchQuery,resSearchMovieResults());
            }

        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }


    /**
     * Receiving Respond from the backend server.
     *
     */
    public Callback resSearchTvResults(){
        return new Callback<TvShow.TvShowsResults>(){
            @Override
            public void onResponse(Call<TvShow.TvShowsResults> call, Response<TvShow.TvShowsResults> response) {
                if(response.isSuccessful()){
                    tvPagination.setTotalPages(response.body().getTotalPages());

                    tvPagination.setLoading(false);

                    displayData(response.body().getResults(),new ArrayList<Movie>());

                }else {
                    PopUpMsg.displayErrorMsg(context);
                }
            }

            @Override
            public void onFailure(Call<TvShow.TvShowsResults> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);
            }
        };
    }

    /**
     * Receiving Respond from the backend server.
     *
     */
    public Callback resSearchMovieResults(){
        return new Callback<Movie.MoviesResults>(){
            @Override
            public void onResponse(Call<Movie.MoviesResults> call, Response<Movie.MoviesResults> response) {
                if(response.isSuccessful()){
                    moviePagination.setTotalPages(response.body().getTotalPages());

                    moviePagination.setLoading(false);

                    displayData(new ArrayList<TvShow>(),response.body().getResults());

                }else {
                    PopUpMsg.displayErrorMsg(context);
                }
            }

            @Override
            public void onFailure(Call<Movie.MoviesResults> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);
            }
        };
    }

    /**
     * Check if the page is last page and if so, set setLastPage to true
     */

    public void checkLastPage(){
        if(tvPagination.getCurrentPage()<= tvPagination.getTotalPages()){
            resultsAdapter.addLoadingFooter();
        }else if(moviePagination.getCurrentPage() <= moviePagination.getTotalPages()){
            resultsAdapter.addLoadingFooter();
        }else{
            tvPagination.setLastPage(true);
            moviePagination.setLastPage(true);
        }

        moviePagination.setCurrentPage(moviePagination.getCurrentPage()+1);
        tvPagination.setCurrentPage(tvPagination.getCurrentPage()+1);

    }
    /**
     * Display the results on the screen
     *
     */
    public void displayData(List<TvShow> tvResults , List<Movie> movieResults){

        if(!resultsAdapter.isEmpty()){
            resultsAdapter.removeLoadingFooter();
        }else{
            if(!tvResults.isEmpty()){
                BackgroundPoster.setRandomBackPosterTv(tvResults,context, poster);
            }else if (!movieResults.isEmpty()){
                BackgroundPoster.setRandomBackPosterMovie(movieResults,context, poster);
            }
        }

        if(GerneList.getGenreTvList() != null){
            resultsAdapter.addAllGenre(GerneList.getGenreTvList());
        }
        resultsAdapter.addAllMovies(movieResults);
        resultsAdapter.addAllTv(tvResults);

        checkLastPage();

    }







}
