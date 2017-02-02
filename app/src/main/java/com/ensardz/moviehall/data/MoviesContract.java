package com.ensardz.moviehall.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Quique on 31/01/2017.
 */

public class MoviesContract {
    private MoviesContract() {}

    public static final String CONTENT_AUTHORITY = "com.ensardz.android.moviehall.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class VideosEntry implements BaseColumns{
        public static final String TABLE_NAME = "videos";

        public static final String COLUMN_NAME_KEY = "name";
        public static final String COLUMN_YOUTUBE_KEY = "youtube_key";
    }

    public static class MoviesEntry implements BaseColumns{

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_VIDEO_KEY = "videos_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POSTER = "poster_path";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_RUNTIME = "runtime";
        public static final String COLUMN_NAME_IS_FAVOURITE = "is_favourite";
    }
}
