package com.justaccounts.iprogrammertestnew.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.justaccounts.iprogrammertestnew.db.CityWeather;
import com.justaccounts.iprogrammertestnew.db.WeatherDatabase;
import com.justaccounts.iprogrammertestnew.db.dao.CityWeatherDao;
import com.justaccounts.iprogrammertestnew.model.WeatherBase;
import com.justaccounts.iprogrammertestnew.networking.APIService;
import com.justaccounts.iprogrammertestnew.networking.ApiUtils;
import com.justaccounts.iprogrammertestnew.networking.ConnectionManager;
import com.justaccounts.iprogrammertestnew.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends AndroidViewModel {
    final MutableLiveData<WeatherBase> weatherData = new MutableLiveData<>();
    LiveData<List<CityWeather>> cityWeatherData;
    public MutableLiveData<String> error = new MutableLiveData<>();

    Application application;
    private static final String TAG = "BloodPressureViewModel";
    WeatherDatabase weatherDatabase;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        weatherDatabase = WeatherDatabase.getDatabase(application);
    }

    public void init() {
        // to vanish the records if its time stamp is less that 10 min of current timestamp
        weatherDatabase.databaseWriteExecutor.execute(()->{
            weatherDatabase.cityWeatherDao().deleteCityData(System.currentTimeMillis()-600000);
        });
        cityWeatherData = weatherDatabase.cityWeatherDao().getAllCityWeather();
    }

    private APIService mAPIService = ApiUtils.getAPIService();


    public void getWeatherData(String city) {
        HashMap<String, String> params = new HashMap<>();
        params.put("q", city);
        params.put("appid", Constants.API_KEY);
        if (new ConnectionManager(application).isConnectingToInternet()) {
            mAPIService.getWeather(params).enqueue(new Callback<WeatherBase>() {
                @Override
                public void onResponse(Call<WeatherBase> call,
                                       Response<WeatherBase> response) {
                    if (response.isSuccessful()) {
                        error.postValue("");
                        weatherData.setValue(response.body());
                        WeatherBase weatherBase = weatherData.getValue();
                        weatherDatabase.databaseWriteExecutor.execute(() -> {
                            boolean val = weatherDatabase.cityWeatherDao().cityAlreadyExists(city);
                            Log.i(TAG, "onResponse: " + val);
                            if (!weatherDatabase.cityWeatherDao().cityAlreadyExists(city)) {
                                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT);
                                String strDate = sdf.format(new Date());
                                weatherDatabase.cityWeatherDao().insertCityWeather(new CityWeather(weatherBase.getId().toString(), weatherBase.getName(), weatherBase.getMain().getTemp().toString(), System.currentTimeMillis(), strDate,
                                        weatherBase.getWeather() != null && weatherBase.getWeather().size() > 0 && weatherBase.getWeather().get(0).getIcon() != null ? weatherBase.getWeather().get(0).getIcon() : "",
                                        (weatherBase.getMain()!=null && weatherBase.getMain().getHumidity()!=null) ? weatherBase.getMain().getHumidity().toString():""));
                            } else {
                                error.postValue("This city's weather record already exists");
                            }
                        });
                    } else {
                        error.postValue("Please enter valid city");
                    }
                }

                @Override
                public void onFailure(Call<WeatherBase> call, Throwable t) {
                    error.postValue(t.toString());
                }
            });
        }else {
            error.postValue("Please check your internet connection to get weather data");
        }
    }


    public LiveData<List<CityWeather>> getCityWeatherData() {
        return cityWeatherData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
