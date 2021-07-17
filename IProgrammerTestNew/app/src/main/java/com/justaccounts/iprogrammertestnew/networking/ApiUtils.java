package com.justaccounts.iprogrammertestnew.networking;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
