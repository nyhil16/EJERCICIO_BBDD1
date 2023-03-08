package com.example.ejercicio_bbdd1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //TRABAJO DE NYL
    EditText et1, et2, et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.etCodigo);
        et2 = findViewById(R.id.etDescripcion);
        et3 = findViewById(R.id.etPrecio);
    }

    public void alta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1); //sqlite helper

        SQLiteDatabase db = admin.getWritableDatabase(); //acceso

        try {
            String cod = et1.getText().toString();
            String desc = et2.getText().toString();
            String pre = et3.getText().toString();

            ContentValues registro = new ContentValues(); //values()
            registro.put("codigo", cod);
            registro.put("descripcion", desc);
            registro.put("precio", pre);

            db.insert("articulos", null, registro); //insert
            db.close();

            et1.setText("");
            et2.setText("");
            et3.setText("");

            Toast.makeText(this, "Se cargaron los datos del articulo", Toast.LENGTH_LONG).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Has introducido un valor incorrecto", Toast.LENGTH_LONG).show();
        }

    }

    public void consCod(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1); //sqlite helper

        SQLiteDatabase db = admin.getWritableDatabase(); //acceso

        try {
            String cod = et1.getText().toString();

            Cursor fila = db.rawQuery("SELECT descripcion, precio FROM articulos WHERE codigo="+cod, null); //select
            if (fila.moveToFirst()) {
                et2.setText(fila.getString(0));
                et3.setText(fila.getString(1));
            } else {
                et2.setText("");
                et3.setText("");
                Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_LONG).show();
            }
            db.close();
        } catch(SQLiteException e) {
            Toast.makeText(this, "Has introducido un valor incorrecto", Toast.LENGTH_LONG).show();
        }

    }

    public void consDesc(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1); //sqlite helper

        SQLiteDatabase db = admin.getWritableDatabase(); //acceso

        try {
            String desc = et2.getText().toString();

            Cursor fila = db.rawQuery("SELECT codigo, precio FROM articulos WHERE descripcion='"+desc+"'", null); //select
            if (fila.moveToFirst()) {
                et1.setText(fila.getString(0));
                et3.setText(fila.getString(1));
            } else {
                et1.setText("");
                et3.setText("");
                Toast.makeText(this, "No existe un articulo con dicho descripcion", Toast.LENGTH_LONG).show();
            }
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Has introducido un valor incorrecto", Toast.LENGTH_LONG).show();
        }

    }

    public void bajaCod(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1); //sqlite helper

        SQLiteDatabase db = admin.getWritableDatabase(); //acceso

        try {
            String cod = et1.getText().toString();

            int cant = db.delete("articulos", "codigo="+cod, null); //delete
            db.close();

            et1.setText("");
            et2.setText("");
            et3.setText("");

            if(cant >= 1) {
                Toast.makeText(this, "Se borro el articulo con dicho codigo", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_LONG).show();
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, "Has introducido un valor incorrecto", Toast.LENGTH_LONG).show();
        }

    }

    public void modifica(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1); //sqlite helper

        SQLiteDatabase db = admin.getWritableDatabase(); //acceso

        try {
            String cod = et1.getText().toString();
            String desc = et2.getText().toString();
            String pre = et3.getText().toString();

            ContentValues registro = new ContentValues(); //values()
            registro.put("codigo", cod);
            registro.put("descripcion", desc);
            registro.put("precio", pre);

            int cant = db.update("articulos", registro, "codigo="+cod, null); //update
            db.close();

            if(cant >= 1) {
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No existe un articulo con dicho codigo", Toast.LENGTH_LONG).show();
            }
        } catch(SQLiteException e) {
            Toast.makeText(this, "Has introducido un valor incorrecto", Toast.LENGTH_LONG).show();
        }


    }

    public void limpia(View v) {
        et1.setText("");
        et2.setText("");
        et3.setText("");
    }

}