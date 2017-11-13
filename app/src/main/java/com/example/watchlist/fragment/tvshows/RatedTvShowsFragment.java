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
import com.example.watchlist.utils.BackgroundPoster;
import com.example.watchlist.utils.Pagination;
import com.example.watchlist.utils.PaginationScrollListener;
import com.example.watchlist.adapter.TvShowsAdapter;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqTvShows;
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatedTvShowsFragment extends Fragment {
    private static final String TAG ="RatedTvShowsFrag";

    private Context context;
    private Time time;
    private Pagination pagination;
    private ImageView poster;
    private TvShowsAdapter tvShowsAdapter;


    public RatedTvShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rated_tv_shows, container, false);
        context = getContext();

        poster = (ImageView) v.findViewById(R.id.poster_rated_tv_shows_imageView);

        RecyclerView ratedTvShowsRecycler = (RecyclerView) v.findViewById(R.id.rated_tv_shows_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        ratedTvShowsRecycler.setLayoutManager(layoutManager);
        if(tvShowsAdapter == null || time == null || pagination == null ||time.isOverTime(time.ONE_HOUR)) {
            initialize();
        }
        ratedTvShowsRecycler.setAdapter(tvShowsAdapter);
        ratedTvShowsRecycler.addOnScrollListener(setPageScrollListener(layoutManager));

        return v;
    }

    public void onStart() {
        super.onStart();
        if(tvShowsAdapter.isEmpty()){
            time.setFirstTime(time.getTimeInMillis());
            reqRatedTvShows();
        }else{
            BackgroundPoster.setRandomBackPosterTv(tvShowsAdapter.getTvShowList(), context, poster);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Initialize the fragment
     */
    public void initialize(){
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
        return new  PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                pagination.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reqRatedTvShows();
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
     * Sends Http Request that request Rated tv shows
     */
    private void reqRatedTvShows(){

        if(NetworkChecker.isOnline(context)) {
            ReqTvShows.ratedTvShows(pagination.getCurrentPage(),resRatedTvShows());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving Respond from the backend server.
     * @return It return Callback.
     */
    public Callback resRatedTvShows(){
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
     * Display the Rated tv shows on the screen;
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
            BackgroundPoster.setRandomBackPosterTv(results.getResults(), context, poster);
        }

        tvShowsAdapter.addAll(results.getResults());

        if(pagination.getCurrentPage() < pagination.getTotalPages()) tvShowsAdapter.addLoadingFooter();
        else pagination.setLastPage(true);


    }

}
