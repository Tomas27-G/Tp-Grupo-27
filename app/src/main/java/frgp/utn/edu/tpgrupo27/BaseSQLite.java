package frgp.utn.edu.tpgrupo27;
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
       BaseDeDatos.execSQL("create table tareas(idTarea int primary key, nombreTarea text,descripcion TEXT, fechaInicio TEXT, fechaFin TEXT, prioridad INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
