package com.example.basededatossqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basededatossqlite.modelo.AdminSqliteOpenHelper;

public class MainActivity extends AppCompatActivity {
    private final AdminSqliteOpenHelper adminDb = new AdminSqliteOpenHelper(this);
    private static final String TABLE_NAME = "articulos";
    private EditText etCodigo;
    private EditText etPrecio;
    private EditText etDescripcion;


    private Button btnAlta;
    private Button btnBuscarPorCodigo;
    private Button btnBaja;
    private Button btnActualizar;
    private Button btnBuscarporDescripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button info = findViewById(R.id.libro);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Libro.class);
                startActivityForResult(intent, 0);

                Button libro = findViewById(R.id.libro);
                libro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (v.getContext(), Libro.class);
                        startActivityForResult(intent, 0);
                    }
                });



                Button libro2 = findViewById(R.id.libro2);
                libro2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (v.getContext(), Libro2.class);
                        startActivityForResult(intent, 0);
                    }
                });


                Button libro3 = findViewById(R.id.libro3);
                libro3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (v.getContext(), Libro3.class);
                        startActivityForResult(intent, 0);
                    }
                });

                Button libro4 = findViewById(R.id.libro4);
                libro4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (v.getContext(), Libro4.class);
                        startActivityForResult(intent, 0);
                    }
                });
                Button libro5 = findViewById(R.id.libro5);
                libro5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (v.getContext(), Libro5.class);
                        startActivityForResult(intent, 0);
                    }
                });

                Button libro6 = findViewById(R.id.libro6);
                libro6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent (v.getContext(), Libro6.class);
                        startActivityForResult(intent, 0);
                    }
                });

            }
        });
        etCodigo = findViewById(R.id.et_codigo);
        etPrecio = findViewById(R.id.et_precio);
        etDescripcion = findViewById(R.id.et_descripcion);
        btnAlta = findViewById(R.id.btn_alta);
        btnBuscarPorCodigo = findViewById(R.id.btn_buscar_codigo);
        btnBaja=findViewById(R.id.btn_baja);
        btnActualizar=findViewById(R.id.btn_actualizar);
        btnBuscarporDescripcion=findViewById(R.id.btn_buscar_descripcion);

        btnAlta.setOnClickListener(view -> { alta(); });
            btnBuscarPorCodigo.setOnClickListener(view -> { buscarPorCodigo();});
        btnBaja.setOnClickListener(view -> bajaArticulo());
        btnActualizar.setOnClickListener(view -> actualizarArticulo());
        btnBuscarporDescripcion.setOnClickListener(view -> BuscarporDescripcion());
        }

    private void BuscarporDescripcion() {
        String descripcion = etDescripcion.getText().toString();
        if (descripcion.length() > 0) {
            SQLiteDatabase db = adminDb.getWritableDatabase();
            String where = "descripcion = ? "; //WHERE
            String[] objectArray = {descripcion}; //WHERE VALUES
            Cursor cursor = db.query(
                    TABLE_NAME,
                    null,
                    where,
                    objectArray,
                    null,
                    null,
                    null
            );
            if (cursor.moveToNext()) {
                @SuppressLint("Range") String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                @SuppressLint("Range")
                String precio = etPrecio.getText().toString();
                etPrecio.setText(precio.toString());
                etCodigo.setText(codigo);
            }else {
                Toast.makeText(this, "Libro : " + descripcion + "No existe en la base de datos ",
                        Toast.LENGTH_LONG).show();
            }

        } else
            Toast.makeText(this, "El campo no debe estar vacio ",
                    Toast.LENGTH_LONG).show();

    }


    private void actualizarArticulo() {
        String codigo = etCodigo.getText().toString();
        String precio = etPrecio.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        SQLiteDatabase db = adminDb.getWritableDatabase();
        if(codigo.length() >0){
            ContentValues content = new ContentValues();
            content.put("precio",precio);
            content.put("descripcion",descripcion);
            String where = "codigo = ?";
            String[] whereparams = {codigo};

            int actualizados = db.update(TABLE_NAME,content,where,whereparams);
            if(actualizados >0){
                Toast.makeText(this,"Libro: "+codigo+ "se ha actualizado correctamente",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(this,"Libro no encontrado en la base detos",Toast.LENGTH_LONG).show();

            }
        }else{
            Toast.makeText(this,"El campo no debe estar vacio", Toast.LENGTH_LONG).show();
        }

    }

    private void bajaArticulo() {
        String codigo = etCodigo.getText().toString();
        if (codigo.length() > 0) {
            SQLiteDatabase db = adminDb.getWritableDatabase();
            String where = "codigo = ? "; //WHERE
            String[] objectArray = {codigo}; //WHERE VALUES
            int registrosBorrados = db.delete(
                    TABLE_NAME,
                    where,
                    objectArray
            );
            if (registrosBorrados>0){
                Toast.makeText(this, "Libro " + codigo + " eliminado de la base de datos ",
                        Toast.LENGTH_LONG).show();
                etCodigo.setText("");
            } else {
                Toast.makeText(this, "El libro " + codigo + "no existe en la base de datos ",
                        Toast.LENGTH_LONG).show();
            }


        } else
            Toast.makeText(this, "El campo no debe estar vacío ",
                    Toast.LENGTH_LONG).show();
    }

    private void buscarPorCodigo() {
        String codigo = etCodigo.getText().toString();
        if (codigo.length() > 0) {
            SQLiteDatabase db = adminDb.getWritableDatabase();
            String where = "codigo = ? "; //WHERE
            String[] objectArray = {codigo}; //WHERE VALUES
          Cursor cursor = db.query(
                    TABLE_NAME,
                    null,
                    where,
                    objectArray,
                    null,
                    null,
                    null
            );
            if (cursor.moveToNext()) {
                @SuppressLint("Range") String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                @SuppressLint("Range")
                String precio = etPrecio.getText().toString();
                etPrecio.setText(precio.toString());
                etDescripcion.setText(descripcion);
            } else {
                Toast.makeText(this, "Libro " + codigo + "No existe en la base de datos ",
                        Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(this, "El campo no debe estar vacío ",
                    Toast.LENGTH_LONG).show();
    }

    private void alta() {
        String codigo = etCodigo.getText().toString();
        String precio = etPrecio.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        SQLiteDatabase db = adminDb.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put ("codigo", codigo);
        content.put ("precio", precio);
        content.put ("descripcion", descripcion);
        db.insert(TABLE_NAME,null,content);
        Toast.makeText(this, "Libro Añadido" + codigo + "en la base de datos ",
        Toast.LENGTH_LONG).show();


    }
}