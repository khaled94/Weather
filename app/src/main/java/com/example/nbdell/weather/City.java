package com.example.nbdell.weather;

public class City {
    String name;
    String temp;
    String max_temp;
    String min_temp;
    String id;

    public City(String id) {
        this.id = id;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public String getTemp() {
        return temp;
    }

    public String getName() {
        return name;
    }
}
