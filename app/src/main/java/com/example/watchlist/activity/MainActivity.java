package com.example.watchlist.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.watchlist.R;
import com.example.watchlist.fragment.movie.MoviesTabFragment;
import com.example.watchlist.fragment.myWatchlist.MyWatchlistTabFragment;
import com.example.watchlist.fragment.search.SearchResultsFragment;
import com.example.watchlist.fragment.tvshows.TvShowsTapFragment;
import com.example.watchlist.fragment.watchlist.WatchlistTabFragment;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqGenre;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.utils.PopUpMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private MenuItem searchTv;
    private MenuItem searchMovie;

    /**
     * It crate the main view where all the fragment life in.
     * It add DrawerLayout to it and set listener to the navigation View
     * and make the first item checked in it.
     * @param savedInstanceState SavedInstanceState is bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.getMenu().getItem(0).setChecked(true);


    }

    /**
     * It handle the on back pressed.
     * It closed the navigation drawer if it is open
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * It handle options menu.
     * It add query text listener for search view and
     * open search results fragment if submitted
     * @param menu Menu is menu
     * @return It return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        searchTv = menu.findItem(R.id.search_tv);
        searchMovie = menu.findItem(R.id.search_movie);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchResultsFragment fragment = new SearchResultsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("searchQuery",query);
                bundle.putBoolean("searchTv",searchTv.isChecked());
                bundle.putBoolean("searchMovie",searchMovie.isChecked());
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                manager.popBackStack("search",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                manager.beginTransaction().replace(R.id.main_container, fragment, fragment.getTag()).addToBackStack("search").commit();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    /**
     * It handle the options selected.
     * It setChecked true or false for searchMovie and search_tv
     * and it also makes sure that at least one of
     * the item are checked
     * @param item Item is MenuItem
     * @return It return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.search_tv) {
            if(item.isChecked()){
                item.setChecked(false);
                searchMovie.setChecked(true);
            }else{
                item.setChecked(true);
            }
            return true;
        }

        if (id == R.id.search_movie) {
            if(item.isChecked()){
                item.setChecked(false);
                searchTv.setChecked(true);
            }else{
                item.setChecked(true);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * It handle the navigation selection
     * and open new fragment from it.
     * @param item Item is MenuItem
     * @return It return true
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();

        manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (id == R.id.tv_shows) {
            TvShowsTapFragment tvShowsTapFragment = new TvShowsTapFragment();
            manager.beginTransaction().replace(R.id.main_container,tvShowsTapFragment,tvShowsTapFragment.getTag()).commit();

        } else if (id == R.id.movies) {
            MoviesTabFragment moviesTabFragment = new MoviesTabFragment();
            manager.beginTransaction().replace(R.id.main_container,moviesTabFragment,moviesTabFragment.getTag()).commit();


        } else if (id == R.id.my_watchlist) {
            MyWatchlistTabFragment myWatchlistTabFragment = new MyWatchlistTabFragment();
            manager.beginTransaction().replace(R.id.main_container,myWatchlistTabFragment,myWatchlistTabFragment.getTag()).commit();

        }else if (id == R.id.watchlist) {
            WatchlistTabFragment watchlistTabFragment = new WatchlistTabFragment();
            manager.beginTransaction().replace(R.id.main_container, watchlistTabFragment, watchlistTabFragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Call reqGenre function
     */
    @Override
    protected void onStart() {
        super.onStart();
        reqGenre();

    }

    /**
     * Sends Http Request that requests Genre.
     */
    private void reqGenre(){

        if(NetworkChecker.isOnline(getApplicationContext())) {
            ReqGenre.genreTvList(resGenreTvShows());
            ReqGenre.genreMovieList(resGenreMovie());

        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",getApplicationContext());
        }

    }

    /**
     * Receiving Respond from the backend server.
     * @return It return callback
     */
    public Callback resGenreTvShows() {
        return new Callback<Genre.GenreResults>() {
            @Override
            public void onResponse(Call<Genre.GenreResults> call, Response<Genre.GenreResults> response) {
                if (response.isSuccessful()) {
                    GerneList.setGenreTvList(response.body().getResults());

                }
            }

            @Override
            public void onFailure(Call<Genre.GenreResults> call, Throwable t) {

            }
        };
    }

    /**
     * Receiving Respond from the backend server.
     * @return It return callback
     */
    public Callback resGenreMovie() {
        return new Callback<Genre.GenreResults>() {
            @Override
            public void onResponse(Call<Genre.GenreResults> call, Response<Genre.GenreResults> response) {
                if (response.isSuccessful()) {
                    GerneList.setGenreMovieList(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<Genre.GenreResults> call, Throwable t) {


            }
        };

    }


}
