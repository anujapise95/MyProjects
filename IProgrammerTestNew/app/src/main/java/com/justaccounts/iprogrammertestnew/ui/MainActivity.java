package com.justaccounts.iprogrammertestnew.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.justaccounts.iprogrammertestnew.R;
import com.justaccounts.iprogrammertestnew.adapter.CityWeatherAdapter;
import com.justaccounts.iprogrammertestnew.databinding.ActivityMainBinding;
import com.justaccounts.iprogrammertestnew.db.CityWeather;
import com.justaccounts.iprogrammertestnew.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    ActivityMainBinding activityMainBinding;
    WeatherViewModel weatherViewModel;
    private static final String TAG = "MainActivity";
    private CityWeatherAdapter cityWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        Utils.showProgressDialog(this, getString(R.string.loading)).show();
        initUi();
        weatherViewModel.init();
        weatherViewModel.getCityWeatherData().observe(this, cityWeathers -> {
            Utils.hideProgressDialog();
            Log.i(TAG, "onCreate: " + cityWeathers);
            setupRecyclerView(cityWeathers);
        });
        weatherViewModel.error.observe(this, err -> {
            Log.i(TAG, "err: " + err);
            if (err != null && !err.equals("")) {
                Utils.hideProgressDialog();
                activityMainBinding.textInputLayout.setErrorEnabled(true);
                activityMainBinding.textInputLayout.setError(err);
            }
        });
    }

    private void initUi() {
        this.setTitle(getString(R.string.weather_data));
        activityMainBinding.fetchDataBtn.setOnClickListener(this);
        activityMainBinding.cityEd.addTextChangedListener(this);
        ArrayAdapter adapter = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, Utils.CITIES);
        activityMainBinding.cityEd.setAdapter(adapter);
        activityMainBinding.cityEd.setThreshold(1);

    }

    private void setupRecyclerView(List<CityWeather> cityWeathers) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cityWeatherAdapter = new CityWeatherAdapter(this, cityWeathers);
        activityMainBinding.cityWeatherList.setLayoutManager(layoutManager);
        activityMainBinding.cityWeatherList.addItemDecoration(new DividerItemDecoration(activityMainBinding.cityWeatherList.getContext(), DividerItemDecoration.VERTICAL));
        activityMainBinding.cityWeatherList.setAdapter(cityWeatherAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        weatherViewModel.checkDataExpired();
    }

    @Override
    public void onClick(View view) {
        if (!activityMainBinding.cityEd.getText().toString().equals("")) {
            Utils.showProgressDialog(this, getString(R.string.loading)).show();
            weatherViewModel.getWeatherData(activityMainBinding.cityEd.getText().toString());
        } else {
            activityMainBinding.textInputLayout.setErrorEnabled(true);
            activityMainBinding.textInputLayout.setError(getString(R.string.empty_text));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
       clearValidationMsg();
    }

    public void clearValidationMsg() {
        activityMainBinding.textInputLayout.setErrorEnabled(false);
        activityMainBinding.textInputLayout.setError("");
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}