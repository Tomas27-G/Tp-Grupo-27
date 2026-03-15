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

    private int idUsuarioTarea;

    public DaoTarea(Context context, int idUsuarioTarea){
        baseDeDatos = new BaseSQLite(context,"baseDeDatosAPP",null,1);
        this.idUsuarioTarea = idUsuarioTarea;
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
            values.put("idUsuario",idUsuarioTarea);

            // NUEVO
            values.put("checkeado", tarea.getCheckeado() ? 1 : 0);

            long resultado = db.insert("tareas", null, values);

            db.close();

            return resultado != -1;
        }

        return false;
    }

    public boolean buscarTarea(Tarea tarea){

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor fila = db.rawQuery(
                "SELECT nombreTarea, descripcionTarea, fechaInicio, fechaFinal, prioridad, checkeado FROM tareas WHERE idTarea = ? AND idUsuario = ?",
                new String[]{String.valueOf(tarea.getIdTarea()), String.valueOf(idUsuarioTarea)}
        );

        if (fila.moveToFirst()){

            tarea.setNombreTarea(fila.getString(0));
            tarea.setDescripcionTarea(fila.getString(1));
            tarea.setFechaInicio(fila.getLong(2));
            tarea.setFechaFinal(fila.getLong(3));
            tarea.setPrioridad(fila.getInt(4));

            // NUEVO
            tarea.setCheckeado(fila.getInt(5) == 1);

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
                "SELECT idTarea, nombreTarea, descripcionTarea, fechaInicio, fechaFinal, prioridad, checkeado FROM tareas  WHERE idUsuario = ?",
                new String[]{String.valueOf(idUsuarioTarea)}
        );

        if (cursor != null && cursor.moveToFirst()){
            do{
                Tarea tarea = new Tarea();

                tarea.setIdTarea(cursor.getInt(0));
                tarea.setNombreTarea(cursor.getString(1));
                tarea.setDescripcionTarea(cursor.getString(2));
                tarea.setFechaInicio(cursor.getLong(3));
                tarea.setFechaFinal(cursor.getLong(4));
                tarea.setPrioridad(cursor.getInt(5));

                // NUEVO
                tarea.setCheckeado(cursor.getInt(6) == 1);

                lista.add(tarea);

            }while(cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }

        db.close();

        return lista;
    }

    public boolean modificarTarea(Tarea tarea){

        SQLiteDatabase db = baseDeDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombreTarea", tarea.getNombreTarea());
        values.put("descripcionTarea", tarea.getDescripcionTarea());
        values.put("fechaInicio", tarea.getFechaInicio());
        values.put("fechaFinal", tarea.getFechaFinal());
        values.put("prioridad", tarea.getPrioridad());
        values.put("checkeado", tarea.getCheckeado() ? 1 : 0);

        int filas = db.update(
                "tareas",
                values,
                "idTarea = ? AND idUsuario = ?",
                new String[]{String.valueOf(tarea.getIdTarea()), String.valueOf(idUsuarioTarea)}
        );

        db.close();

        return filas > 0;
    }

    // NUEVA FUNCION
    public boolean actualizarCheckeado(int idTarea, boolean checkeado){

        SQLiteDatabase db = baseDeDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("checkeado", checkeado ? 1 : 0);

        int filas = db.update(
                "tareas",
                values,
                "idTarea = ? AND idUsuario = ?",
                new String[]{String.valueOf(idTarea), String.valueOf(idUsuarioTarea)}
        );

        db.close();

        return filas > 0;
    }

    public boolean bajaTarea(Tarea tarea){

        SQLiteDatabase db = baseDeDatos.getWritableDatabase();

        int filas = db.delete(
                "tareas",
                "idTarea = ? AND idUsuario = ?",
                new String[]{String.valueOf(tarea.getIdTarea()), String.valueOf(idUsuarioTarea)}
        );

        db.close();

        return filas > 0;
    }

    public int contarTareasHechas(){

        int cantidad = 0;

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM tareas WHERE checkeado = 1 AND idUsuario = ?",
                new String[]{String.valueOf(idUsuarioTarea)}
        );

        if(cursor.moveToFirst()){
            cantidad = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return cantidad;
    }
    public int contarTareasPendientes(){

        int cantidad = 0;

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM tareas WHERE checkeado = 0 AND idUsuario = ?",
                new String[]{String.valueOf(idUsuarioTarea)}
        );

        if(cursor.moveToFirst()){
            cantidad = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return cantidad;
    }
    public int contarTareasPendientesHoy(){

        int cantidad = 0;

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        long hoy = System.currentTimeMillis();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM tareas " +
                        "WHERE checkeado = 0 " +
                        "AND idUsuario = ? " +
                        "AND fechaInicio <= ? " +
                        "AND fechaFinal >= ?",
                new String[]{
                        String.valueOf(idUsuarioTarea),
                        String.valueOf(hoy),
                        String.valueOf(hoy)
                }
        );

        if(cursor.moveToFirst()){
            cantidad = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return cantidad;
    }
}