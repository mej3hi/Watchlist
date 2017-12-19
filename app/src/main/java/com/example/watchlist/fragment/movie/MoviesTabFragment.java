package com.example.watchlist.fragment.movie;


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
public class MoviesTabFragment extends Fragment {

    private static final String TAG = "MoviesTabFrag";

    private RatedMoviesFragment ratedMoviesFragment;
    private NowPlayingMoviesFragment nowPlayingMoviesFragment;
    private PopularMoviesFragment popularMoviesFragment;
    private UpcomingMoviesFragment upcomingMoviesFragment;


    private TabLayout moviesTab;
    private ViewPager moviesViewPager;
    private ViewPagerAdapter adapter;

    public MoviesTabFragment() {
        // Required empty public constructor
    }

    /**
     * It create the view for the movie tab and
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
        View v = inflater.inflate(R.layout.fragment_movies_tab, container, false);

        ratedMoviesFragment = new RatedMoviesFragment();
        nowPlayingMoviesFragment = new NowPlayingMoviesFragment();
        popularMoviesFragment = new PopularMoviesFragment();
        upcomingMoviesFragment = new UpcomingMoviesFragment();


        moviesViewPager = (ViewPager) v.findViewById(R.id.movies_viewpager);
        moviesTab = (TabLayout) v.findViewById(R.id.movies_tabs);
        setupViewPager();

        return v;
    }

    /**
     * It setup View page adapter and add the fragment to it.
     */
    private void setupViewPager(){
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ratedMoviesFragment, "TOP RATED");
        adapter.addFragment(popularMoviesFragment, "POPULAR");
        adapter.addFragment(upcomingMoviesFragment, "UPCOMING");
        adapter.addFragment(nowPlayingMoviesFragment, "NOW PLAYING");

        moviesViewPager.setAdapter(adapter);
        moviesTab.setupWithViewPager(moviesViewPager);

    }

}
