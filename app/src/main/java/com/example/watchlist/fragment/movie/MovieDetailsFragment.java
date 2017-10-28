package com.example.watchlist.fragment.movie;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqMovies;
import com.example.watchlist.themoviedb.MovieDetails;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment {

    private static final String TAG = "MovieDatailsFrag";

    private Context context;
    private Time time;

    private MovieDetails movieDetails;
    private long movieId;

    private ImageView poster;
    private ImageView backdrop;
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

        poster = (ImageView) v.findViewById(R.id.poster_movie_details_imageView);
        backdrop = (ImageView) v.findViewById(R.id.backdrop_movie_details_imageView);
        name = (TextView) v.findViewById(R.id.name_movie_details_textView);
        rating = (TextView) v.findViewById(R.id.rating_movie_details_textView);
        releaseDate = (TextView) v.findViewById(R.id.release_date_movie_details_textView);
        runTime = (TextView) v.findViewById(R.id.run_time_movie_details_textView);
        overview = (TextView) v.findViewById(R.id.overview_movie_details_textView);
        genre = (TextView) v.findViewById(R.id.genre_movie_details_textView);
        watchlistBtn = (Button) v.findViewById(R.id.add_watchlist_movie_details_btn);

        if(time == null ) {
            time = new Time();
        }

        if(getArguments() != null){
            movieId = getArguments().getLong("movieId");
        }

        watchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick watchlistBtn");
            }
        });

        Log.d(TAG,"onCreateView");

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        reqMovieDetails();
        if(movieDetails == null || time.isOverTime(time.ONE_HOUR)){
            reqMovieDetails();
            time.setFirstTime(time.getTimeInMillis());
        }else {
            displayData(movieDetails);
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    /**
     * Sends HttpRequest that request Movie details.
     */
    private void reqMovieDetails(){
        Log.d(TAG,"ná í Movie Details");

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
    public Callback resMovieDetails(){
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
     * Display the Tv details on the screen;
     * @param details contains Movie shows details.
     */
    public void displayData(MovieDetails details){
        name.setText(details.getTitle());
        rating.setText(ConvertValue.toOneDecimal(details.getVoteAverage()));
        releaseDate.setText(details.getReleaseDate());
        runTime.setText((details.getRuntime())+" Min");
        overview.setText(details.getOverview());
        genre.setText(ConvertValue.genreToString(details.getGenres()));

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+details.getBackdropPath()).into(backdrop);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+details.getPosterPath()).into(poster);


    }


}
