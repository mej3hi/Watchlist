package com.example.watchlist.fragment.tvshows;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.watchlist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeasonDetailsFragment extends Fragment {
    private static final String TAG = "SeasonDetailsFragment";

    private long tvId;
    private int seasonNumber;


    public SeasonDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_season_details, container, false);

        if(getArguments() != null){
            tvId = getArguments().getLong("tvId");
            seasonNumber = getArguments().getInt("seasonNumber");
        }
        Log.d(TAG,"onStart id "+tvId+" s "+seasonNumber );

        return v;
    }

}
