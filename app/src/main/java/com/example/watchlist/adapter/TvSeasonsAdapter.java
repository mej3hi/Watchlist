package com.example.watchlist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.fragment.tvshows.SeasonDetailsFragment;
import com.example.watchlist.themoviedb.TvDetails;
import com.example.watchlist.utils.ImageHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * TvSeasonsAdapter is used to create list of seasons for some particular tv show.
 *
 */
public class TvSeasonsAdapter extends RecyclerView.Adapter<TvSeasonsAdapter.TvSeasonsViewHolder>{

    private List<TvDetails.Season> seasonList;
    private long tvId;
    private FragmentManager fm;
    private Context context;

    class TvSeasonsViewHolder extends RecyclerView.ViewHolder {
        private TextView seasonName;
        private ImageHandler posterImg;
        private LinearLayout seasonlayout;

        TvSeasonsViewHolder(View itemView) {
            super(itemView);
            seasonName = (TextView) itemView.findViewById(R.id.name_season_textView);
            posterImg = new ImageHandler(context,(ImageView) itemView.findViewById(R.id.poster_season_imageView));
            seasonlayout = (LinearLayout) itemView.findViewById(R.id.season_linearLayout);

        }
    }

    public TvSeasonsAdapter(Context context,FragmentManager fm){
        this.fm = fm;
        this.context = context;
        this.seasonList = new ArrayList<>();
    }


    @Override
    public TvSeasonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_seasons_recycler_view,parent,false);
        return new TvSeasonsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TvSeasonsViewHolder holder, int position) {
        final TvDetails.Season season = seasonList.get(position);
        holder.seasonName.setText(String.format("Season %s", String.valueOf(season.getSeasonNumber())));
        holder.posterImg.setSmallImg(season.getPosterPath());
        holder.seasonlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeasonDetailsFragment fragment = new SeasonDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("tvId",tvId);
                bundle.putInt("seasonNumber",season.getSeasonNumber());
                fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.main_container, fragment, fragment.getTag()).addToBackStack(null).commit();

            }

        });


    }

    @Override
    public int getItemCount() {
        return seasonList == null ? 0 : seasonList.size();
    }



    // Helpers

    //-------------------------------------------------------------------------


    public void setSeasons(List<TvDetails.Season> list, long tvId){
        this.tvId = tvId;
        for (int i = list.size()-1; i >= 0 ; i--) {
            seasonList.add(list.get(i));
        }
        notifyDataSetChanged();

    }

    public void clear(){
        seasonList.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return getItemCount() == 0;
    }
}