package com.justaccounts.iprogrammertestnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.justaccounts.iprogrammertestnew.R;
import com.justaccounts.iprogrammertestnew.databinding.ItemCityWeatherBinding;
import com.justaccounts.iprogrammertestnew.db.CityWeather;
import com.justaccounts.iprogrammertestnew.utils.Constants;
import com.justaccounts.iprogrammertestnew.utils.Utils;

import java.util.List;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.WeatherViewHolder> {
    private static final String TAG = "CityWeatherAdapter";
    List<CityWeather> cityWeatherArrayList;
    Context context;

    public CityWeatherAdapter(Context context, List<CityWeather> menuMainCatArrayList) {
        this.context = context;
        this.cityWeatherArrayList = menuMainCatArrayList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCityWeatherBinding itemCityWeatherBinding = DataBindingUtil.inflate(inflater, R.layout.item_city_weather, parent, false);
        return new WeatherViewHolder(itemCityWeatherBinding.getRoot(), itemCityWeatherBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        CityWeather menu = cityWeatherArrayList.get(position);

        Glide.with(context)  //2
                .load(Constants.IMAGE_BASE_URL+menu.getImg()+".png") //3
                .fitCenter() //4
                .placeholder(R.drawable.no_picture_available) //5
                .error(R.drawable.no_picture_available) //6
                .fallback(R.drawable.no_picture_available) //7
                .into( holder.itemFavMenuRowBinding.weatherImage);
        holder.itemFavMenuRowBinding.cityTv.setText(menu.getCityName());
        if (menu.getTemp() != null)
            holder.itemFavMenuRowBinding.tempTv.setText(Utils.setTwoDecimalPlaces(Utils.getFarhToCelcius(menu.getTemp())) + context.getString(R.string.celcius_symb));
        if (!menu.getHumidity().equals(""))
            holder.itemFavMenuRowBinding.humidity.setText(context.getString(R.string.humidity)+menu.getHumidity());
        holder.itemFavMenuRowBinding.dateTime.setText(context.getString(R.string.last_date_time)+menu.getDateTime());
    }

    @Override
    public int getItemCount() {
        return cityWeatherArrayList.size();
    }


    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        ItemCityWeatherBinding itemFavMenuRowBinding;

        public WeatherViewHolder(@NonNull View itemView, ItemCityWeatherBinding itemFavMenuRowBinding) {
            super(itemView);
            this.itemFavMenuRowBinding = itemFavMenuRowBinding;
        }

    }


}
