package com.justaccounts.iprogrammertestnew.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cityweather")
public class CityWeather {
    @NonNull
    @PrimaryKey
    private String id;
    private String cityName;
    private String temp;
    private long timeStampe;
    private String dateTime;
    private String img;
    private String humidity;


    public CityWeather(@NonNull String id, String cityName, String temp, long timeStampe, String dateTime, String img, String humidity) {
        this.id = id;
        this.cityName = cityName;
        this.temp = temp;
        this.timeStampe = timeStampe;
        this.dateTime = dateTime;
        this.img=img;
        this.humidity = humidity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public long getTimeStampe() {
        return timeStampe;
    }

    public void setTimeStampe(long timeStampe) {
        this.timeStampe = timeStampe;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
