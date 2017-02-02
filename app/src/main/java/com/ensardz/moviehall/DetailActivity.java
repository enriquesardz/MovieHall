package com.ensardz.moviehall;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.ensardz.moviehall.R.id.textview_overview;

public class DetailActivity extends AppCompatActivity {

    public static oGridItem movieItem = new oGridItem();
    public static final String LOG_TAG = DetailActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

    }

    static public class DetailFragment extends Fragment {

        private ImageView iVMoviePoster;
        private TextView tVOriginalTitle;
        private TextView tVOverview;
        private TextView tVRating;
        private TextView tVReleaseDate;

        private int movieID;

        public DetailFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity_detail, container, false);
            Intent intent = getActivity().getIntent();
            if(intent != null && intent.hasExtra("movie_id") && intent.hasExtra("movie_image")){
                movieID = intent.getIntExtra("movie_id", 0);
                movieItem.setId(movieID);
                movieItem.setImage(intent.getStringExtra("movie_image"));
            }
            iVMoviePoster = (ImageView) rootView.findViewById(R.id.imageview_movie_poster);
            tVOriginalTitle = (TextView) rootView.findViewById(R.id.textview_original_title);
            tVOverview = (TextView) rootView.findViewById(textview_overview);
            tVRating = (TextView) rootView.findViewById(R.id.textview_rating);
            tVReleaseDate = (TextView) rootView.findViewById(R.id.textview_release_date);

            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();
            getMovieData();
        }

        public void getMovieData(){
            FetchMovieDataTask movieDataTask = new FetchMovieDataTask();
            movieDataTask.execute(Integer.toString(movieID));
        }

        private class FetchMovieDataTask extends AsyncTask<String, Void, Integer>{

            @Override
            protected Integer doInBackground(String... params) {
                if(params.length == 0){
                    return 0;
                }

                HttpURLConnection urlConn = null;
                BufferedReader reader = null;

                String moviesJsonString = null;
                try {
                    URL url = new URL("https://api.themoviedb.org/3/movie/" + params[0] + "?api_key=48452e1f6e93d01edd5d5f32f4d09cd8&language=en-US");
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestMethod("GET");
                    urlConn.connect();

                    InputStream inputStream = urlConn.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    if(inputStream == null){
                        return 0;
                    }

                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    if(buffer.length() == 0){
                        return 0;
                    }

                    moviesJsonString = buffer.toString();

                    try{
                        getMoviesDataFromJson(moviesJsonString);
                    }
                    catch(JSONException e){
                        Log.e(LOG_TAG, e.toString());
                    }
                }
                catch(IOException e){
                    Log.e(LOG_TAG, e.toString());
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
                            Log.e(LOG_TAG, e.toString());
                        }
                    }
                }

                return 1;
            }

            @Override
            protected void onPostExecute(Integer result) {
                if(result == 1){
                    Picasso.with(getContext()).load(movieItem.getImage()).into(iVMoviePoster);
                    tVOriginalTitle.setText(movieItem.getTitle());
                    tVOverview.setText(movieItem.getOverview());
                    tVRating.setText(Double.toString(movieItem.getRating()));
                    tVReleaseDate.setText(movieItem.getReleaseDate());
                }
                else{
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_LONG);
                }
            }

            public void getMoviesDataFromJson(String jsonStr) throws JSONException{
                JSONObject movieDataJson = new JSONObject(jsonStr);
                    movieItem.setTitle(movieDataJson.getString("original_title"));
                    movieItem.setOverview(movieDataJson.getString("overview"));
                    movieItem.setRating(movieDataJson.getDouble("vote_average"));
                    movieItem.setReleaseDate(movieDataJson.getString("release_date"));
            }
        }


    }
}
