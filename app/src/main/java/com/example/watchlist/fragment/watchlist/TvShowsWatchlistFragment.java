package com.example.watchlist.fragment.watchlist;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.watchlist.utils.BackgroundPoster;
import com.example.watchlist.utils.Pagination;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowsWatchlistFragment extends Fragment {
    private static final String TAG ="TvShowsWatchFrag";

    private Context context;
    private Time time;
    private Pagination pagination;
    private List<TvShowsWatch> tvShowsWatchList;

    private ImageView poster;
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

        poster = (ImageView) v.findViewById(R.id.poster_watchlist_tv_shows_imageView);
        time = new Time();
        pagination = new Pagination();

        RecyclerView onAirTvShowsRecycler = (RecyclerView) v.findViewById(R.id.watchlist_tv_shows_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        onAirTvShowsRecycler.setLayoutManager(layoutManager);
        tvShowsAdapter = new TvShowsAdapter(context, getActivity().getSupportFragmentManager());
        onAirTvShowsRecycler.setAdapter(tvShowsAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        tvShowsWatchList = TvDatabaseUtil.getallTvShows();
        if(!Cache.TodayTvshows.isEmpty() && !time.isOverTime(Cache.TodayTvshows.getTime(),time.ONE_HOUR)){
            displayData(Cache.TodayTvshows.getTvShowList());
        }else{
            Cache.TodayTvshows.clear();
            Cache.TodayTvshows.setTime(time.getTimeInMillis());
            reqToDayShows();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
    }


    /**
     * Sends HttpRequest that request On air tv shows
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
     * Receiving Respond from the backend server.
     * @return It return Callback.
     */
    public Callback resToDayShows(){
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

    public List<TvShow> filterOutData(List<TvShow> tvShowList, List<TvShowsWatch> tvShowsWatchList){
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
    public void displayData(List<TvShow> tvShowList){

        List<TvShow> newTvList = filterOutData(tvShowList,tvShowsWatchList);

        if(newTvList.size() != 0) {

            if (GerneList.getGenreTvList() != null) {
                tvShowsAdapter.addAllGenre(GerneList.getGenreTvList());
            }
            if (tvShowsAdapter.isEmpty()) {
                BackgroundPoster.setRandomBackPosterTv(newTvList, context, poster);
            }
            tvShowsAdapter.addAll(newTvList);

        }

    }






}
