package frgp.utn.edu.tpgrupo27;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import utils.RecordatorioReceiver;

public class NotificacionHelper {

    public static void programarNotificacion(Context context, long fecha, int id) {

        Intent intent = new Intent(context, RecordatorioReceiver.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                fecha,
                pendingIntent
        );
    }


}