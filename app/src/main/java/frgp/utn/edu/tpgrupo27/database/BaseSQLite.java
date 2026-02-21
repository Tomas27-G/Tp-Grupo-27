package frgp.utn.edu.tpgrupo27.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseSQLite extends SQLiteOpenHelper{
    public BaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
       BaseDeDatos.execSQL("create table tareas(idTarea int primary key autoincrement, nombreTarea TEXT,descripcionTarea TEXT, fechaInicio INTEGER, fechaFinal INTEGER, prioridad INT)");

       BaseDeDatos.execSQL("create table habitos(idHabito int primary key autoincrement,nombreHabito TEXT, descripcionHabito TEXT, fechaInicio INTEGER, fechaFinal INTEGER, frecuencia INT, hora TEXT )");

       BaseDeDatos.execSQL("create table usuarios(idUsuario int primary key autoincrement, nombre TEXT,apellido TEXT,contrasena TEXT,mail TEXT,fechaNacimiento TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
