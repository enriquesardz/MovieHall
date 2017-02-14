package com.ensardz.moviehall.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Quique on 31/01/2017.
 */

public class MoviesContract {
    private MoviesContract() {}

    public static final String CONTENT_AUTHORITY = "com.ensardz.android.moviehall.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_VIDEOS = "videos";



    public static class MoviesEntry implements BaseColumns {
        //Table name
        public static final String TABLE_MOVIES = "movies";
        //Table columns
        public static final String COLUMN_MOVIE_ID = "movie_id"; //This is the actual Movie ID given by themoviesdb.org, not the table _ID
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_IS_FAVOURITE = "is_favourite";

        //Content URI
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_MOVIES).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIES;

        public static Uri buildMoviesUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static class VideosEntry implements BaseColumns{
        //Table name
        public static final String TABLE_VIDEOS = "videos";
        //Table columns
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_YOUTUBE_KEY = "youtube_key";

        //Content URI
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_VIDEOS).build();

        public static final String CONTENT_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_VIDEOS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_VIDEOS;

        //For building URIs on insertion
        public static Uri buildVideosUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
