package com.claresti.mistareas.gestordetareas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ServicioNotificaciones extends Service {

    MiTarea miTarea;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("Servicio", "Servicio creado");
        miTarea = new MiTarea();
    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        if(miTarea.getStatus() != AsyncTask.Status.RUNNING){
            miTarea.execute();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        miTarea.cancel(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class MiTarea extends AsyncTask{

        private AdminBD db;
        private boolean flag;
        private long contador;
        private ArrayList<ObjTarea> tareas;
        //private Date horaNotificaciones;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flag = true;
            db = new AdminBD(getApplicationContext());
            tareas = db.selectTareas();
            contador = 36000;
        }


        @Override
        protected Object doInBackground(Object[] params) {
            while(flag){
                if(contador == 0) {
                    tareas = db.selectTareas();
                    if (tareas.size() > 0) {
                        ArrayList<ObjTarea> tareasPendientes = new ArrayList<ObjTarea>();
                        for (ObjTarea t : tareas) {
                            if (t.getCompletado() == 0) {
                                tareasPendientes.add(t);
                            }
                        }
                        ArrayList<ObjTarea> tareasHoy = new ArrayList<ObjTarea>();
                        Date feAc = new Date();
                        for (ObjTarea t : tareasPendientes) {
                            if (t.getFechaEntrega().before(sumarDiasAFecha(feAc, 1))) {
                                tareasHoy.add(t);
                            } else if (t.getFechaEntrega().after(sumarDiasAFecha(feAc, 1)) && t.getFechaEntrega().before(sumarDiasAFecha(feAc, 2))) {
                                tareasHoy.add(t);
                            }
                        }
                        if (tareasHoy.size() > 0) {
                            NotificationManager nManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                    getBaseContext())
                                    .setSmallIcon(R.drawable.icono_not_pe)
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icono_notificacion))
                                    .setContentTitle("Recuerda")
                                    .setContentText("Tienes " + tareasHoy.size() + " tarea pendientes para hoy y mañana")
                                    .setVibrate(new long[]{100, 250, 100, 500})
                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                    .setWhen(System.currentTimeMillis());

                            Intent i = new Intent(ServicioNotificaciones.this, MainActivity.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(ServicioNotificaciones.this, 0, i, 0);
                            builder.setContentIntent(pendingIntent);

                            nManager.notify(12345, builder.build());
                        }
                    }
                    contador = 36000;
                }
                try {
                    Thread.sleep(1000);
                    contador --;
                } catch (InterruptedException e) {
                    Log.e("Dormir acplicacion", e + "");
                }
                Log.i("contador", contador + "");
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            flag = false;
        }

        public Date sumarDiasAFecha(Date fecha, int dias){
            if (dias==0) return fecha;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.add(Calendar.DAY_OF_YEAR, dias);
            return calendar.getTime();
        }
    }
}
