package frgp.utn.edu.tpgrupo27.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import frgp.utn.edu.tpgrupo27.database.BaseSQLite;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;

public class DaoTarea {
    private BaseSQLite baseDeDatos;

    public DaoTarea (Context context){
        baseDeDatos = new BaseSQLite(context,"baseDeDatosAPP",null,1);
    }

    public boolean altaTarea (Tarea tarea){
        if (tarea.getNombreTarea() != null && !tarea.getNombreTarea().isEmpty() &&
                tarea.getDescripcionTarea() != null && !tarea.getDescripcionTarea().isEmpty() &&
                tarea.getFechaInicio() > 0 &&
                tarea.getFechaFinal() > 0 &&
                tarea.getPrioridad() > 0){
            return false;
        }
        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();

        ContentValues registrar = new ContentValues();
        registrar.put("nombreTarea", tarea.getNombreTarea());
        registrar.put("descripcionTarea", tarea.getDescripcionTarea());
        registrar.put("fechaInicio", tarea.getFechaInicio());
        registrar.put("fechaFinal", tarea.getFechaFinal());
        registrar.put("prioridad", tarea.getPrioridad());

        baseDatosApp.insert("tarea", null, registrar);

        baseDatosApp.close();
        return true;
    }

    public boolean buscarTarea (Tarea tarea){
        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();
        if(tarea.getNombreTarea()!= null){
            Cursor fila = baseDatosApp.rawQuery(
                    "SELECT nombreTarea, descripcionTarea, fechaInicio, fechaFinal, prioridad " +
                            "FROM tarea WHERE nombreTarea = "+
                    tarea.getNombreTarea(), null
            );
            if (fila.moveToFirst()){
                tarea.setNombreTarea(fila.getString(0));
                tarea.setDescripcionTarea(fila.getString(1));
                tarea.setFechaInicio(fila.getLong(2));
                tarea.setFechaFinal(fila.getLong(3));
                tarea.setPrioridad(fila.getInt(4));
                baseDatosApp.close();
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
