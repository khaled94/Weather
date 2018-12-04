package com.example.nbdell.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CityDetailsAdapter extends BaseAdapter {

    City city;
    Context context;
    LayoutInflater inflter;

    public CityDetailsAdapter(Context mcontext, City city) {
        this.city = city;
        context = mcontext;
        inflter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return city.getSize();
    }

    @Override
    public Object getItem(int i) {
        return city;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        
        for (int j = 0; j < city.getSize(); j++) {

            view = LayoutInflater.from(context).inflate(R.layout.list_details_item, viewGroup, false);

            TextView max_temp = (TextView) view.findViewById(R.id.max_temp);
            TextView min_temp = (TextView) view.findViewById(R.id.min_temp);
            TextView humidity = (TextView) view.findViewById(R.id.humidity);
            TextView sea_level = (TextView) view.findViewById(R.id.sea_level);
            TextView pressure = (TextView) view.findViewById(R.id.pressure);

            max_temp.setText("Max Temp " + city.max_temp[j]);
            min_temp.setText("Min Temp " + city.min_temp[j]);
            humidity.setText("Humidity " + city.humidity[j]);
            sea_level.setText("Sea Level " + city.sea_level[j]);
            pressure.setText("Pressure " + city.pressure[j]);

        }
        return view;
    }
}