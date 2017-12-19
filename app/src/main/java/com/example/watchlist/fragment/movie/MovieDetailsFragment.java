package com.example.watchlist.fragment.movie;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchlist.R;

import com.example.watchlist.database.MovieDatabaseUtil;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqMovies;
import com.example.watchlist.themoviedb.MovieDetails;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.ImageHandler;
import com.example.watchlist.utils.PopUpMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */

public class MovieDetailsFragment extends Fragment {
    private static final String TAG = "MovieDatailsFrag";

    private Context context;

    private MovieDetails movieDetails;
    private long movieId;

    private boolean hasBeenAdded;

    private ImageHandler posterImg;
    private ImageHandler backdropImg;
    private TextView name;
    private TextView rating;
    private TextView releaseDate;
    private TextView runTime;
    private TextView overview;
    private TextView genre;
    private Button watchlistBtn;


    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movie_details, container, false);

        context = getContext();
        hasBeenAdded = false;

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_movie_details_imageView));
        backdropImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.backdrop_movie_details_imageView));
        name = (TextView) v.findViewById(R.id.name_movie_details_textView);
        rating = (TextView) v.findViewById(R.id.rating_movie_details_textView);
        releaseDate = (TextView) v.findViewById(R.id.release_date_movie_details_textView);
        runTime = (TextView) v.findViewById(R.id.run_time_movie_details_textView);
        overview = (TextView) v.findViewById(R.id.overview_movie_details_textView);
        genre = (TextView) v.findViewById(R.id.genre_movie_details_textView);
        watchlistBtn = (Button) v.findViewById(R.id.add_watchlist_movie_details_btn);

        if(getArguments() != null){
            movieId = getArguments().getLong("movieId");
        }

        watchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasBeenAdded){
                    removeMovie();
                }else {
                    addMovie();
                }
            }
        });

        return v;
    }

    /**
     * Call reqMovieDetails and changeButton function.
     */
    @Override
    public void onStart() {
        super.onStart();
        reqMovieDetails();
        changeButton();
    }

    /**
     * Sends Http Request that request Movie details.
     */
    private void reqMovieDetails(){
        if(NetworkChecker.isOnline(context)) {
            ReqMovies.movieDetails(movieId, resMovieDetails());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }
    /**
     * Receiving Respond from the backend server.
     *
     */
    private Callback resMovieDetails(){
        return new Callback<MovieDetails>(){
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if(response.isSuccessful()){
                    movieDetails = response.body();
                    displayData(response.body());

                }else {
                    PopUpMsg.displayErrorMsg(context);
                }

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);

            }
        };

    }

    /**
     * Display the movie details on the screen;
     * @param details contains movie details.
     */
    private void displayData(MovieDetails details){
        name.setText(details.getTitle());
        rating.setText(ConvertValue.toOneDecimal(details.getVoteAverage()));
        releaseDate.setText(details.getReleaseDate());
        runTime.setText(String.format("%s Min", String.valueOf(details.getRuntime())));
        overview.setText(details.getOverview());
        genre.setText(ConvertValue.genreToString(details.getGenres()));
        backdropImg.setMediumImg(details.getBackdropPath());
        posterImg.setLargeImg(details.getPosterPath());

    }

    /**
     * Checks if movie is added to watchlist and display
     * the right button.
     */
    private void changeButton(){
        if(MovieDatabaseUtil.isMovieAddedToWatchlist(movieId)){
            watchlistBtn.setBackgroundColor(0xffe6b800);
            watchlistBtn.setText(R.string.removeBtn);
            hasBeenAdded = true;
        }else{
            watchlistBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            watchlistBtn.setText(R.string.addBtn);
            hasBeenAdded = false;
        }
    }

    /**
     * Remove movie from watchlist and
     * call changeButton function.
     */
    private void removeMovie(){
        MovieDatabaseUtil.removeMovieFromWatchlist(movieId);
        changeButton();
    }

    /**
     * Add movie to watchlist and
     * call changeButton function.
     */
    private void addMovie(){
        MovieDatabaseUtil.addMovieToWatchlist(
                movieId,
                movieDetails.getTitle(),
                movieDetails.getPosterPath(),
                movieDetails.getVoteAverage(),
                ConvertValue.genreIdToString(movieDetails.getGenres()));
        changeButton();
    }




}
