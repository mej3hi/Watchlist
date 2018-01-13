package com.example.watchlist.utils;


import com.example.watchlist.themoviedb.Genre;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created year 2017.
 * Author:
 *  Eiríkur Kristinn Hlöðversson
 *  Martin Einar Jensen
 */
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
     * It take list of Genre and create single name string from it
     * with comma between them.
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
    /**
     * It take list of Genre and create single string ID from it
     * with : between them .
     * @param list List is the Genre.
     * @return It return a string.
     */
    public static String genreIdToString(List<Genre> list){

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() ; i++) {
            sb.append(list.get(i).getId());
            if(list.size()!= i+1){
                sb.append(":");
            }
        }

        return sb.toString();
    }

    /**
     * It split up string id with : between them and
     * convert it  to list of integer id.
     * @param id Id is String
     * @return It return list of integer
     */
    public static List<Integer> genreIdToListInteger(String id){
        List<Integer> genreId = new ArrayList<>();
        String[] genre = id.split(":");
        for (String s : genre) {
            genreId.add(Integer.valueOf(s));
        }
        return genreId;
    }

    /**
     * It takes list of Genre and list of Integer and
     * check whether the id is the same and create
     * single string ID from it with comma between them
     * @param listId Is list of id
     * @param genreList Is the Genre Object
     * @return It return string
     */
    public static String genreFromId(List<Integer> listId, List<Genre> genreList){
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

}
