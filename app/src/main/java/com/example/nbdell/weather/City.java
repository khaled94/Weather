package com.example.nbdell.weather;

public class City {
    String name;
    String temp;
    String max_temp;
    String min_temp;
    String id;
    String pressure;
    String sea_level;
    String humidity;

    public City(String id) {
        this.id = id;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setSea_level(String sea_level) {
        this.sea_level = sea_level;
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

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getSea_level() {
        return sea_level;
    }
}
