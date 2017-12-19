package com.example.watchlist.fragment.watchlist;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.watchlist.R;
import com.example.watchlist.adapter.ViewPagerAdapter;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class WatchlistTabFragment extends Fragment {
    private static final String TAG = "WatchlistTabFrag";

    private MoviesWatchlistFragment moviesWatchlistFragment;
    private TvShowsWatchlistFragment tvShowsWatchlistFragment;

    private TabLayout watchlistTab;
    private ViewPager watchlistViewPager;
    private ViewPagerAdapter adapter;



    public WatchlistTabFragment() {
        // Required empty public constructor
    }

    /**
     * It create the view for the watchlist tab and
     * add the fragment to it
     * @param inflater Inflater is LayoutInflater
     * @param container Container is ViewGroup
     * @param savedInstanceState SavedInstanceState is Bundle
     * @return It return the View for the fragment's UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watchlist_tab, container, false);

        moviesWatchlistFragment = new MoviesWatchlistFragment();
        tvShowsWatchlistFragment = new TvShowsWatchlistFragment();

        watchlistViewPager = (ViewPager) view.findViewById(R.id.today_viewpager);
        watchlistTab = (TabLayout) view.findViewById(R.id.today_tabs);
        setupViewPager();

        return view;
    }

    /**
     * It setup View page adapter and add the fragment to it.
     */
    private void setupViewPager(){
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(tvShowsWatchlistFragment, "TODAY SHOWS");
        adapter.addFragment(moviesWatchlistFragment, "IN THEATERS");
        watchlistViewPager.setAdapter(adapter);
        watchlistTab.setupWithViewPager(watchlistViewPager);
    }

}
