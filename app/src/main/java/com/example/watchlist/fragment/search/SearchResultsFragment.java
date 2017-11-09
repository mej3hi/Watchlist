package com.example.watchlist.fragment.search;


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
public class SearchResultsFragment extends Fragment {
    private static final String TAG = "SearchResultsFragment";

    private boolean searchTv;
    private boolean searchMovie;

    private String searchQuery;


    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_results, container, false);

        if(getArguments() != null){
            searchQuery = getArguments().getString("searchQuery");
            searchTv = getArguments().getBoolean("searchTv");
            searchMovie = getArguments().getBoolean("searchMovie");
        }

        Log.d(TAG,"Search query "+searchQuery);
        Log.d(TAG,"Search tv "+searchTv);
        Log.d(TAG,"Search movie "+searchMovie);
        return v;
    }

}
