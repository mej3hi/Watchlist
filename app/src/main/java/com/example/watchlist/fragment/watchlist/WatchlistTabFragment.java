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
 * A simple {@link Fragment} subclass.
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

    private void setupViewPager(){
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(tvShowsWatchlistFragment, "TODAY SHOWS");
        adapter.addFragment(moviesWatchlistFragment, "IN THEATERS");
        watchlistViewPager.setAdapter(adapter);
        watchlistTab.setupWithViewPager(watchlistViewPager);
    }

}
