package com.example.watchlist.fragment.tvshows;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.R;
import com.example.watchlist.utils.ImageHandler;
import com.example.watchlist.utils.Pagination;
import com.example.watchlist.utils.PaginationScrollListener;
import com.example.watchlist.adapter.TvShowsAdapter;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;

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
public class PopularTvShowsFragment extends Fragment {
    private static final String TAG ="PopularTvShowsFrag";

    private Context context;
    private Time time;
    private Pagination pagination;

    private ImageHandler posterImg;
    private TvShowsAdapter tvShowsAdapter;


    public PopularTvShowsFragment() {
        // Required empty public constructor
    }

    /**
     * It create the view for the popular tv shows and
     * get the context and also setup the recycler view
     * with page scroll listener, it also initialize the
     * fragment
     * @param inflater Inflater is LayoutInflater
     * @param container Container is ViewGroup
     * @param savedInstanceState SavedInstanceState is Bundle
     * @return It return the View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_popular_tv_shows, container, false);

        context = getContext();

        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_popular_tv_shows_imageView));

        RecyclerView popularTvShowsRecycler = (RecyclerView) v.findViewById(R.id.popular_tv_shows_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        popularTvShowsRecycler.setLayoutManager(layoutManager);
        if(tvShowsAdapter == null || time == null || pagination == null || time.isOverTime(time.ONE_HOUR)) {
            initialize();
        }
        popularTvShowsRecycler.setAdapter(tvShowsAdapter);
        popularTvShowsRecycler.addOnScrollListener(setPageScrollListener(layoutManager));

        return v;
    }

    /**
     * Check whether the tvShowsAdapter is empty
     * if so call reqPopularTvShows function and
     * set the time it was called,
     * if not empty set random poster image.
     */
    @Override
    public void onStart() {
        super.onStart();
        if(tvShowsAdapter.isEmpty()) {
            time.setFirstTime(time.getTimeInMillis());
            reqPopularTvShows();
        }else{
            int r = new Random().nextInt(tvShowsAdapter.getTvShowList().size());
            posterImg.setLargeImg(tvShowsAdapter.getTvShowList().get(r).getPosterPath());
        }

    }

    /**
     * Initialize the fragment
     */
    private void initialize(){
        time = new Time();
        tvShowsAdapter = new TvShowsAdapter(context, getActivity().getSupportFragmentManager());
        pagination = new Pagination();
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
                pagination.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reqPopularTvShows();
                    }
                },500);
            }

            @Override
            public boolean isLastPage() {
                return pagination.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return pagination.isLoading();
            }
        };

    }

    /**
     * Sends Http Request that request Popular tv shows.
     */
    private void reqPopularTvShows(){

        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.popularTvShows(pagination.getCurrentPage(),resPopularTvShows());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving respond from the backend server.
     * @return It return callback.
     */
    private Callback resPopularTvShows(){
        return new Callback<TvShow.TvShowsResults>(){
            @Override
            public void onResponse(Call<TvShow.TvShowsResults> call, Response<TvShow.TvShowsResults> response) {
                if(response.isSuccessful()){
                    pagination.setTotalPages(response.body().getTotalPages());

                    pagination.setLoading(false);

                    displayData(response.body());

                    pagination.setCurrentPage(pagination.getCurrentPage()+1);

                }else {
                    PopUpMsg.displayErrorMsg(context);
                 }
            }

            @Override
            public void onFailure(Call<TvShow.TvShowsResults> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);
            }
        };
    }


    /**
     * Display the Popular tv shows on the screen;
     * @param results Results contains Tv shows results.
     */
    private void displayData(TvShow.TvShowsResults results){
        if(!tvShowsAdapter.isEmpty()){
            tvShowsAdapter.removeLoadingFooter();
        }

        if(GerneList.getGenreTvList() != null){
            tvShowsAdapter.addAllGenre(GerneList.getGenreTvList());
        }
        if(tvShowsAdapter.isEmpty()){
            int r = new Random().nextInt(results.getResults().size());
            posterImg.setLargeImg(results.getResults().get(r).getPosterPath());
        }

        tvShowsAdapter.addAll(results.getResults());

        if(pagination.getCurrentPage() < pagination.getTotalPages()) tvShowsAdapter.addLoadingFooter();
        else pagination.setLastPage(true);

    }



}
