package com.example.nbdell.weather;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class DetailsActivityFragment extends Fragment {


    DetailsActivity activity_temp = (DetailsActivity) getActivity();
    String SentId = activity_temp.id;
    CityDetailsAdapter adapter;

    public DetailsActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        FetchWeatherTask data = new FetchWeatherTask();
        data.execute();
        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, City> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        private City getDataFromJson(String forecastJsonStr) throws JSONException{

            City result = new City(" SentId");

            JSONObject weather = new JSONObject(forecastJsonStr);
            JSONArray cityDetails = weather.getJSONArray("list");
            int cnt = weather.getInt("cnt");
            result.setSize(cnt);

            result.pressure = new String[cnt];
            result.max_temp = new String[cnt];
            result.min_temp = new String[cnt];
            result.sea_level = new String[cnt];
            result.humidity = new String[cnt];

            for ( int i = 0 ; i < cnt ; i++ ) {

                JSONObject main_details = cityDetails.getJSONObject(i);
                JSONObject main_details2 = main_details.getJSONObject("main");

                String temp_min = main_details2.getString("temp_min");
                String temp_max = main_details2.getString("temp_max");
                String pressure = main_details2.getString("pressure");
                String humidity = main_details2.getString("humidity");
                String sea_level = main_details2.getString("sea_level");

                result.humidity[i] = humidity;
                result.max_temp[i] = temp_max;
                result.min_temp[i] = temp_min;
                result.pressure[i] = pressure;
                result.sea_level[i] = sea_level;
            }
            return result;
        }


        @Override
        protected City doInBackground(String... params) {
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
                String baseUrl = "https://samples.openweathermap.org/data/2.5/forecast";
                String Sent_Id ="?id="+ SentId;
                String apiKey = "&APPID=" + Key;
                baseUrl = baseUrl.concat(Sent_Id);
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

        protected void onPostExecute(City result) {
            if (result != null) {

                ListView listView = (ListView) getActivity().findViewById(R.id.listview_Details);
                adapter = new CityDetailsAdapter(getContext(),result);
                listView.setAdapter(adapter);

            }
        }
    }
}