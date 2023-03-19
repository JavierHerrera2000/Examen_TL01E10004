package com.example.tl01e10004.configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConexion extends SQLiteOpenHelper
{

    public SQLiteConexion(@Nullable Context context,
                          @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creacion de objetos de base de datos
        sqLiteDatabase.execSQL(Transacciones.CreateTBContactos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //Eliminacion de objetos de base de datos
        sqLiteDatabase.execSQL(Transacciones.DropTBContactos);
    }
}
