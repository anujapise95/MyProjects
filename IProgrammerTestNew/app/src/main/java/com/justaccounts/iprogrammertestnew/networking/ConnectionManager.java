package com.justaccounts.iprogrammertestnew.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionManager {
    private Context mContext;

    public ConnectionManager(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Description:Used to check connection of Internet
     *
     * @return
     */
    public boolean isConnectingToInternet() {
        boolean isNoInternet = false;
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isNoInternet = true;
                    }

        }
        if (!isNoInternet) {
            Log.i("Connection", "isConnectingToInternet: Not connected !!");
            //Toast.makeText(mContext, mContext.getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
        }
        return isNoInternet;
    }
}
