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
public class EpisodeDetailsFragment extends Fragment {
    private static final String TAG = "EpisodeDetailsFragment";

    private long tvId;
    private int episodeNumber;
    private int seasonNumber;
    private String posterPath;

    private Context context;

    private ImageHandler posterImg;
    private ImageHandler stillImg;
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
        stillImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.still_episode_details_imageView));
        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_episode_details_imageView));


        if(getArguments() != null){
            tvId = getArguments().getLong("tvId");
            seasonNumber = getArguments().getInt("seasonNumber");
            episodeNumber = getArguments().getInt("episodeNumber");
            posterPath = getArguments().getString("posterPath");
        }
        Log.d(TAG,"id " +tvId+" S "+seasonNumber+" Ep "+episodeNumber);


        return v;
    }

    /**
     * Call reqEpisodeDetails function.
     */
    @Override
    public void onStart() {
        super.onStart();
        reqEpisodeDetails();
    }

    /**
     *  Sends Http Request that requests Episode details.
     */
    private void reqEpisodeDetails(){
        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.episodeDetails(tvId,seasonNumber,episodeNumber, resEpisodeDetails() );
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving respond from the backend server.
     * @return It return Callback.
     */
    private Callback resEpisodeDetails(){
        return new Callback<TvEpisodeDetails>(){
            @Override
            public void onResponse(Call<TvEpisodeDetails> call, Response<TvEpisodeDetails> response) {
                if(response.isSuccessful()){
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
    private void displayData(TvEpisodeDetails details){

        name.setText(details.getName());
        rating.setText(ConvertValue.toOneDecimal(details.getVoteAverage()));
        releaseDate.setText(details.getAirDate());
        seriesNumber.setText(String.format("S%s, Ep%s", String.valueOf(details.getSeasonNumber()), String.valueOf(details.getEpisodeNumber())));
        overview.setText(details.getOverview());
        posterImg.setLargeImg(posterPath);
        stillImg.setMediumImg(details.getStillPath());

    }




}
