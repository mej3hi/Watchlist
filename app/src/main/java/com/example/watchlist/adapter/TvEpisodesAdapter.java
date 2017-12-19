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
import com.example.watchlist.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 *
 * TvEpisodesAdapter is used to create list of tv episodes in some particular tv shows
 * season.
 */
public class TvEpisodesAdapter extends RecyclerView.Adapter<TvEpisodesAdapter.TvEpisodesViewHolder> {

    private List<TvSeasonDetails.Episode> episodeList;
    private FragmentManager fm;
    private Context context;
    private long tvId;
    private String posterPath;

    /**
     * TvEpisodesViewHolder content ViewHolder
     */
    class TvEpisodesViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout episodeReLayout;
        private ImageHandler stillImg;
        private TextView episodeName;
        private TextView rating;
        private TextView releaseDate;
        private TextView seriesNumber;

        TvEpisodesViewHolder(View itemView) {
            super(itemView);
            stillImg = new ImageHandler(context,(ImageView) itemView.findViewById(R.id.still_episode_imageView));
            rating = (TextView) itemView.findViewById(R.id.rating_episode_TextView);
            episodeName = (TextView) itemView.findViewById(R.id.name_episode_TextView);
            releaseDate = (TextView) itemView.findViewById(R.id.release_episode_TextView);
            seriesNumber = (TextView) itemView.findViewById(R.id.series_number_episode_textView);
            episodeReLayout = (RelativeLayout) itemView.findViewById(R.id.episode_recyclerView);

        }
    }
    /**
     * Is the constructor for the TvEpisodesAdapter
     * @param context Context is context
     * @param fm Fm is FragmentManager
     */
    public TvEpisodesAdapter(Context context, FragmentManager fm) {
        this.fm = fm;
        this.context = context;
        this.episodeList = new ArrayList<>();
    }

    /**
     * Add new view that is episode view
     * @param parent Parent is the viewGroup
     * @param viewType What view type to display
     * @return It return the new view
     */
    @Override
    public TvEpisodesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tv_episodes_recycler_view,parent,false);
        return new TvEpisodesViewHolder(itemView);
    }

    /**
     * It display the episode at the specified position and add
     * onClick listener that open episode details fragment
     * for that episode
     * @param holder Holder is the viewHolder
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(TvEpisodesViewHolder holder, int position) {
        final TvSeasonDetails.Episode episode = episodeList.get(position);
        holder.episodeName.setText(episode.getName());
        holder.rating.setText(String.format("Rating: %s", ConvertValue.toOneDecimal(episode.getVoteAverage())));
        holder.releaseDate.setText(episode.getAirDate());
        holder.seriesNumber.setText(String.format("S%s, Ep%s", String.valueOf(episode.getSeasonNumber()), String.valueOf(episode.getEpisodeNumber())));
        holder.stillImg.setMediumImg(episode.getStillPath());

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

    /**
     * It get item count
     * @return Return 0 if null else the size of episodeList
     */
    @Override
    public int getItemCount() {
        return episodeList == null ? 0 : episodeList.size();
    }


    // helpers
    //-------------------------------------------------------------------

    /**
     * Is set list of episodes to episodeList and the tv Id and also
     * the poster path.
     * @param list List is list of episodes
     * @param tvId TvId is the id of tv show
     * @param posterPath PosterPath is the path for the poster
     */
    public void setEpisodes(List<TvSeasonDetails.Episode> list,long tvId,String posterPath){
        this.tvId = tvId;
        this.posterPath = posterPath;
        episodeList = list;
        notifyDataSetChanged();
    }

    /**
     * Check whether the episodeList is empty
     * @return Return true if it is empty else false
     */
    public boolean isEmpty(){
        return getItemCount() == 0;
    }
}
