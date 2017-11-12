package com.example.watchlist.fragment.tvshows;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvDetailsFragment extends Fragment {
    private static final String TAG ="TvDetailsFrag";

    private Context context;
    private Time time;

    private TvDetails tvDetails;
    private long tvId;
    private boolean hasBeenStored;

    private TvSeasonsAdapter tvSeasonsAdapter;
    private ImageView poster;
    private ImageView backdrop;
    private TextView name;
    private TextView rating;
    private TextView releaseDate;
    private TextView runTime;
    private TextView overview;
    private TextView genre;
    private Button watchlistBtn;




    public TvDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv_details, container, false);
        context = getContext();
        hasBeenStored = false;

        poster = (ImageView) v.findViewById(R.id.poster_tv_details_imageView);
        backdrop = (ImageView) v.findViewById(R.id.backdrop_tv_details_imageView);
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
        if(time == null ) {
            time = new Time();
        }
        tvSeasonsAdapter = new TvSeasonsAdapter(context,getActivity().getSupportFragmentManager());
        tvSeasonsRecycler.setAdapter(tvSeasonsAdapter);

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

        Log.d(TAG,"onCreateView" );
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        if(tvDetails == null || time.isOverTime(time.ONE_HOUR)){
            reqTvDetails();
            time.setFirstTime(time.getTimeInMillis());
        }else{
            displayData();
        }

        changeButton();
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }



    /**
     * Sends HttpRequest that request Tv details.
     */
    private void reqTvDetails(){
        Log.d(TAG,"ná í Tv Details");

        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.tvDetails(tvId,resTvDetails());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving Respond from the backend server.
     * @return It return Callback.
     */
    public Callback resTvDetails(){
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
     * Display the Tv details on the screen;
     *
     */
    public void displayData(){
        name.setText(tvDetails.getName());
        rating.setText(ConvertValue.toOneDecimal(tvDetails.getVoteAverage()));
        releaseDate.setText(tvDetails.getFirstAirDate());
        runTime.setText(TextUtils.join(", ",tvDetails.getEpisodeRunTime())+" Min");
        overview.setText(tvDetails.getOverview());
        genre.setText(ConvertValue.genreToString(tvDetails.getGenres()));
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+tvDetails.getBackdropPath()).into(backdrop);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+tvDetails.getPosterPath()).into(poster);
        //Collections.reverse(tvDetails.getSeasons());
        tvSeasonsAdapter.setSeasons(tvDetails.getSeasons(),tvId);
    }



    public void changeButton(){
        if(TvDatabaseUtil.isTvAddedToWatchlist(tvId)){
            watchlistBtn.setBackgroundColor(0xffe6b800);
            watchlistBtn.setText("Remove from watchlist");
            hasBeenStored = true;
        }else{
            watchlistBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            watchlistBtn.setText("Add to watchlist");
            hasBeenStored = false;
        }
    }

    public void removeTvShow(){
        TvDatabaseUtil.removeTvFromWatchlist(tvId);
        changeButton();
    }

    public void addTvShow(){
        TvDatabaseUtil.addTvToWatchlist(
                tvId,
                tvDetails.getName(),
                tvDetails.getPosterPath(),
                tvDetails.getVoteAverage(),
                ConvertValue.genreIdToString(tvDetails.getGenres()));
        changeButton();
    }


}
