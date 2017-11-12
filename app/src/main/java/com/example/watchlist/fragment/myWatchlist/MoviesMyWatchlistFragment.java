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
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.BackgroundPoster;
import com.example.watchlist.utils.ConvertValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesMyWatchlistFragment extends Fragment {

    private Context context;

    private ImageView poster;
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

        poster = (ImageView) v.findViewById(R.id.poster_my_watchlist_movie_imageView);

        RecyclerView ratedTvShowsRecycler = (RecyclerView) v.findViewById(R.id.my_watchlist_movie_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        ratedTvShowsRecycler.setLayoutManager(layoutManager);
        moviesAdapter = new MoviesAdapter(context,getActivity().getSupportFragmentManager());
        ratedTvShowsRecycler.setAdapter(moviesAdapter);

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        getAllData();
    }

    /**
     * It will get all the movies that user has saved
     * and display it.
     */
    public void getAllData(){
        List<Movie> newMovieList = new ArrayList<>();
        Movie movie;
        for (MovieWatch t : MovieDatabaseUtil.getallMovie()) {
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
     * @param movieList Results contains movies.
     */
    public void displayData(List<Movie> movieList){
        if(GerneList.getGenreMovieList() != null){
            moviesAdapter.addAllGenre(GerneList.getGenreMovieList());
        }
        if(moviesAdapter.isEmpty() && movieList.size() != 0 ) {
            BackgroundPoster.setRandomBackPosterMovie(movieList,context,poster);
        }
        moviesAdapter.addAll(movieList);
    }


}
