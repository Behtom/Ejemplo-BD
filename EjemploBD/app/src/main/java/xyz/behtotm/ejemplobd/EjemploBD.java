package xyz.behtotm.ejemplobd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mrmar on 05/06/2017.
 */

public class EjemploBD extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "prueba.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLA_NOMBRES = "persona";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_APELLIDOS = "apellidos";
    public static final String COLUMNA_EMAIL = "email";
    public static final String COLUMNA_TELEFONO = "telefono";

    private static final String SQL_CREAR = "create table "
            + TABLA_NOMBRES + "("
            + COLUMNA_ID + " integer primary key autoincrement, "
            + COLUMNA_NOMBRE + " text not null, "
            + COLUMNA_APELLIDOS + " text not null, "
            + COLUMNA_EMAIL + " text not null, "
            + COLUMNA_TELEFONO + " text not null);";

    public EjemploBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int agregar(String nombre, String apellidos, String email, String telefono){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMNA_NOMBRE, nombre);
        values.put(COLUMNA_APELLIDOS, apellidos);
        values.put(COLUMNA_EMAIL, email);
        values.put(COLUMNA_TELEFONO, telefono);

        long newRowId;
        newRowId = db.insert(TABLA_NOMBRES, null, values);
        db.close();

        return (int)newRowId;
    }

    public String obtener(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ID, COLUMNA_NOMBRE, COLUMNA_APELLIDOS, COLUMNA_EMAIL, COLUMNA_TELEFONO};

        Cursor cursor =
                db.query(TABLA_NOMBRES,
                        projection,
                        " _id = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);


        if (cursor != null)
            cursor.moveToFirst();

        db.close();
        try {
            return cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "La lectura de datos falló.";
        }
    }
}
