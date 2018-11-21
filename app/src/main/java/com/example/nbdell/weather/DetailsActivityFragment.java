package com.example.nbdell.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
public class DetailsActivityFragment extends Fragment {

    private ArrayAdapter<String> ForecastAdapter ;
    DetailsActivity activity_temp = (DetailsActivity) getActivity();
    String SentId = activity_temp.id;

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
    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        private String[] getDataFromJson(String forecastJsonStr) throws JSONException{

            JSONObject weather = new JSONObject(forecastJsonStr);
            JSONArray cityDetails = weather.getJSONArray("list");
            JSONObject main_details = cityDetails.getJSONObject(0);
            JSONObject main_details2 = main_details.getJSONObject("main");
            String[] resultStrs = new String[3];
            String temp =  main_details2.getString("temp");
            String temp_min =  main_details2.getString("temp_min");
            String temp_max =  main_details2.getString("temp_max");
            resultStrs[0] = "temp" + " " + temp;
            resultStrs[1] = "temp_min" + " " + temp_min;
            resultStrs[2] = "temp_max" + " " + temp_max;
            Log.v("firstTest","hhjs");
            Log.v("firstTest",temp);
            return resultStrs;
        }


        @Override
        protected String[] doInBackground(String... params) {
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
        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {

                List<String> Forecast = new ArrayList<String>(Arrays.asList(result));
                ForecastAdapter =
                        new ArrayAdapter<String>(
                                getActivity(), // The current context (this activity)
                                R.layout.list_item, // The name of the layout ID.
                                R.id.list_item_forecast_textview, // The ID of the textview to populate.
                                Forecast);

                ListView listView = (ListView) getActivity().findViewById(R.id.listview_Details);
                listView.setAdapter(ForecastAdapter);


                // New data is back from the server.  Hooray!
            }
        }
    }
}
