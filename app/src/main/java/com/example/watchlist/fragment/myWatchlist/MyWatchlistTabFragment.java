package com.example.watchlist.fragment.myWatchlist;


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
public class MyWatchlistTabFragment extends Fragment {
    private static final String TAG = "MyWatchlistTabFrag";

    private TvShowsMyWatchlistFragment TvShowsMyWatchlistFragment;
    private MoviesMyWatchlistFragment MoviesMyWatchlistFragment;

    private TabLayout myWatchlistTap;
    private ViewPager myWatchlistViewPager;
    private ViewPagerAdapter adapter;


    public MyWatchlistTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_watchlist_tab, container, false);

        TvShowsMyWatchlistFragment = new TvShowsMyWatchlistFragment();
        MoviesMyWatchlistFragment = new MoviesMyWatchlistFragment();

        myWatchlistViewPager = (ViewPager) v.findViewById(R.id.my_watchlist_viewpager);
        myWatchlistTap = (TabLayout) v.findViewById(R.id.my_watchlist_tabs);
        setupViewPager();

        return v;
    }

    /**
     * It setup View page adapter and add the fragment to it.
     */
    private void setupViewPager(){
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(TvShowsMyWatchlistFragment, "MY TV SHOWS");
        adapter.addFragment(MoviesMyWatchlistFragment,"MY MOVIES");
        myWatchlistViewPager.setAdapter(adapter);
        myWatchlistTap.setupWithViewPager(myWatchlistViewPager);
    }

}
