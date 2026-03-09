package frgp.utn.edu.tpgrupo27.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import frgp.utn.edu.tpgrupo27.database.BaseSQLite;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;

public class DaoTarea {

    private BaseSQLite baseDeDatos;

    public DaoTarea(Context context){
        baseDeDatos = new BaseSQLite(context,"baseDeDatosAPP",null,1);
    }

    public boolean altaTarea(Tarea tarea){

        if (tarea.getNombreTarea() != null && !tarea.getNombreTarea().isEmpty() &&
                tarea.getDescripcionTarea() != null && !tarea.getDescripcionTarea().isEmpty() &&
                tarea.getFechaInicio() > 0 &&
                tarea.getFechaFinal() > 0 &&
                tarea.getPrioridad() > 0){

            SQLiteDatabase db = baseDeDatos.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombreTarea", tarea.getNombreTarea());
            values.put("descripcionTarea", tarea.getDescripcionTarea());
            values.put("fechaInicio", tarea.getFechaInicio());
            values.put("fechaFinal", tarea.getFechaFinal());
            values.put("prioridad", tarea.getPrioridad());

            long resultado = db.insert("tareas", null, values);

            db.close();

            return resultado != -1;
        }

        return false;
    }

    public boolean buscarTarea(Tarea tarea){

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor fila = db.rawQuery(
                "SELECT nombreTarea, descripcionTarea, fechaInicio, fechaFinal, prioridad FROM tareas WHERE nombreTarea = ?",
                new String[]{tarea.getNombreTarea()}
        );

        if (fila.moveToFirst()){

            tarea.setNombreTarea(fila.getString(0));
            tarea.setDescripcionTarea(fila.getString(1));
            tarea.setFechaInicio(fila.getLong(2));
            tarea.setFechaFinal(fila.getLong(3));
            tarea.setPrioridad(fila.getInt(4));

            fila.close();
            db.close();
            return true;
        }

        fila.close();
        db.close();
        return false;
    }

    public List<Tarea> listarTareas(){

        List<Tarea> lista = new ArrayList<>();

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT nombreTarea, descripcionTarea, fechaInicio, fechaFinal, prioridad FROM tareas",
                null
        );

        if (cursor.moveToFirst()){
            do{
                Tarea tarea = new Tarea();

                tarea.setNombreTarea(cursor.getString(0));
                tarea.setDescripcionTarea(cursor.getString(1));
                tarea.setFechaInicio(cursor.getLong(2));
                tarea.setFechaFinal(cursor.getLong(3));
                tarea.setPrioridad(cursor.getInt(4));

                lista.add(tarea);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

    public boolean modificarTarea(Tarea tarea){

        SQLiteDatabase db = baseDeDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("descripcionTarea", tarea.getDescripcionTarea());
        values.put("fechaInicio", tarea.getFechaInicio());
        values.put("fechaFinal", tarea.getFechaFinal());
        values.put("prioridad", tarea.getPrioridad());

        int filas = db.update(
                "tareas",
                values,
                "nombreTarea = ?",
                new String[]{tarea.getNombreTarea()}
        );

        db.close();

        return filas > 0;
    }

    public boolean bajaTarea(Tarea tarea){

        SQLiteDatabase db = baseDeDatos.getWritableDatabase();

        int filas = db.delete(
                "tareas",
                "nombreTarea = ?",
                new String[]{tarea.getNombreTarea()}
        );

        db.close();

        return filas > 0;
    }
}