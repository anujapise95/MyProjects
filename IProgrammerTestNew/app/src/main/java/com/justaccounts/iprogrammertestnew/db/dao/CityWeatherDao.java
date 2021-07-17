package com.justaccounts.iprogrammertestnew.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.justaccounts.iprogrammertestnew.db.CityWeather;

import java.util.List;

@Dao
public interface CityWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCityWeather(CityWeather menu);

    @Query("SELECT * from cityweather ORDER BY timeStampe ASC")
    LiveData<List<CityWeather>> getAllCityWeather();


    @Query("SELECT EXISTS (SELECT 1 FROM cityweather WHERE cityName LIKE :city)")
    Boolean cityAlreadyExists(String city);

//    @Query("DELETE FROM cityweather WHERE timeStamp < (UNIX_TIMESTAMP() - 600)")
//    void deleteCityData(String city);

    //    @Query("DELETE FROM cityweather where datetime(dateTime) >=datetime('now', '-3 Minute')")
//    void deleteCityData();

    @Query("DELETE FROM cityweather where timeStampe<= :timeStamp")
    void deleteCityData(long timeStamp);

}
