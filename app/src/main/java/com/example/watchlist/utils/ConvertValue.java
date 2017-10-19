package com.example.watchlist.utils;


import com.example.watchlist.themoviedb.Genre;

import java.text.DecimalFormat;
import java.util.List;


public class ConvertValue {

    /**
     * Get rid of all the decimal places except one.
     * @param num is double.
     * @return it return a string.
     */
    public static String toOneDecimal(double num){

        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(num);
    }

    /**
     * It take list of Genre and create single name string from it.
     * @param list List is the Genre.
     * @return It return a string.
     */
    public static String genreToString(List<Genre> list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() ; i++) {
            sb.append(list.get(i).getName());
            if(list.size()!= i+1){
                sb.append(", ");
            }
        }

        return sb.toString();
    }


}
