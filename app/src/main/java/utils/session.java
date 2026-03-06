package utils;

import android.content.Context;
import android.content.SharedPreferences;

public class session {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public session(Context context){
        pref = context.getSharedPreferences("Sesion", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void crearSesion(int idUsuario, String nombre){

        editor.putBoolean("logueado", true);
        editor.putInt("idUsuario", idUsuario);
        editor.putString("nombre", nombre);

        editor.apply();
    }

    public boolean estaLogueado(){
        return pref.getBoolean("logueado", false);
    }

    public int getIdUsuario(){
        return pref.getInt("idUsuario", -1);
    }

    public String getNombre(){
        return pref.getString("nombre", "Usuario");
    }

    public void cerrarSesion(){
        editor.clear();
        editor.apply();
    }
}