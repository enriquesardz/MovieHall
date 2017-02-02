package com.ensardz.moviehall;

/**
 * Created by Quique on 27/12/2016.
 */

public class oGridItem {
    String image, title, overview, releaseDate;
    int id;
    double rating;

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getOverview(){
        return this.overview;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }

    public String getReleaseDate(){
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }

    public double getRating(){
        return this.rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }
}
