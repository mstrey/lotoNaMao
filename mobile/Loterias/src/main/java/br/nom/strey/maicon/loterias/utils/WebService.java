package br.nom.strey.maicon.loterias.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class WebService {

    public static final Integer DISCONNECTED = 0;
    public static final Integer CONNECTED_3G = 1;
    public static final Integer CONNECTED_WIFI = 2;

    public static Integer isConnected(Context ctx) {
        Integer retorno = DISCONNECTED;
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                //handler.sendEmptyMessage(0);
                Log.d("WebService", "Status de conexão 3G: " + cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
                retorno = CONNECTED_3G;
            } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                //handler.sendEmptyMessage(0);
                Log.d("WebService", "Status de conexão Wifi: " + cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
                retorno = CONNECTED_WIFI;
            } else {
                //handler.sendEmptyMessage(0);
                Log.e("WebService", "Status de conexão Wifi: " + cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
                Log.e("WebService", "Status de conexão 3G: " + cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
                // TODO: exibir um toast informando que nenhuma conexão foi localizada.
                retorno = DISCONNECTED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            retorno = DISCONNECTED;
        }
        return retorno;
    }

}