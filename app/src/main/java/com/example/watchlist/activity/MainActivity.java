package com.example.watchlist.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.watchlist.R;
import com.example.watchlist.fragment.tvshows.TvShowsTapFragment;
import com.example.watchlist.service.client.NetworkChecker;
import com.example.watchlist.service.request.ReqGenre;
import com.example.watchlist.service.response.tvShows.ResTvGenre;
import com.example.watchlist.shareInfo.GerneList;
import com.example.watchlist.utils.PopUpMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.tv_shows) {
            TvShowsTapFragment tvShowsTapFragment = new TvShowsTapFragment();
            manager.beginTransaction().replace(R.id.main_container,tvShowsTapFragment,tvShowsTapFragment.getTag()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mainActivity","OnStart");
        EventBus.getDefault().register(this);
        reqGenre();

    }

    @Override
    public void onStop() {
        Log.d(TAG,"onStop");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    /**
     * Sends HttpRequest that requests Genre.
     */
    private void reqGenre(){
        Log.d(TAG,"ná í genre");

        if(NetworkChecker.isOnline(getApplicationContext())) {
            ReqGenre.genreTvList();

        }
        else {
            PopUpMsg.toastMsg("Network isn't avilable",getApplicationContext());
        }

    }

    /**
     * Receiving Respond from the backend server.
     * @param response Response has the Code and the tv Genre from backend server.
     */
    @Subscribe(priority = 1)
    public void resGenreTvShows(ResTvGenre response){
        Log.d(TAG,"Tókst ná í genre fyrir tv shows");

        if(response.getCode() == 200){
            GerneList.setGenreTvList(response.getGenreResults().getResults());

        }else{
            String title ="Something went wrong";
            String msg = "Something went wrong, please try again";
            PopUpMsg.dialogMsg(title,msg,getApplicationContext());
        }

    }



}
