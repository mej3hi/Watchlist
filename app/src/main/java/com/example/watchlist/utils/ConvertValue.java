package com.example.watchlist.utils;


import java.text.DecimalFormat;



public class ConvertValue {

    private static DecimalFormat df;

    /**
     * Get rid of all the decimal places except one.
     * @param num is double.
     * @return it return a string.
     */
    public static String toOneDecimal(double num){

        df = new DecimalFormat("#.#");
        return df.format(num);
    }


}
