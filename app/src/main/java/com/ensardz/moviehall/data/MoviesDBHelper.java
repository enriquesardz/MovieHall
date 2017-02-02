package com.ensardz.moviehall.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaRecorder;

import com.ensardz.moviehall.data.MoviesContract.VideosEntry;
import com.ensardz.moviehall.data.MoviesContract.MoviesEntry;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

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
        final String SQL_CREATE_VIDEOS_TABLE = "CREATE TABLE " + VideosEntry.TABLE_NAME + " (" +
                VideosEntry._ID + " INTEGER PRIMARY KEY, " +
                VideosEntry.COLUMN_NAME_KEY + " TEXT NOT NULL, " +
                VideosEntry.COLUMN_YOUTUBE_KEY + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesEntry.COLUMN_NAME_VIDEO_KEY + "INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_NAME_MOVIE_ID + "INTEGER NOT NULL, " +
                MoviesEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                MoviesEntry.COLUMN_NAME_POSTER + " TEXT NULL, " +
                MoviesEntry.COLUMN_NAME_OVERVIEW + " TEXT NULL, " +
                MoviesEntry.COLUMN_NAME_VOTE_AVERAGE + " REAL NULL, " +
                MoviesEntry.COLUMN_NAME_RELEASE_DATE + " TEXT NULL, " +
                MoviesEntry.COLUMN_NAME_RUNTIME + " INTEGER NULL, " +
                MoviesEntry.COLUMN_NAME_IS_FAVOURITE + " INTEGER DEFAULT 0" +
                ");";
        db.execSQL(SQL_CREATE_VIDEOS_TABLE);
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

