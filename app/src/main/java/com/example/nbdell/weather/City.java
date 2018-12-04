package com.example.nbdell.weather;

public class City {

    String id;
    String name;
    String temp;
    String max_temp[];
    String min_temp[];
    String pressure[];
    String sea_level[];
    String humidity[];
    int size;


    public City(String id) {
        this.id = id;
    }


    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getId() {
        return id;
    }

    public String getTemp() {
        return temp;
    }

    public String getName() {
        return name;
    }
}