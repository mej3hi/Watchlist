package com.example.watchlist.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
public class ImageHandler  {

    private final String SMALL = "http://image.tmdb.org/t/p/w92";
    private final String MEIDUM = "http://image.tmdb.org/t/p/w185";
    private final String LARGE = "http://image.tmdb.org/t/p/w342";

    private Context context;
    private ImageView imageView;

    public ImageHandler(Context context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Load a small image.
     * @param path Is string and contents the path
     */
    public void setSmallImg(String path){
        Picasso.with(context).load(SMALL+path).into(imageView);
    }
    /**
     * Load a medium image.
     * @param path Is string and contents the path
     */
    public void setMediumImg(String path){
        Picasso.with(context).load(MEIDUM+path).into(imageView);
    }
    /**
     * Load a larg image.
     * @param path Is string and contents the path
     */
    public void setLargeImg(String path){
        Picasso.with(context).load(LARGE+path).into(imageView);
    }

}
