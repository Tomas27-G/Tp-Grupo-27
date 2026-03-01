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
        BaseDeDatos.execSQL("CREATE TABLE tareas(idTarea INTEGER PRIMARY KEY AUTOINCREMENT, nombreTarea TEXT, descripcionTarea TEXT, fechaInicio INTEGER, fechaFinal INTEGER, prioridad INT)");

        BaseDeDatos.execSQL("CREATE TABLE habitos(idHabito INTEGER PRIMARY KEY AUTOINCREMENT, nombreHabito TEXT, descripcionHabito TEXT, fechaInicio INTEGER, fechaFinal INTEGER, frecuencia INT, hora TEXT)");

        BaseDeDatos.execSQL("CREATE TABLE usuarios(idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, contrasena TEXT, mail TEXT, fechaNacimiento TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
