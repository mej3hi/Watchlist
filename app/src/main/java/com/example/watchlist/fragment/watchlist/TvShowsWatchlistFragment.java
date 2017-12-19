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
import com.example.watchlist.adapter.TvShowsAdapter;
import com.example.watchlist.database.TvDatabaseUtil;
import com.example.watchlist.database.TvShowsWatch;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.shareInfo.Cache;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.TvShow;
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
public class TvShowsWatchlistFragment extends Fragment {
    private static final String TAG ="TvShowsWatchFrag";

    private Context context;
    private Time time;
    private Pagination pagination;
    private List<TvShowsWatch> tvShowsWatchList;

    private ImageHandler posterImg;
    private TvShowsAdapter tvShowsAdapter;

    public TvShowsWatchlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv_shows_watchlist, container, false);

        context = getContext();

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_watchlist_tv_shows_imageView));
        time = new Time();
        pagination = new Pagination();

        RecyclerView onAirTvShowsRecycler = (RecyclerView) v.findViewById(R.id.watchlist_tv_shows_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        onAirTvShowsRecycler.setLayoutManager(layoutManager);
        tvShowsAdapter = new TvShowsAdapter(context, getActivity().getSupportFragmentManager());
        onAirTvShowsRecycler.setAdapter(tvShowsAdapter);

        return v;
    }

    /**
     * Get all tv shows that user has saved in watchlist.
     * Check whether Cache.TodayTvshows is not empty and
     * is not over time, if so call displayData function
     * with data from Cache.TodayTvshows,
     * else clear it and set time and call reqToDayShows function
     */
    @Override
    public void onStart() {
        super.onStart();
        tvShowsWatchList = TvDatabaseUtil.getAllTvShows();
        if(!Cache.TodayTvshows.isEmpty() && !time.isOverTime(Cache.TodayTvshows.getTime(),time.ONE_HOUR)){
            displayData(Cache.TodayTvshows.getTvShowList());
        }else{
            Cache.TodayTvshows.clear();
            Cache.TodayTvshows.setTime(time.getTimeInMillis());
            reqToDayShows();
        }
    }


    /**
     * Sends HttpRequest that request today tv shows
     */
    private void reqToDayShows(){

        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.toDayShows(pagination.getCurrentPage(),resToDayShows());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving respond from the backend server.
     * @return It return Callback.
     */
    private Callback resToDayShows(){
        return new Callback<TvShow.TvShowsResults>(){
            @Override
            public void onResponse(Call<TvShow.TvShowsResults> call, Response<TvShow.TvShowsResults> response) {
                if(response.isSuccessful()){
                    pagination.setTotalPages(response.body().getTotalPages());

                    Cache.TodayTvshows.addToTvShows(response.body().getResults());

                    pagination.setCurrentPage(pagination.getCurrentPage()+1);

                    if (pagination.getCurrentPage() > pagination.getTotalPages()){
                        displayData(Cache.TodayTvshows.getTvShowList());

                    }else{
                        reqToDayShows();
                    }

                }else {
                    PopUpMsg.displayErrorMsg(context);
                }
            }

            @Override
            public void onFailure(Call<TvShow.TvShowsResults> call, Throwable t) {
                t.printStackTrace();
                PopUpMsg.displayErrorMsg(context);
            }
        };
    }

    /**
     * Filter out the tv show that is not in the user watchlist and
     * return a new list.
     * @param tvShowList List of TvShow
     * @param tvShowsWatchList List of TvShowsWatch
     * @return return a list of TvShow
     */
    private List<TvShow> filterOutData(List<TvShow> tvShowList, List<TvShowsWatch> tvShowsWatchList){
        List<TvShow> newTvShowList = new ArrayList<>();

        for (TvShow tvShow : tvShowList){
            for(TvShowsWatch watch : tvShowsWatchList){
                if(tvShow.getId() == watch.getTvId()){
                    newTvShowList.add(tvShow);
                    break;
                }
            }
        }
        return newTvShowList;
    }


    /**
     * Display the On air tv shows on the screen;
     * @param tvShowList tvShowList contains Tv shows results.
     */
    private void displayData(List<TvShow> tvShowList){

        List<TvShow> newTvList = filterOutData(tvShowList,tvShowsWatchList);

        if(newTvList.size() != 0) {

            if (GerneList.getGenreTvList() != null) {
                tvShowsAdapter.addAllGenre(GerneList.getGenreTvList());
            }
            if (tvShowsAdapter.isEmpty()) {
                int r = new Random().nextInt(newTvList.size());
                posterImg.setLargeImg(newTvList.get(r).getPosterPath());
            }
            tvShowsAdapter.addAll(newTvList);

        }

    }






}
