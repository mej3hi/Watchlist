package com.example.watchlist.fragment.movie;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.watchlist.R;
import com.example.watchlist.adapter.MoviesAdapter;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqMovies;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.utils.BackgroundPoster;
import com.example.watchlist.utils.Pagination;
import com.example.watchlist.utils.PaginationScrollListener;
import com.example.watchlist.utils.PopUpMsg;
import com.example.watchlist.utils.Time;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMoviesFragment extends Fragment {

    private static final String TAG ="UpcomingMoviesFrag";

    private Context context;
    private Time time;
    private Pagination pagination;
    private ImageView poster;
    private MoviesAdapter moviesAdapter;

    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upcoming_movies, container, false);

        context = getContext();

        poster = (ImageView) v.findViewById(R.id.poster_upcoming_movies_imageView);

        RecyclerView upcomingMoviesRecycler = (RecyclerView) v.findViewById(R.id.upcoming_movies_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        upcomingMoviesRecycler.setLayoutManager(layoutManager);
        if(moviesAdapter == null || time == null ||time.isOverTime(time.ONE_HOUR) || pagination == null) {
            initialize();
        }
        upcomingMoviesRecycler.setAdapter(moviesAdapter);
        upcomingMoviesRecycler.addOnScrollListener(setPageScrollListener(layoutManager));


        Log.d(TAG,"onCreateView");

        return v;
    }

    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        if(moviesAdapter.isEmpty()){
            reqUpcomingMovies();
        }
        else BackgroundPoster.setRandomBackPosterMovie(moviesAdapter.getMovieList(), context, poster);
    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    /**
     * Here we initialize the fragment
     */
    public void initialize(){
        time = new Time();
        moviesAdapter = new MoviesAdapter(context, getActivity().getSupportFragmentManager());
        pagination = new Pagination();
    }

    /**
     * It add a pagination scroll listener that ask for more data
     * if it is not the last page and not loading.
     * @param layoutManager LayoutManager contains the LinearLayoutManager.
     * @return It return the PaginationScrollListener.
     */
    private PaginationScrollListener setPageScrollListener(LinearLayoutManager layoutManager){
        return new  PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                pagination.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reqUpcomingMovies();
                    }
                },500);
            }

            @Override
            public boolean isLastPage() {
                return pagination.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return pagination.isLoading();
            }
        };

    }
    /**
     * Sends HttpRequest that request Upcoming Movies
     */
    private void reqUpcomingMovies(){
        Log.d(TAG,"ná í upcoming movies");

        if(NetworkChecker.isOnline(context)) {
            ReqMovies.upcomingMovies(pagination.getCurrentPage(), resUpcomingMovies());
        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",context);
        }

    }

    /**
     * Receiving Respond from the backend server.
     *
     */
    public Callback resUpcomingMovies(){
        return new Callback<Movie.MoviesResults>(){
            @Override
            public void onResponse(Call<Movie.MoviesResults> call, Response<Movie.MoviesResults> response) {
                if(response.isSuccessful()){
                    pagination.setTotalPages(response.body().getTotalPages());

                    pagination.setLoading(false);

                    displayData(response.body());

                    pagination.setCurrentPage(pagination.getCurrentPage()+1);

                    Log.d(TAG,"isLastPage "+pagination.isLastPage()+" isLoading "+pagination.isLoading());

                }else {
                    PopUpMsg.displayErrorMsg(context);
                }


            }

            @Override
            public void onFailure(Call<Movie.MoviesResults> call, Throwable t) {
                PopUpMsg.displayErrorMsg(context);

            }
        };
    }

    /**
     * Display the Upcoming movies on the screen;
     * @param results Results contains movies results.
     */
    public void displayData(Movie.MoviesResults results){
        if(!moviesAdapter.isEmpty()){
            moviesAdapter.removeLoadingFooter();
        }

        if(GerneList.getGenreMovieList() != null){
            moviesAdapter.addAllGenre(GerneList.getGenreMovieList());
        }

        if(moviesAdapter.isEmpty()){
            BackgroundPoster.setRandomBackPosterMovie(results.getResults(), context, poster);
        }

        moviesAdapter.addAll(results.getResults());

        if(pagination.getCurrentPage() < pagination.getTotalPages()) moviesAdapter.addLoadingFooter();
        else pagination.setLastPage(true);

    }

}
