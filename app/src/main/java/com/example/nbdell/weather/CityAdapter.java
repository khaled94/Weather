package com.example.nbdell.weather;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends BaseAdapter {

    List<City> cities = new ArrayList<>();
    Context context;
    LayoutInflater inflter;

    public CityAdapter(Context mcontext, List<City> cities) {
        this.cities = cities;
        context = mcontext;
        inflter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int i) {
        return cities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        City city = (City) getItem(i);
        view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        TextView name = (TextView) view.findViewById(R.id.city_name);
        TextView temp = (TextView) view.findViewById(R.id.city_temp);
        name.setText(city.getName());
        temp.setText(city.getTemp());
        return view;
    }

    }
