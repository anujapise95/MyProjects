package com.justaccounts.iprogrammertestnew.utils;

import android.app.ProgressDialog;
import android.content.Context;

import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    private static ProgressDialog loadingDialog;
    public static String getFarhToCelcius(String farh) {
        Double fahrenheit = Double.parseDouble(farh);
        Double celciusVal = fahrenheit - 32;
        Double c = celciusVal * 5 / 9;
        String convertedval = String.valueOf(c);
        return convertedval;
    }

    public static String setTwoDecimalPlaces(String price) {
        Locale locale = Locale.ENGLISH;
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        // for trailing zeros:
        nf.setMinimumFractionDigits(2);
        // round to 2 digits:
        nf.setMaximumFractionDigits(2);
        return String.valueOf(nf.format(Float.valueOf(price)));
    }

    public static ProgressDialog showProgressDialog(Context context, String mMessage) {
        hideProgressDialog();
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setMessage(mMessage);
        loadingDialog.setIndeterminate(false);
        loadingDialog.setCancelable(false);
        return loadingDialog;
    }

    ///dismiss loading dialog
    public static void hideProgressDialog() {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static final String[] CITIES = new String[] {
            "Nashik", "Pune", "Mumbai", "Nagpur", "Amravati","Kolhapur","Akola","Yavatmal","Satara","Nandurbar","Bhusawal","Ratnagiri","Dhule"
    };
}
