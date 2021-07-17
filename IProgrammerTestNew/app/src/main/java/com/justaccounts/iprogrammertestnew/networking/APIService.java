package com.justaccounts.iprogrammertestnew.networking;



import com.justaccounts.iprogrammertestnew.model.WeatherBase;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface APIService {
    @GET("weather")
    Call<WeatherBase> getWeather(@QueryMap HashMap<String,String> currentLocation);
}
