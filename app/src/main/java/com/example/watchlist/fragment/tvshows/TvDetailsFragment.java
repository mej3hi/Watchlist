package com.example.watchlist.fragment.tvshows;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.watchlist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvDetailsFragment extends Fragment {


    public TvDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_details, container, false);
    }

}
