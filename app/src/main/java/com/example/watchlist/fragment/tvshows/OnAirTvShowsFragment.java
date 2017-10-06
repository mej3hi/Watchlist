package com.example.watchlist.fragment.tvshows;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.R;
import com.example.watchlist.utils.PaginationScrollListener;
import com.example.watchlist.adapter.TvShowsAdapter;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.service.response.tvShows.ResOnAirTvShows;
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.PopUpMsg;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnAirTvShowsFragment extends Fragment {
    private static final String TAG ="OnAirTvShowsFrag";

    private Context context;
    private Calendar time;
    private ImageView poster;
    private TvShowsAdapter tvShowsAdapter;
    private long firstRegTime;
    private String posterPath;

    private boolean isLastPage;
    private boolean isLoading;
    private int totalPages;
    private int currentPage;


    public OnAirTvShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_on_air_tv_shows, container, false);

        context = getContext();
        time = Calendar.getInstance();

        poster = (ImageView) v.findViewById(R.id.poster_on_air_tv_shows_imageView);

        RecyclerView onAirTvShowsRecycler = (RecyclerView) v.findViewById(R.id.on_air_tv_shows_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        onAirTvShowsRecycler.setLayoutManager(layoutManager);
        if(tvShowsAdapter == null || firstRegTime +3600000 < time.getTimeInMillis()) {
            initialize();
        }
        onAirTvShowsRecycler.setAdapter(tvShowsAdapter);
        onAirTvShowsRecycler.addOnScrollListener(setPageScrollListener(layoutManager));

        Log.d(TAG,"onCreateView");
        return  v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        EventBus.getDefault().register(this);
        if(tvShowsAdapter.isEmpty()) {
            firstRegTime = time.getTimeInMillis();
            reqOnAirTvShows();
        }else{
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+posterPath).into(poster);
        }
    }


    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * Here we initialize the fragment
     */
    public void initialize(){
        firstRegTime = 0;
        isLastPage = false;
        isLoading = false;
        totalPages = 0;
        currentPage = 1;
        tvShowsAdapter = new TvShowsAdapter(context, getActivity().getSupportFragmentManager());
    }

    /**
     * It add a pagination scroll listener that ask for more data
     * if it is not the last page and not loading.
     * @param layoutManager LayoutManager contains the LinearLayoutManager.
     * @return It return the PaginationScrollListener.
     */
    private PaginationScrollListener setPageScrollListener(LinearLayoutManager layoutManager){
        return new PaginationScrollListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reqOnAirTvShows();
                    }
                },500);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };

    }

    /**
     * Sends HttpRequest that request On air tv shows
     */
    private void reqOnAirTvShows(){
        Log.d(TAG,"ná í on air tv shows");

        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.onAirTvShows(currentPage);
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving Respond from the backend server.
     * @param response Response has the Code and the On air tv shows from backend server.
     */
    @Subscribe
    public void resOnAirTvShows(ResOnAirTvShows response){
        Log.d(TAG,"Tókst ná í top on air tv shows");

        if(response.getCode() == 200){
            totalPages = response.getTvShowsResults().getTotalPages();

            isLoading = false;

            displayData(response.getTvShowsResults());

            currentPage++;

            Log.d(TAG,"isLastPage "+isLastPage+" isLoading "+isLoading);

        }else {

            String title ="Something went wrong";
            String msg = "Something went wrong, please try again";
            PopUpMsg.dialogMsg(title,msg,context);
        }

    }

    /**
     * Display the On air tv shows on the screen;
     * @param results Results contains Tv shows results.
     */
    public void displayData(TvShow.TvShowsResults results){
        if(!tvShowsAdapter.isEmpty()){
            tvShowsAdapter.removeLoadingFooter();
        }

        if(GerneList.getGenreTvList() != null){
            tvShowsAdapter.addAllGenre(GerneList.getGenreTvList());
        }
        if(tvShowsAdapter.isEmpty()){
            setRandomBackPoster(results.getResults());
        }
        tvShowsAdapter.addAll(results.getResults());

        if(currentPage < totalPages) tvShowsAdapter.addLoadingFooter();
        else isLastPage = true;

    }


    /**
     * It pick a random image and set it to the back poster.
     * @param list List contains the list of the Tv shows.
     */
    public void setRandomBackPoster(List<TvShow> list){
        Random r = new Random();
        int random = r.nextInt(list.size());
        posterPath = list.get(random).getPosterPath();
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+posterPath).into(poster);

    }


}
