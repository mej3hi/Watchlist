package com.example.watchlist.fragment.movie;


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
import com.example.watchlist.adapter.MoviesAdapter;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqMovies;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.utils.ImageHandler;
import com.example.watchlist.utils.Pagination;
import com.example.watchlist.utils.PaginationScrollListener;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class PopularMoviesFragment extends Fragment {

    private static final String TAG ="PopularMoviesFrag";

    private Context context;
    private Time time;
    private Pagination pagination;
    private ImageHandler posterImg;
    private MoviesAdapter moviesAdapter;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_popular_movies, container, false);

        context = getContext();

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_popular_movies_imageView));
        RecyclerView popularMoviesRecycler = (RecyclerView) v.findViewById(R.id.popular_movies_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        popularMoviesRecycler.setLayoutManager(layoutManager);
        if(moviesAdapter == null || time == null ||time.isOverTime(time.ONE_HOUR) || pagination == null) {
            initialize();
        }
        popularMoviesRecycler.setAdapter(moviesAdapter);
        popularMoviesRecycler.addOnScrollListener(setPageScrollListener(layoutManager));

        return v;
    }

    /**
     * Check whether the moviesAdapter is empty
     * if so call reqPopularMovies function and
     * set the time it was called,
     * if not empty set random poster image.
     */
    @Override
    public void onStart() {
        super.onStart();
        if(moviesAdapter.isEmpty()){
            time.setFirstTime(time.getTimeInMillis());
            reqPopularMovies();
        }
        else{
            int r = new Random().nextInt(moviesAdapter.getMovieList().size());
            posterImg.setLargeImg(moviesAdapter.getMovieList().get(r).getPosterPath());
        }
    }

    /**
     * Initialize the fragment
     */
    private void initialize(){
        time = new Time();
        moviesAdapter = new MoviesAdapter(context, getActivity().getSupportFragmentManager());
        pagination = new Pagination();
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
                pagination.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reqPopularMovies();
                    }
                },500);
            }

            @Override
            public boolean isLastPage() {
                return pagination.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return pagination.isLoading();
            }
        };

    }
    /**
     * Sends Http Request that request Popular Movies
     */
    private void reqPopularMovies(){
        if(NetworkChecker.isOnline(context)) {
            ReqMovies.popularMovies(pagination.getCurrentPage(), resPopularMovies());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving respond from the backend server.
     *
     */
    private Callback resPopularMovies(){
        return new Callback<Movie.MoviesResults>(){
            @Override
            public void onResponse(Call<Movie.MoviesResults> call, Response<Movie.MoviesResults> response) {
                if(response.isSuccessful()){
                    pagination.setTotalPages(response.body().getTotalPages());

                    pagination.setLoading(false);

                    displayData(response.body());

                    pagination.setCurrentPage(pagination.getCurrentPage()+1);

                    Log.d(TAG,"isLastPage "+pagination.isLastPage()+" isLoading "+pagination.isLoading());

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
     * Display the Popular movies on the screen;
     * @param results Results contains movies results.
     */
    private void displayData(Movie.MoviesResults results){
        if(!moviesAdapter.isEmpty()){
            moviesAdapter.removeLoadingFooter();
        }
        if(GerneList.getGenreMovieList() != null){
            moviesAdapter.addAllGenre(GerneList.getGenreMovieList());
        }

        if(moviesAdapter.isEmpty()){
            int r = new Random().nextInt(results.getResults().size());
            posterImg.setLargeImg(results.getResults().get(r).getPosterPath());
        }

        moviesAdapter.addAll(results.getResults());

        if(pagination.getCurrentPage() < pagination.getTotalPages()) moviesAdapter.addLoadingFooter();
        else pagination.setLastPage(true);


    }

}
