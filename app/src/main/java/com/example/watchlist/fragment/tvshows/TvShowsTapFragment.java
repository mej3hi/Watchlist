package com.example.watchlist.fragment.tvshows;


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
public class TvShowsTapFragment extends Fragment {
    private static final String TAG = "TvShowsTapFrag";

    private RatedTvShowsFragment ratedTvShowsFragment;
    private PopularTvShowsFragment popularTvShowsFragment;
    private OnAirTvShowsFragment onAirTvShowsFragment;
    private TodayTvShowsFragment todayTvShowsFragment;

    private TabLayout tvShowsTab;
    private ViewPager tvShowsViewPager;
    private ViewPagerAdapter adapter;


    public TvShowsTapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv_shows_tap, container, false);

        ratedTvShowsFragment = new RatedTvShowsFragment();
        popularTvShowsFragment = new PopularTvShowsFragment();
        onAirTvShowsFragment = new OnAirTvShowsFragment();
        todayTvShowsFragment = new TodayTvShowsFragment();

        tvShowsViewPager = (ViewPager) v.findViewById(R.id.tv_shows_viewpager);
        tvShowsTab = (TabLayout) v.findViewById(R.id.tv_shows_tabs);
        setupViewPager();

        return v;
    }

    /**
     * It setup View page adapter and add the fragment to it.
     */
    private void setupViewPager(){
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ratedTvShowsFragment, "TOP RATED");
        adapter.addFragment(popularTvShowsFragment, "POPULAR");
        adapter.addFragment(onAirTvShowsFragment, "ON AIR");
        adapter.addFragment(todayTvShowsFragment, "TODAY");
        tvShowsViewPager.setAdapter(adapter);
        tvShowsTab.setupWithViewPager(tvShowsViewPager);

    }

}
