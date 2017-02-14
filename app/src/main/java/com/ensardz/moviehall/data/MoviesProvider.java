package com.ensardz.moviehall.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by ensardz on 13/02/2017.
 */

public class MoviesProvider extends ContentProvider {

    private static final String LOG_TAG = MoviesProvider.class.getSimpleName();
    private static final UriMatcher mUriMatcher = buildUriMatcher();

    private static final int MOVIES = 1;
    private static final int MOVIE_WITH_ID = 2;
    private static final int VIDEOS = 3;
    private static final int VIDEOS_WITH_MOVIE_ID = 4;

    private static final SQLiteQueryBuilder sMovieWithVideoQueryBuilder;

    static{
        sMovieWithVideoQueryBuilder = new SQLiteQueryBuilder();

        //INNER JOIN
        //movies INNER JOIN videos ON movies.movie_id = videos.movie_id
        sMovieWithVideoQueryBuilder.setTables(
                MoviesContract.MoviesEntry.TABLE_MOVIES + " INNER JOIN " +
                        MoviesContract.VideosEntry.TABLE_VIDEOS +
                        " ON " + MoviesContract.MoviesEntry.TABLE_MOVIES +
                        "." + MoviesContract.MoviesEntry.COLUMN_MOVIE_ID +
                        " = " + MoviesContract.VideosEntry.COLUMN_MOVIE_ID

        );
    }

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MoviesContract.PATH_MOVIES, MOVIES);
        matcher.addURI(authority, MoviesContract.PATH_VIDEOS, VIDEOS);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
