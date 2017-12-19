package com.example.watchlist.fragment.myWatchlist;


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

import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.ImageHandler;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class MoviesMyWatchlistFragment extends Fragment {

    private Context context;

    private ImageHandler posterImg;
    private MoviesAdapter moviesAdapter;

    public MoviesMyWatchlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movies_my_watchlist, container, false);

        context = getContext();

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_my_watchlist_movie_imageView));

        RecyclerView ratedTvShowsRecycler = (RecyclerView) v.findViewById(R.id.my_watchlist_movie_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        ratedTvShowsRecycler.setLayoutManager(layoutManager);
        moviesAdapter = new MoviesAdapter(context,getActivity().getSupportFragmentManager());
        ratedTvShowsRecycler.setAdapter(moviesAdapter);

        return v;

    }

    /**
     * Call the getAllData function.
     */
    @Override
    public void onStart() {
        super.onStart();
        getAllData();
    }

    /**
     * Get all movies that user has saved in watchlist
     * and display it.
     */
    private void getAllData(){
        List<Movie> newMovieList = new ArrayList<>();
        Movie movie;
        for (MovieWatch t : MovieDatabaseUtil.getAllMovie()) {
            movie = new Movie(
                    t.getMovieId(),
                    t.getName(),
                    t.getPosterPath(),
                    t.getRating(),
                    ConvertValue.genreIdToListInteger(t.getGenre()));
            newMovieList.add(movie);
        }
        displayData(newMovieList);
    }

    /**
     * Display the user watchlist on the screen;
     * @param movieList Results contains list of movies.
     */
    private void displayData(List<Movie> movieList){
        if(GerneList.getGenreMovieList() != null){
            moviesAdapter.addAllGenre(GerneList.getGenreMovieList());
        }
        if(moviesAdapter.isEmpty() && movieList.size() != 0 ) {
            int r = new Random().nextInt(movieList.size());
            posterImg.setLargeImg(movieList.get(r).getPosterPath());
        }
        moviesAdapter.addAll(movieList);
    }


}
