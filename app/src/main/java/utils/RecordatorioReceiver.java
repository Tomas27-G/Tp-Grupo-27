package utils;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import frgp.utn.edu.tpgrupo27.R;
import frgp.utn.edu.tpgrupo27.database.DAO.DaoHabito;
import frgp.utn.edu.tpgrupo27.database.DAO.DaoTarea;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;
import frgp.utn.edu.tpgrupo27.negocio.negocioTarea;

public class RecordatorioReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        session sesion = new session(context);
        int idUsuario = sesion.getIdUsuario();
        String nombreUsuario = sesion. getNombre();

        negocioTarea negocioTarea = new negocioTarea(context, idUsuario);
        negocioHabito negocioHabito = new negocioHabito(context, idUsuario);


        int tareasPendientes = negocioTarea.contarTareasPendientesHoy();
        int habitosPendientes = negocioHabito.contarHabitosPendientesHoy();

        int totalPendientes = tareasPendientes + habitosPendientes;

        // Si no hay nada pendiente no envía notificación
        if (totalPendientes == 0) {
            return;
        }

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "recordatorios";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            channelId,
                            "Recordatorios",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Recordatorio")
                        .setContentText("Tenés " + totalPendientes + " tareas o hábitos pendientes")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

        manager.notify(1, builder.build());
    }
}