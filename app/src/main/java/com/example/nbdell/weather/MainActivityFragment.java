package com.example.nbdell.weather;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    CityAdapter adapter;
    private String[] Cities_Id = {
            "524901",
            "703448",
            "2643743",
            "707860",
            "519188"
    };
    private City [] cities  = new City[5];


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.forecast_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        for( int i = 0 ; i < 5 ; i++ ){
            cities[i] = new City(Cities_Id[i]);
        }

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        FetchWeatherTask data = new FetchWeatherTask();
        data.execute();
        return rootView;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, City[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        private City[] getDataFromJson(String forecastJsonStr) throws JSONException{
            JSONObject weather = new JSONObject(forecastJsonStr);
            JSONArray cities = weather.getJSONArray("list");
            City[] resultStrs = new City[5];
            for( int i = 0 ; i < 5 ; i++ ){
                resultStrs[i] = new City(Cities_Id[i]);
            }
            for (int i=0; i<cities.length(); i++) {
                JSONObject city = cities.getJSONObject(i);
                String name = city.getString("name");
                JSONObject main_details = city.getJSONObject("main");
                String temp =  main_details.getString("temp");
                Log.v(LOG_TAG,"name" + name);
                Log.v(LOG_TAG,"temprature" + temp);
                resultStrs[i].setName(name);
                resultStrs[i].setTemp(temp);
            }
        return resultStrs;
        }


        @Override
        protected City[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String Key="d171f3f96693d539da5e1abceb26c482";
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                String baseUrl = "http://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743,707860,519188&units=metric";
                String apiKey = "&APPID=" + Key;
                URL url = new URL(baseUrl.concat(apiKey));

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(City[] result) {
            if (result != null) {

                List<City> cities = new ArrayList<City>(Arrays.asList(result));
                ListView listView = (ListView) getActivity().findViewById(R.id.listview_forecast);
                adapter = new CityAdapter(getContext(), cities);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String id =Cities_Id[position];
                        Intent intent = new Intent(getActivity(), DetailsActivity.class)
                                .putExtra("id", id);
                        startActivity(intent);
                    }
                });

                // New data is back from the server.  Hooray!
            }
        }
    }
}