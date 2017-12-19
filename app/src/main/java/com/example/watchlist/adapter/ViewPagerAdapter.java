package com.example.watchlist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
 * ViewPagerAdapter is used to create tabs for the fragments.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    /**
     * Is the constructor for the ViewPagerAdapter
     * @param fm Fm is FragmentManager
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * It get the item in that position
     * @param object Object is object
     * @return It return int value POSITION_NONE
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /**
     * It get the fragment in that position
     * @param position Position is the position of the fragment
     * @return It return fragment in that position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * It get mFragmentList size
     * @return It return the mFragmentList size
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     *  It add fragment to mFragmentList and
     *  title to mFragmentTitleList
     * @param fragment Fragment is the fragment
     * @param title Title is the title of the fragment
     */
    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    /**
     * It get the title of the fragment
     * @param position Position is the position of the fragment
     * @return It return the title of the fragment
     */
    @Override
    public CharSequence getPageTitle(int position){
        return mFragmentTitleList.get(position);
    }
}
