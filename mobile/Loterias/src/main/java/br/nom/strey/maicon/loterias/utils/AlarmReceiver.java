package br.nom.strey.maicon.loterias.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by maicon on 26/10/13.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // When our Alaram time is triggered , this method will be excuted (onReceive)
        // We're invoking a service in this method which shows Notification to the User

        Intent myIntent = new Intent(context, NotificationService.class);
        context.startService(myIntent);
    }


}