package frgp.utn.edu.tpgrupo27.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import frgp.utn.edu.tpgrupo27.database.BaseSQLite;
import frgp.utn.edu.tpgrupo27.entidades.Habito;

public class DaoHabito {

    private BaseSQLite baseDeDatos;

    public DaoHabito(Context context) {
        baseDeDatos = new BaseSQLite(context, "baseDeDatosAPP", null, 1);
    }

    public boolean altaHabito(Habito habito) {

        if(habito.getNombreHabito() != null && !habito.getNombreHabito().isEmpty() &&
                habito.getDescripcionHabito() != null && !habito.getDescripcionHabito().isEmpty() &&
                habito.getFechaInicio() > 0 &&
                habito.getFechaFinal() > 0 &&
                habito.getFrecuencia() > 0 &&
                habito.getHora() != null && !habito.getHora().isEmpty()){
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
}