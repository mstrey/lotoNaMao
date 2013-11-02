package br.nom.strey.maicon.loterias.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import br.nom.strey.maicon.loterias.R;
import br.nom.strey.maicon.loterias.main.LoteriaListActivity;
import br.nom.strey.maicon.loterias.megasena.MegaListFragment;

/**
 * Created by maicon on 26/10/13.
 */
public class NotificationService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void showNotification() {

        int icon = R.drawable.ic_launcher;

        CharSequence tickerText = " 3:10 manhã - " + getString(R.string.notifica_novo_resultado);

        long when = System.currentTimeMillis();
        Context context = getApplicationContext();
        CharSequence contentTitle = getString(R.string.app_name);

        Intent notificationIntent = new Intent(this, LoteriaListActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification(icon, tickerText, when);

        notification.setLatestEventInfo(context, contentTitle, tickerText, contentIntent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
        mNotificationManager.notify(1, notification);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        MegaListFragment megaListFragment = new MegaListFragment();

//        if (megaListFragment.verificaResultados()){
//            showNotification();
//        };

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


}