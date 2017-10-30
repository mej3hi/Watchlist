package com.example.watchlist.fragment.tvshows;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.themoviedb.TvEpisodeDetails;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.PopUpMsg;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodeDetailsFragment extends Fragment {
    private static final String TAG = "EpisodeDetailsFragment";

    private long tvId;
    private int episodeNumber;
    private int seasonNumber;
    private String posterPath;

    private Context context;

    private ImageView poster;
    private ImageView stillImg;
    private TextView name;
    private TextView rating;
    private TextView releaseDate;
    private TextView seriesNumber;
    private TextView overview;


    public EpisodeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_episode_details, container, false);

        context = getContext();

        name = (TextView) v.findViewById(R.id.name_episode_details_textView);
        rating = (TextView) v.findViewById(R.id.rating_episode_details_textView);
        releaseDate = (TextView) v.findViewById(R.id.release_date_episode_details_textView);
        seriesNumber = (TextView) v.findViewById(R.id.series_number_episode_details_textView);
        overview = (TextView) v.findViewById(R.id.overview_episode_details_textView);
        stillImg = (ImageView) v.findViewById(R.id.still_episode_details_imageView);
        poster = (ImageView) v.findViewById(R.id.poster_episode_details_imageView);

        if(getArguments() != null){
            tvId = getArguments().getLong("tvId");
            seasonNumber = getArguments().getInt("seasonNumber");
            episodeNumber = getArguments().getInt("episodeNumber");
            posterPath = getArguments().getString("posterPath");
        }
        Log.d(TAG,"id " +tvId+" S "+seasonNumber+" Ep "+episodeNumber);


        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        reqEpisodeDetails();
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    /**
     *  Sends HttpRequest that requests Episode details.
     */
    private void reqEpisodeDetails(){
        Log.d(TAG,"ná í Episode Details");
        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.episodeDetails(tvId,seasonNumber,episodeNumber, resEpisodeDetails() );
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving Respond from the backend server.
     * @return It return Callback.
     */
    public Callback resEpisodeDetails(){
        return new Callback<TvEpisodeDetails>(){
            @Override
            public void onResponse(Call<TvEpisodeDetails> call, Response<TvEpisodeDetails> response) {
                if(response.isSuccessful()){
                    Log.d(TAG,"tókst að ná EpisodeDetails");
                    displayData(response.body());

                }else {
                    PopUpMsg.displayErrorMsg(context);
                }
            }

            @Override
            public void onFailure(Call<TvEpisodeDetails> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);
            }
        };
    }

    /**
     * Display the Episode details on the screen.
     * @param details Details contains episode details.
     */
    public void displayData(TvEpisodeDetails details){

        name.setText(details.getName());
        rating.setText(ConvertValue.toOneDecimal(details.getVoteAverage()));
        releaseDate.setText(details.getAirDate());
        seriesNumber.setText("S"+details.getSeasonNumber()+", "+"Ep"+details.getEpisodeNumber());
        overview.setText(details.getOverview());

        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+posterPath).into(poster);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+details.getStillPath()).into(stillImg);

    }




}