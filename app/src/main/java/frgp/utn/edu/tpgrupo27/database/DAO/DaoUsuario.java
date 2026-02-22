package frgp.utn.edu.tpgrupo27.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import frgp.utn.edu.tpgrupo27.database.BaseSQLite;
import frgp.utn.edu.tpgrupo27.entidades.Usuario;

public class DaoUsuario {

    private BaseSQLite baseDeDatos;
    public DaoUsuario(Context context) {
        baseDeDatos = new BaseSQLite(context, "baseDeDatosAPP", null, 1);
    }
    public boolean altaUsuario(Usuario usuario) {
        SQLiteDatabase baseDatosAPP = baseDeDatos.getWritableDatabase();
        boolean resultado = false;

        try {
            ContentValues registro = new ContentValues();
            registro.put("idUsuario", usuario.getIdUsuario());
            registro.put("nombre", usuario.getNombre());
            registro.put("apellido", usuario.getApellido());
            registro.put("contrasena", usuario.getContrasena());
            registro.put("mail", usuario.getMail());
            registro.put("fechaNacimiento", usuario.getFechaNacimiento());

            long filaInsertada = baseDatosAPP.insert("Usuario", null, registro);

            if (filaInsertada != -1) {
                resultado = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baseDatosAPP.close();
        }

        return resultado;
    }
}
