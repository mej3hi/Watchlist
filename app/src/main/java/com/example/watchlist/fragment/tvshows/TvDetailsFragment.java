package com.example.watchlist.fragment.tvshows;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.adapter.TvSeasonsAdapter;
import com.example.watchlist.database.TvDatabaseUtil;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.themoviedb.TvDetails;
import com.example.watchlist.utils.ConvertValue;
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
public class TvDetailsFragment extends Fragment {
    private static final String TAG ="TvDetailsFrag";

    private Context context;
    private Time time;
    private TvDetails tvDetails;
    private long tvId;
    private boolean hasBeenStored;

    private TvSeasonsAdapter tvSeasonsAdapter;
    private TextView name;
    private TextView rating;
    private TextView releaseDate;
    private TextView runTime;
    private TextView overview;
    private TextView genre;
    private Button watchlistBtn;
    private ImageHandler posterImg;
    private ImageHandler backdropImg;


    public TvDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * It create the view for the tv show details and
     * get the context and set setOnClickListener for
     * the watchlistBtn and setup the recycler view for
     * the seasons
     * @param inflater Inflater is LayoutInflater
     * @param container Container is ViewGroup
     * @param savedInstanceState SavedInstanceState is Bundle
     * @return It return the View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv_details, container, false);
        context = getContext();
        hasBeenStored = false;

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_tv_details_imageView));
        backdropImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.backdrop_tv_details_imageView));
        name = (TextView) v.findViewById(R.id.name_tv_details_textView);
        rating = (TextView) v.findViewById(R.id.rating_tv_details_textView);
        releaseDate = (TextView) v.findViewById(R.id.release_date_tv_details_textView);
        runTime = (TextView) v.findViewById(R.id.run_time_tv_details_textView);
        overview = (TextView) v.findViewById(R.id.overview_tv_details_textView);
        genre = (TextView) v.findViewById(R.id.genre_tv_details_textView);
        watchlistBtn = (Button) v.findViewById(R.id.add_watchlist_tv_details_btn);

        RecyclerView tvSeasonsRecycler = (RecyclerView) v.findViewById(R.id.seasons_tv_details_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        tvSeasonsRecycler.setHasFixedSize(true);
        tvSeasonsRecycler.setLayoutManager(layoutManager);
        tvSeasonsAdapter = new TvSeasonsAdapter(context,getActivity().getSupportFragmentManager());
        tvSeasonsRecycler.setAdapter(tvSeasonsAdapter);

        if(time == null ) {
            time = new Time();
        }

        if(getArguments() != null){
            tvId = getArguments().getLong("tvId");
        }

        watchlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasBeenStored){
                    removeTvShow();

                }else {
                    addTvShow();
                }
            }
        });

        return v;
    }

    /**
     * Call changeButton function and
     * check whether the tvDetails is empty
     * if so call reqTvDetails function and
     * set the time it was called,
     * if not empty call displayData function.
     */
    @Override
    public void onStart() {
        super.onStart();
        if(tvDetails == null || time.isOverTime(time.ONE_HOUR)){
            time.setFirstTime(time.getTimeInMillis());
            reqTvDetails();
        }else{
            displayData();
        }
        changeButton();
    }

    /**
     * Sends Http Request that request Tv details.
     */
    private void reqTvDetails(){

        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.tvDetails(tvId,resTvDetails());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving respond from the backend server.
     * @return It return callback.
     */
    private Callback resTvDetails(){
        return new Callback<TvDetails>(){
            @Override
            public void onResponse(Call<TvDetails> call, Response<TvDetails> response) {
                if(response.isSuccessful()){
                    tvDetails = response.body();
                    displayData();
                }else {
                    PopUpMsg.displayErrorMsg(context);
                }
            }

            @Override
            public void onFailure(Call<TvDetails> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);
            }
        };
    }

    /**
     * Display the Tv details on the screen
     */
    private void displayData(){
        name.setText(tvDetails.getName());
        rating.setText(ConvertValue.toOneDecimal(tvDetails.getVoteAverage()));
        releaseDate.setText(tvDetails.getFirstAirDate());
        runTime.setText(String.format("%s Min", TextUtils.join(", ", tvDetails.getEpisodeRunTime())));
        overview.setText(tvDetails.getOverview());
        genre.setText(ConvertValue.genreToString(tvDetails.getGenres()));
        posterImg.setLargeImg(tvDetails.getPosterPath());
        backdropImg.setMediumImg(tvDetails.getBackdropPath());
        tvSeasonsAdapter.setSeasons(tvDetails.getSeasons(),tvId);
    }


    /**
     * Checks if tv show is added to watchlist and display
     * the right button.
     */
    private void changeButton(){
        if(TvDatabaseUtil.isTvAddedToWatchlist(tvId)){
            watchlistBtn.setBackgroundColor(0xffe6b800);
            watchlistBtn.setText(R.string.removeBtn);
            hasBeenStored = true;
        }else{
            watchlistBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            watchlistBtn.setText(R.string.addBtn);
            hasBeenStored = false;
        }
    }

    /**
     * Remove tv show  from watchlist and
     * call changeButton function.
     */
    private void removeTvShow(){
        TvDatabaseUtil.removeTvFromWatchlist(tvId);
        changeButton();
    }
    /**
     * Add tv show to watchlist and
     * call changeButton function.
     */
    private void addTvShow(){
        TvDatabaseUtil.addTvToWatchlist(
                tvId,
                tvDetails.getName(),
                tvDetails.getPosterPath(),
                tvDetails.getVoteAverage(),
                ConvertValue.genreIdToString(tvDetails.getGenres()));
        changeButton();
    }


}
