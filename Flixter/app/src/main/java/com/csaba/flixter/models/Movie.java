package com.csaba.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saba on 29/12/16.
 */
public class Movie {
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    String posterPath; //portrait
    String originalTitle;
    String overview;
    String backdropPath; //landscape

    Movie(JSONObject jsonObject) throws JSONException{
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
    }

    public static ArrayList<Movie> fromJsonArray(JSONArray jsonArray){
        ArrayList<Movie> results = new ArrayList<>();
        for(int x=0;x<jsonArray.length();x++){
            try {
                results.add(new Movie(jsonArray.getJSONObject(x)));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

}
