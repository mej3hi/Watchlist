package com.example.watchlist.fragment.myWatchlist;


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
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.TvShow;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class TvShowsMyWatchlistFragment extends Fragment {
    private static final String TAG = "TvShowsMyWatchlistFrag";

    private Context context;

    private ImageHandler posterImg;
    private TvShowsAdapter tvShowsAdapter;


    public TvShowsMyWatchlistFragment() {
        // Required empty public constructor
    }

    /**
     * It create the view for the tv shows my watchlist and
     * get the context and add the recycler view to it
     * @param inflater Inflater is LayoutInflater
     * @param container Container is ViewGroup
     * @param savedInstanceState SavedInstanceState is Bundle
     * @return It return the View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        context = getContext();
        View v = inflater.inflate(R.layout.fragment_tv_shows_my_watchlist, container, false);
        posterImg = new ImageHandler(context,(ImageView) v.findViewById(R.id.poster_my_watchlist_tv_shows_imageView));
        RecyclerView tvShowsRecycler = (RecyclerView) v.findViewById(R.id.my_watchlist_tv_shows_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        tvShowsRecycler.setLayoutManager(layoutManager);
        tvShowsAdapter = new TvShowsAdapter(context,getActivity().getSupportFragmentManager());
        tvShowsRecycler.setAdapter(tvShowsAdapter);

        return v;
    }

    /**
     * Call the getAllData function
     * and display it
     */
    @Override
    public void onStart() {
        super.onStart();
        displayData(getAllData());
    }

    /**
     * Get all the tv shows that user has saved in watchlist
     * @return It returns a list of tv shows
     */
    private List<TvShow>getAllData(){
        List<TvShow> newTvShowList = new ArrayList<>();
        TvShow tvShow;
        for (TvShowsWatch t : TvDatabaseUtil.getAllTvShows()) {
            tvShow = new TvShow(
                    t.getTvId(),
                    t.getName(),
                    t.getPosterPath(),
                    t.getRating(),
                    ConvertValue.genreIdToListInteger(t.getGenre()));
            newTvShowList.add(tvShow);
        }
        return newTvShowList;

    }

    /**
     * Display the user watchlist on the screen;
     * @param watchList Results contains list of Tv shows.
     */
    private void displayData(List<TvShow> watchList){
        if(GerneList.getGenreTvList() != null){
            tvShowsAdapter.addAllGenre(GerneList.getGenreTvList());
        }

        if(tvShowsAdapter.isEmpty() && watchList.size() != 0) {
            int r = new Random().nextInt(watchList.size());
            posterImg.setLargeImg(watchList.get(r).getPosterPath());
        }
        tvShowsAdapter.addAll(watchList);
    }

}
