package com.example.nbdell.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class CityDetailsAdapter extends BaseAdapter {

    List<City> cities = new ArrayList<>();
    Context context;
    LayoutInflater inflter;

    public CityDetailsAdapter(Context mcontext, List<City> cities) {
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
        return null;
    }
}
