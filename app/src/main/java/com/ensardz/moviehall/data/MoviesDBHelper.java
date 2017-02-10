package com.ensardz.moviehall.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ensardz.moviehall.data.MoviesContract.VideosEntry;
import com.ensardz.moviehall.data.MoviesContract.MoviesEntry;

/**
 * Created by Quique on 01/02/2017.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "moviehall.db";

    public MoviesDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_VIDEOS_TABLE = "CREATE TABLE " + VideosEntry.TABLE_VIDEOS + " (" +
                VideosEntry._ID + " INTEGER PRIMARY KEY, " +
                VideosEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL" +
                VideosEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                VideosEntry.COLUMN_YOUTUBE_KEY + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_MOVIES + " (" +
                MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesEntry.COLUMN_MOVIE_ID + "INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_POSTER + " TEXT NULL, " +
                MoviesEntry.COLUMN_OVERVIEW + " TEXT NULL, " +
                MoviesEntry.COLUMN_VOTE_AVERAGE + " REAL NULL, " +
                MoviesEntry.COLUMN_RELEASE_DATE + " TEXT NULL, " +
                MoviesEntry.COLUMN_RUNTIME + " INTEGER NULL, " +
                MoviesEntry.COLUMN_IS_FAVOURITE + " INTEGER DEFAULT 0" +
                ");";
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_VIDEOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

