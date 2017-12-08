package com.example.watchlist.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.watchlist.R;
import com.example.watchlist.fragment.movie.MovieDetailsFragment;
import com.example.watchlist.themoviedb.Genre;
import com.example.watchlist.themoviedb.Movie;
import com.example.watchlist.utils.ConvertValue;
import com.example.watchlist.utils.ImageHandler;


import java.util.ArrayList;
import java.util.List;

/**
 * MoviesAdapter is used to create list of movies.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Movie> movieList;
    private List<Genre> genreList;
    private Context context;
    private FragmentManager fm;

    private boolean isLoadingAdded = false;

    public MoviesAdapter(Context context, FragmentManager fm) {
        this.context = context;
        this.fm = fm;
        this.movieList = new ArrayList<>();
        this.genreList = new ArrayList<>();
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.movies_recycler_view, parent, false);
        viewHolder = new MoviesAdapter.MoviesVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Movie movie = movieList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                MoviesAdapter.MoviesVH moviesVH = (MoviesAdapter.MoviesVH) holder;

                moviesVH.name.setText(movie.getTitle());
                moviesVH.rating.setText(String.format("Rating: %s", ConvertValue.toOneDecimal(movie.getVoteAverage())));
                moviesVH.genre.setText(makeGenreFromId(movie.getGenreIds()));
                moviesVH.posterImg.setSmallImg(movie.getPosterPath());
                moviesVH.movieDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putLong("movieId",movie.getId());
                        movieDetailsFragment.setArguments(bundle);
                        fm.beginTransaction().replace(R.id.main_container, movieDetailsFragment, movieDetailsFragment.getTag()).addToBackStack(null).commit();


                    }
                });

                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Movie movie) {
        movieList.add(movie);
        notifyItemInserted(movieList.size() - 1);
    }

    public void addAll(List<Movie> List){
        for (Movie movie : List) {
            add(movie);
        }
    }

    public void remove(Movie movie) {
        int position = movieList.indexOf(movie);
        if (position > -1) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;

        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Movie());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieList.size() - 1;
        Movie item = getItem(position);

        if (item != null) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public void addAllGenre(List<Genre> list) {
        genreList.addAll(list);
    }

    public void isGenreListEmpty(){
        genreList.isEmpty();
    }

    public void clearGenreList(){
        genreList.clear();
    }

    private String makeGenreFromId(List<Integer> listId){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Integer id : listId) {
            for (Genre genre : genreList){
                if(genre.getId() == id){
                    if(i != 0){
                        sb.append(", ");
                    }
                    sb.append(genre.getName());
                    i ++;
                    break;
                }
            }
        }
        return sb.toString();
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    private class MoviesVH extends RecyclerView.ViewHolder {
        private TextView rating;
        private TextView name;
        private TextView genre;
        private ImageHandler posterImg;
        private RelativeLayout movieDetail;

        private MoviesVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_movie_textView);
            rating = (TextView) itemView.findViewById(R.id.rating_movie_textView);
            posterImg = new ImageHandler(context,(ImageView) itemView.findViewById(R.id.poster_movie_imageView));
            genre = (TextView) itemView.findViewById(R.id.genre_movie_textView);
            movieDetail =(RelativeLayout) itemView.findViewById(R.id.movie_recycler);
        }
    }


    private class LoadingVH extends RecyclerView.ViewHolder {

        private LoadingVH(View itemView) {
            super(itemView);
        }
    }
}

