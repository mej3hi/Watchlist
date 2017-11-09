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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.watchlist.R;
import com.example.watchlist.fragment.movie.MoviesTabFragment;
import com.example.watchlist.fragment.search.SearchResultsFragment;
import com.example.watchlist.fragment.tvshows.TvShowsTapFragment;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqGenre;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.utils.PopUpMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private MenuItem searchTv;
    private MenuItem searchMovie;

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

                Log.d(TAG,"onQueryTextSubmit");

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
                Log.d(TAG,"onQueryTextChange");
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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


        }
        Log.d("onNavigationItem","BINGO");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mainActivity","OnStart");
        reqGenre();

    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }


    /**
     * Sends HttpRequest that requests Genre.
     */
    private void reqGenre(){
        Log.d(TAG,"ná í genre");

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
     *
     */
    @Subscribe(priority = 1)
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
     *
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
