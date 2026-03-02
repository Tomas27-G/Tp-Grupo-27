package frgp.utn.edu.tpgrupo27.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import frgp.utn.edu.tpgrupo27.database.BaseSQLite;
import frgp.utn.edu.tpgrupo27.entidades.Habito;

public class DaoHabito {

    private BaseSQLite baseDeDatos;

    public DaoHabito(Context context) {
        baseDeDatos = new BaseSQLite(context, "baseDeDatosAPP", null, 1);
    }

    public boolean altaHabito(Habito habito) {

        if (habito.getNombreHabito() != null && !habito.getNombreHabito().isEmpty() &&
                habito.getDescripcionHabito() != null && !habito.getDescripcionHabito().isEmpty() &&
                habito.getFechaInicio() > 0 &&
                habito.getFechaFinal() > 0 &&
                habito.getFrecuencia() > 0 &&
                habito.getHora() != null && !habito.getHora().isEmpty()) {
            return false;
        }

        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombreHabito", habito.getNombreHabito());
        registro.put("descripcionHabito", habito.getDescripcionHabito());
        registro.put("fechaInicio", habito.getFechaInicio());
        registro.put("fechaFinal", habito.getFechaFinal());
        registro.put("frecuencia", habito.getFrecuencia());
        registro.put("hora", habito.getHora());

        baseDatosApp.insert("habitos", null, registro);

        baseDatosApp.close();

        return true;
    }

    public boolean consultaHabito(Habito habito) {
        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();

        String nombre = habito.getNombreHabito();

        if (!nombre.isEmpty()) {
            Cursor fila = baseDatosApp.rawQuery("select descripcionHabito,fechaInicio,fechaFinal,frecuencia,hora from habitos where nombreHabito=" + nombre, null);

            if (fila.moveToFirst()) {
                habito.setDescripcionHabito(fila.getString(0));
                habito.setFechaInicio(fila.getLong(1));
                habito.setFechaFinal(fila.getLong(2));
                habito.setFrecuencia(fila.getInt(3));
                habito.setHora(fila.getString(4));

                fila.close();
                baseDatosApp.close();
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean bajaHabito(Habito habito) {
        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();
        String nombre = habito.getNombreHabito();

        if (!nombre.isEmpty()) {
            int cantidad = baseDatosApp.delete("habitos", "nombreHabito=" + nombre, null);
            baseDatosApp.close();
            if (cantidad == 1) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean modificarHabito(Habito habito) {

        if (habito.getNombreHabito() != null && !habito.getNombreHabito().isEmpty() &&
                habito.getDescripcionHabito() != null && !habito.getDescripcionHabito().isEmpty() &&
                habito.getFechaInicio() > 0 &&
                habito.getFechaFinal() > 0 &&
                habito.getFrecuencia() > 0 &&
                habito.getHora() != null && !habito.getHora().isEmpty()) {
            return false;
        }

        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombreHabito", habito.getNombreHabito());
        registro.put("descripcionHabito", habito.getDescripcionHabito());
        registro.put("fechaInicio", habito.getFechaInicio());
        registro.put("fechaFinal", habito.getFechaFinal());
        registro.put("frecuencia", habito.getFrecuencia());
        registro.put("hora", habito.getHora());

        int cantidad = baseDatosApp.update("habitos", registro, "where nombreHabito=" + habito.getNombreHabito(), null);
        baseDatosApp.close();
        if (cantidad > 0) {
            return true;
        }else{
            return false;
        }

    }

    public java.util.List<Habito> listaDeHabitos(){

        java.util.List<Habito> lista = new java.util.ArrayList<>();

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT nombreHabito, descripcionHabito, fechaInicio, fechaFinal, frecuencia, hora FROM habitos",
                null
        );

        if(cursor.moveToFirst()){
            do{
                Habito h = new Habito();

                h.setNombreHabito(cursor.getString(0));
                h.setDescripcionHabito(cursor.getString(1));
                h.setFechaInicio(cursor.getLong(2));
                h.setFechaFinal(cursor.getLong(3));
                h.setFrecuencia(cursor.getInt(4));
                h.setHora(cursor.getString(5));

                lista.add(h);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lista;
    }

}
