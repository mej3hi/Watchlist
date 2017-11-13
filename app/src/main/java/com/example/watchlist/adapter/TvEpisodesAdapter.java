package com.example.watchlist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.fragment.tvshows.EpisodeDetailsFragment;
import com.example.watchlist.themoviedb.TvSeasonDetails;
import com.example.watchlist.utils.ConvertValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * TvEpisodesAdapter is used to create list of tv episodes in some particular tv shows
 * season.
 */
public class TvEpisodesAdapter extends RecyclerView.Adapter<TvEpisodesAdapter.TvEpisodesViewHolder> {

    private List<TvSeasonDetails.Episode> episodeList;
    private FragmentManager fm;
    private Context context;
    private long tvId;
    private String posterPath;

    public class TvEpisodesViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout episodeReLayout;
        public ImageView stillImg;
        public TextView episodeName;
        public TextView rating;
        public TextView releaseDate;
        public TextView seriesNumber;

        public TvEpisodesViewHolder(View itemView) {
            super(itemView);
            stillImg = (ImageView) itemView.findViewById(R.id.still_episode_imageView);
            rating = (TextView) itemView.findViewById(R.id.rating_episode_TextView);
            episodeName = (TextView) itemView.findViewById(R.id.name_episode_TextView);
            releaseDate = (TextView) itemView.findViewById(R.id.release_episode_TextView);
            seriesNumber = (TextView) itemView.findViewById(R.id.series_number_episode_textView);
            episodeReLayout = (RelativeLayout) itemView.findViewById(R.id.episode_recyclerView);

        }
    }

    public TvEpisodesAdapter(Context context, FragmentManager fm) {
        this.fm = fm;
        this.context = context;
        this.episodeList = new ArrayList<>();
    }

    @Override
    public TvEpisodesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_episodes_recycler_view,parent,false);
        return new TvEpisodesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TvEpisodesViewHolder holder, int position) {
        final TvSeasonDetails.Episode episode = episodeList.get(position);
        holder.episodeName.setText(episode.getName());
        holder.rating.setText("Rating: "+ ConvertValue.toOneDecimal(episode.getVoteAverage()));
        holder.releaseDate.setText(episode.getAirDate());
        holder.seriesNumber.setText("S"+episode.getSeasonNumber()+", "+"Ep"+episode.getEpisodeNumber());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+episode.getStillPath()).into(holder.stillImg);

        holder.episodeReLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EpisodeDetailsFragment fragment = new EpisodeDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("tvId",tvId);
                bundle.putInt("seasonNumber",episode.getSeasonNumber());
                bundle.putInt("episodeNumber",episode.getEpisodeNumber());
                bundle.putString("posterPath",posterPath);
                fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.main_container, fragment, fragment.getTag()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodeList == null ? 0 : episodeList.size();
    }


    // helpers
    //-------------------------------------------------------------------


    public void setEpisodes(List<TvSeasonDetails.Episode> list,long tvId,String posterPath){
        this.tvId = tvId;
        this.posterPath = posterPath;
        episodeList = list;
        notifyDataSetChanged();
    }

    public void clear(){
        episodeList.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return getItemCount() == 0;
    }
}
