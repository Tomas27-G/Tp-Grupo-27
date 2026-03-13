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
        SQLiteDatabase db = baseDeDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", usuario.getNombre());
        values.put("apellido", usuario.getApellido());
        values.put("mail", usuario.getMail());
        values.put("contrasena", usuario.getContrasena());
        values.put("fechaNacimiento", usuario.getFechaNacimiento());

        long idGenerado = db.insert("usuarios", null, values);
        db.close();

        if (idGenerado != -1) {
            usuario.setIdUsuario((int) idGenerado);
            return true;
        } else {
            return false;
        }
    }

    public boolean modificarUsuario(Usuario usuario) {
        SQLiteDatabase baseDatosAPP = baseDeDatos.getWritableDatabase();
        boolean resultado = false;

        try {
            ContentValues registro = new ContentValues();

            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                registro.put("nombre", usuario.getNombre());
            }
            if (usuario.getApellido() != null && !usuario.getApellido().isEmpty()) {
                registro.put("apellido", usuario.getApellido());
            }
            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
                registro.put("contrasena", usuario.getContrasena());
            }
            if (usuario.getMail() != null && !usuario.getMail().isEmpty()) {
                registro.put("mail", usuario.getMail());
            }
            if (usuario.getFechaNacimiento() != null && !usuario.getFechaNacimiento().isEmpty()) {
                registro.put("fechaNacimiento", usuario.getFechaNacimiento());
            }

            if (registro.size() > 0) {
                int filasAfectadas = baseDatosAPP.update(
                        "usuarios",
                        registro,
                        "idUsuario = ?",
                        new String[]{String.valueOf(usuario.getIdUsuario())}
                );

                if (filasAfectadas > 0) {
                    resultado = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baseDatosAPP.close();
        }

        return resultado;
    }

    public Usuario loginUsuario(String mail, String contrasena) {
        SQLiteDatabase baseDatosAPP = baseDeDatos.getReadableDatabase();
        Usuario usuario = null;

        try {
            Cursor cursor = baseDatosAPP.rawQuery(
                    "SELECT * FROM usuarios WHERE mail = ? AND contrasena = ?",
                    new String[]{mail, contrasena}
            );

            if (cursor.moveToFirst()) {
                usuario = new Usuario();
                usuario.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")));
                usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
                usuario.setApellido(cursor.getString(cursor.getColumnIndexOrThrow("apellido")));
                usuario.setContrasena(cursor.getString(cursor.getColumnIndexOrThrow("contrasena")));
                usuario.setMail(cursor.getString(cursor.getColumnIndexOrThrow("mail")));
                usuario.setFechaNacimiento(cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")));
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baseDatosAPP.close();
        }

        return usuario;
    }


    public boolean eliminarUsuario(Usuario usuario) {
        SQLiteDatabase baseDatosAPP = baseDeDatos.getWritableDatabase();
        boolean resultado = false;

        try {
            int filasAfectadas = baseDatosAPP.delete(
                    "usuarios",
                    "idUsuario = ?",
                    new String[]{String.valueOf(usuario.getIdUsuario())}
            );

            if (filasAfectadas > 0) {
                resultado = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baseDatosAPP.close();
        }

        return resultado;
    }

    public boolean verificarMail(String mail){
        SQLiteDatabase baseDatosAPP = baseDeDatos.getReadableDatabase();

        Cursor cursor = baseDatosAPP.rawQuery("SELECT * FROM usuarios WHERE mail = ?",
                new String[]{mail});

        if (cursor.moveToFirst()){
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }
}
