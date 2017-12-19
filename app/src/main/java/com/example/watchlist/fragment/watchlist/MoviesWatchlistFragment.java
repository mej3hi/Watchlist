package com.example.watchlist.fragment.watchlist;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.watchlist.R;
import com.example.watchlist.adapter.MoviesAdapter;
import com.example.watchlist.database.MovieDatabaseUtil;
import com.example.watchlist.database.MovieWatch;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqMovies;
import com.example.watchlist.shareInfo.Cache;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.utils.ImageHandler;
import com.example.watchlist.utils.Pagination;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;

import java.util.ArrayList;
import java.util.List;
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
public class MoviesWatchlistFragment extends Fragment {
    private static final String TAG ="MoviesWatchFrag";

    private Context context;
    private Time time;
    private Pagination pagination;
    private List<MovieWatch> movieWatchList;

    private ImageHandler posterImg;
    private MoviesAdapter moviesAdapter;


    public MoviesWatchlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movies_watchlist, container, false);

        context = getContext();

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_watchlist_movie_imageView));
        time = new Time();
        pagination = new Pagination();

        RecyclerView onAirTvShowsRecycler = (RecyclerView) v.findViewById(R.id.watchlist_movie_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        onAirTvShowsRecycler.setLayoutManager(layoutManager);
        moviesAdapter = new MoviesAdapter(context,getActivity().getSupportFragmentManager());
        onAirTvShowsRecycler.setAdapter(moviesAdapter);

        return v;
    }

    /**
     * Get all movies that user has saved in watchlist.
     * Check whether Cache.NowPlayingMovie is not empty and
     * is not over time, if so call displayData function
     * with data from Cache.NowPlayingMovie,
     * else clear it and set time and call reqToDayMovie function
     */
    @Override
    public void onStart() {
        super.onStart();
        movieWatchList = MovieDatabaseUtil.getAllMovie();
        if(!Cache.NowPlayingMovie.isEmpty() && !time.isOverTime(Cache.NowPlayingMovie.getTime(),time.ONE_HOUR)){
            displayData(Cache.NowPlayingMovie.getMovieList());
        }else{
            Cache.NowPlayingMovie.clear();
            Cache.NowPlayingMovie.setTime(time.getTimeInMillis());
            reqToDayMovie();
        }
    }

    /**
     * Sends Http Request that request now playing movies
     */
    private void reqToDayMovie(){
        if(NetworkChecker.isOnline(context)) {
            ReqMovies.nowPlayingMovies(pagination.getCurrentPage(), resNowPlayingMovies());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving respond from the backend server.
     *
     */
    private Callback resNowPlayingMovies(){
        return new Callback<Movie.MoviesResults>(){
            @Override
            public void onResponse(Call<Movie.MoviesResults> call, Response<Movie.MoviesResults> response) {
                if(response.isSuccessful()){
                    pagination.setTotalPages(response.body().getTotalPages());

                    Cache.NowPlayingMovie.addToMovie(response.body().getResults());

                    pagination.setCurrentPage(pagination.getCurrentPage()+1);

                    if (pagination.getCurrentPage() > pagination.getTotalPages()){
                        displayData(Cache.NowPlayingMovie.getMovieList());

                    }else{
                        reqToDayMovie();
                    }

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
     * Filter out the movie that is not in the user watchlist and
     * return a new list.
     * @param movieList List of Movie
     * @param movieWatchList List of MovieWatch
     * @return return a list of movie
     */
    private List<Movie> filterOutData(List<Movie> movieList, List<MovieWatch> movieWatchList){
        List<Movie> newMovieList = new ArrayList<>();

        for (Movie movie : movieList){
            for(MovieWatch watch : movieWatchList){
                if(movie.getId() == watch.getMovieId()){
                    newMovieList.add(movie);
                    break;
                }
            }
        }
        return newMovieList;
    }

    /**
     * Display the now playing movies on the screen;
     * @param movieList tvShowList contains movies results.
     */
    private void displayData(List<Movie> movieList){

        List<Movie> newMovieList = filterOutData(movieList,movieWatchList);

        if(newMovieList.size() != 0) {
            if (GerneList.getGenreMovieList() != null) {
                moviesAdapter.addAllGenre(GerneList.getGenreMovieList());
            }
            if (moviesAdapter.isEmpty()) {
                int r = new Random().nextInt(newMovieList.size());
                posterImg.setLargeImg(newMovieList.get(r).getPosterPath());
            }
            moviesAdapter.addAll(newMovieList);

        }

    }


}
