package com.ensardz.moviehall;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    public static final String LOG_TAG = MoviesFragment.class.getSimpleName();
    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    private ArrayList<oGridItem> mGridData;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_activity_main_movies, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.movie_gridview);

        //Empty Data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(getContext(), R.layout.gridview_movie_item, mGridData);
        mGridView.setAdapter(mGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oGridItem item = mGridAdapter.getItem(position);
                Intent detail = new Intent(getContext(), DetailActivity.class)
                        .putExtra("movie_id", item.getId())
                        .putExtra("movie_image", item.getImage());
                startActivity(detail);

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMoviesData();
    }
    public class FetchMovieData extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            if (params.length == 0){
                return null;
            }

            HttpURLConnection urlConn = null;
            BufferedReader reader = null;
            String moviesJsonStr = null;

            String appId = "48452e1f6e93d01edd5d5f32f4d09cd8";
            String language = "en-US";
            String sortBy = params[0];
            String includeAdult = "false";
            String includeVideo = "false";
            String numPage = "1";
            try{
                final String MOVIESDB_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
                final String APP_ID = "api_key";
                final String LANGUAGE_PARAM = "language";
                final String SORT_BY_PARAM = "sort_by";
                final String INCLUDE_ADULT_PARAM = "include_adult";
                final String INCLUDE_VIDEO_PARAM = "include_video";
                final String PAGE_PARAM = "page";

                Uri builtUri = Uri.parse(MOVIESDB_BASE_URL).buildUpon()
                        .appendQueryParameter(APP_ID, appId)
                        .appendQueryParameter(LANGUAGE_PARAM, language)
                        .appendQueryParameter(SORT_BY_PARAM, sortBy)
                        .appendQueryParameter(INCLUDE_ADULT_PARAM, includeAdult)
                        .appendQueryParameter(INCLUDE_VIDEO_PARAM, includeVideo)
                        .appendQueryParameter(PAGE_PARAM, numPage)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestMethod("GET");
                urlConn.connect();

                InputStream inputStream = urlConn.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                moviesJsonStr = buffer.toString();
                Log.i(LOG_TAG, moviesJsonStr);

                try{
                    getMoviesDataFromJson(moviesJsonStr);
                }
                catch (JSONException e){
                    Log.e(LOG_TAG, e.toString());
                }
            }
            catch(IOException e){
                Log.e(LOG_TAG, "Error: ", e);
            }
            finally{
                if(urlConn != null){
                    urlConn.disconnect();
                }
                if(reader != null){
                    try{
                        reader.close();
                    }
                    catch (final IOException e){
                        Log.e(LOG_TAG, "Error closing stream: ", e);
                    }
                }
            }
            return 1;
        }

        public void getMoviesDataFromJson(String jsonStr) throws JSONException{
            JSONObject moviesDataJson = new JSONObject(jsonStr);
            JSONArray movieList = moviesDataJson.getJSONArray("results");
            oGridItem item;
            for(int i=0; i<movieList.length(); i++){
                JSONObject movie = movieList.getJSONObject(i);
                item = new oGridItem();
                item.setId(movie.getInt("id")); //for example: 297761
                item.setImage("http://image.tmdb.org/t/p/w185" + movie.getString("poster_path"));

            }
        }
    }

    public void getMoviesData(){
        FetchMovieData moviesTask = new FetchMovieData();
        String sortOrder = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("order", "popularity.desc");
        mGridAdapter.clear();
        moviesTask.execute(sortOrder);
    }
}