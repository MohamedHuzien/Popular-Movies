package com.mohamedhuzien.PopularMovies;

/**
 * Created by Mohamed Huzien on 1/30/2016.
 */
public class Settings {


    private static final String popularityDesc = "popularity.desc";
    private static final String vote_countDesc = "vote_count.desc";

    public static String sortBypopularity()
    {
        return popularityDesc;
    }

    public   static String sortByVote_count()
    {
        return vote_countDesc;
    }

}
