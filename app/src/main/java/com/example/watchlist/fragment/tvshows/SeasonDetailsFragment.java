package com.example.watchlist.fragment.tvshows;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.adapter.TvEpisodesAdapter;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.themoviedb.TvSeasonDetails;
import com.example.watchlist.utils.ImageHandler;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class SeasonDetailsFragment extends Fragment {
    private static final String TAG = "SeasonDetailsFragment";

    private Context context;
    private Time time;
    private TvSeasonDetails tvSeasonDetails;

    private long tvId;
    private int seasonNumber;
    private ImageHandler posterImg;
    private TvEpisodesAdapter tvEpisodesAdapter;
    private TextView seasonName;


    public SeasonDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * It create the view for the season details and
     * get the context and also setup the recycler view
     * for it
     * @param inflater Inflater is LayoutInflater
     * @param container Container is ViewGroup
     * @param savedInstanceState SavedInstanceState is Bundle
     * @return It return the View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_season_details, container, false);

        context = getContext();

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_season_details_imageView));
        seasonName = (TextView) v.findViewById(R.id.name_season_details_textView);

        RecyclerView tvEpisodesRecycler = (RecyclerView) v.findViewById(R.id.tv_episodes_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        tvEpisodesRecycler.setLayoutManager(layoutManager);
        tvEpisodesAdapter = new TvEpisodesAdapter(context,getActivity().getSupportFragmentManager());
        tvEpisodesRecycler.setAdapter(tvEpisodesAdapter);

        if(time == null){
            time = new Time();
        }

        if(getArguments() != null){
            tvId = getArguments().getLong("tvId");
            seasonNumber = getArguments().getInt("seasonNumber");
        }

        return v;
    }

    /**
     * Check whether the tvShowsAdapter is empty
     * if so call reqSeasonDetails function and
     * set the time it was called,
     * if not empty call displayData function.
     */
    @Override
    public void onStart() {
        super.onStart();
        if(tvSeasonDetails == null || time.isOverTime(time.ONE_HOUR)){
            reqSeasonDetails();
            time.setFirstTime(time.getTimeInMillis());
        }else{
            displayData();
        }

    }

    /**
     * Sends Http Request that request Season details.
     */
    private void reqSeasonDetails(){
        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.seasonDetails(tvId,seasonNumber,resSeasonDetails());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }


    /**
     * Receiving respond from the backend server.
     * @return It return callback.
     */
    private Callback resSeasonDetails(){
        return new Callback<TvSeasonDetails>(){
            @Override
            public void onResponse(Call<TvSeasonDetails> call, Response<TvSeasonDetails> response) {
                if(response.isSuccessful()){
                    tvSeasonDetails = response.body();
                    displayData();

                }else {
                    PopUpMsg.displayErrorMsg(context);
                }
            }

            @Override
            public void onFailure(Call<TvSeasonDetails> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);

            }
        };
    }

    /**
     * Display the Season details on the screen;
     */
    private void displayData(){

        seasonName.setText(tvSeasonDetails.getName());

        posterImg.setLargeImg(tvSeasonDetails.getPosterPath());

        tvEpisodesAdapter.setEpisodes(tvSeasonDetails.getEpisodes(),tvId,tvSeasonDetails.getPosterPath());


    }


}
