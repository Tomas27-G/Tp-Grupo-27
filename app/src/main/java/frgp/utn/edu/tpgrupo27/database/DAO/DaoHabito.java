package frgp.utn.edu.tpgrupo27.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import frgp.utn.edu.tpgrupo27.database.BaseSQLite;
import frgp.utn.edu.tpgrupo27.entidades.Habito;

import java.util.ArrayList;
import java.util.List;

public class DaoHabito {

    private BaseSQLite baseDeDatos;

    public DaoHabito(Context context) {
        baseDeDatos = new BaseSQLite(context, "baseDeDatosAPP", null, 1);
    }

    public boolean altaHabito(Habito habito) {
        if (habito.getNombreHabito() != null && !habito.getNombreHabito().isEmpty() &&
                habito.getDescripcionHabito() != null && !habito.getDescripcionHabito().isEmpty() &&
                habito.getFechaInicio() > 0 &&
                habito.getFrecuencia() > 0 ) {

            SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("nombreHabito", habito.getNombreHabito());
            registro.put("descripcionHabito", habito.getDescripcionHabito());
            registro.put("fechaInicio", habito.getFechaInicio());
            registro.put("frecuencia", habito.getFrecuencia());
            registro.put("checkeado", habito.getCheckeado() ? 1 : 0);

            long resultado = baseDatosApp.insert("habitos", null, registro);

            baseDatosApp.close();
            return resultado != -1;
        }
        return false;
    }

    public boolean consultaHabito(Habito habito) {
        SQLiteDatabase baseDatosApp = baseDeDatos.getReadableDatabase();

        int id = habito.getIdHabito();

        if ( id >0 ) {
            Cursor fila = baseDatosApp.rawQuery(
                    "SELECT descripcionHabito, fechaInicio, frecuencia, checkeado FROM habitos WHERE idHabito = ?",
                    new String[]{String.valueOf(habito.getIdHabito())}
            );

            if (fila.moveToFirst()) {
                habito.setDescripcionHabito(fila.getString(0));
                habito.setFechaInicio(fila.getLong(1));
                habito.setFrecuencia(fila.getInt(2));
                habito.setCheckeado(fila.getInt(3) == 1);

                fila.close();
                baseDatosApp.close();
                return true;
            }
            fila.close();
        }
        baseDatosApp.close();
        return false;
    }

    public boolean bajaHabito(Habito habito) {

        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();

        int id = habito.getIdHabito();

        if(id > 0){
            int cantidad = baseDatosApp.delete(
                    "habitos",
                    "idHabito = ?",
                    new String[]{String.valueOf(id)}
            );

            baseDatosApp.close();
            return cantidad == 1;
        }

        baseDatosApp.close();
        return false;
    }

    public boolean modificarHabito(String nombreOriginal, Habito habitoModificado) {

        if (habitoModificado.getNombreHabito() == null || habitoModificado.getNombreHabito().isEmpty() ||
                habitoModificado.getDescripcionHabito() == null || habitoModificado.getDescripcionHabito().isEmpty() ||
                habitoModificado.getFechaInicio() <= 0 ||
                habitoModificado.getFrecuencia() <= 0) {
            return false;
        }

        SQLiteDatabase baseDatosApp = baseDeDatos.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombreHabito", habitoModificado.getNombreHabito());
        registro.put("descripcionHabito", habitoModificado.getDescripcionHabito());
        registro.put("fechaInicio", habitoModificado.getFechaInicio());
        registro.put("frecuencia", habitoModificado.getFrecuencia());
        registro.put("checkeado", habitoModificado.getCheckeado() ? 1 : 0);

        int cantidad = baseDatosApp.update("habitos", registro, "nombreHabito = ?", new String[]{nombreOriginal});
        baseDatosApp.close();

        return cantidad > 0;
    }
    public boolean actualizarCheckeado(int id, boolean estado){

        SQLiteDatabase db = baseDeDatos.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("checkeado", estado ? 1 : 0);

        int filas = db.update(
                "habitos",
                values,
                "idHabito = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return filas > 0;
    }
    public List<Habito> listaDeHabitos() {

        List<Habito> lista = new ArrayList<>();

        SQLiteDatabase db = baseDeDatos.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT idHabito, nombreHabito, descripcionHabito, fechaInicio, frecuencia, checkeado FROM habitos",
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {

                Habito h = new Habito();

                h.setIdHabito(cursor.getInt(0));
                h.setNombreHabito(cursor.getString(1));
                h.setDescripcionHabito(cursor.getString(2));
                h.setFechaInicio(cursor.getLong(3));
                h.setFrecuencia(cursor.getInt(4));
                h.setCheckeado(cursor.getInt(5) == 1);

                lista.add(h);

            } while (cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }

        db.close();

        return lista;
    }

}