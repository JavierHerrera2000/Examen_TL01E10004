package com.example.tl01e10004;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tl01e10004.configuracion.SQLiteConexion;
import com.example.tl01e10004.configuracion.Transacciones;
import com.example.tl01e10004.tablas.Contactos;

import java.util.ArrayList;

public class ActivityVerimg extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listacontactos;
    ArrayList<Contactos> lista;
    ArrayList<String> ArregloContactos;
    ImageView Img;
    TextView Nimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verimg);
        conexion=new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        try {
            ObtenerlistaPersonas();

            ArrayAdapter adp= new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloContactos);
            listacontactos.setAdapter(adp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int  posicion=getIntent().getExtras().getInt("Pimg");
        String nombcon=getIntent().getExtras().getString("Nimg");
        Img=(ImageView) findViewById(R.id.imgv);
        Nimg=(TextView)findViewById(R.id.txtnombcon);
        Nimg.setText(nombcon);
        Bitmap bitmap= BitmapFactory.decodeByteArray(lista.get(posicion).getImagen(), 0, lista.get(posicion).getImagen().length);
        Img.setImageBitmap(bitmap);

    }
    private void ObtenerlistaPersonas()
    {
        SQLiteDatabase db =conexion.getReadableDatabase();
        Contactos contactos=null;
        lista= new ArrayList<Contactos>();

        Cursor cursor= db.rawQuery("Select * FROM "+Transacciones.tablacontact,null);
        while (cursor.moveToNext()){
            contactos= new Contactos();
            contactos.setId(cursor.getInt(0));
            contactos.setPais(cursor.getString(1));
            contactos.setNombreContacto(cursor.getString(2));
            contactos.setNumeroContacto(cursor.getString(3));
            contactos.setNotaContacto(cursor.getString(4));
            contactos.setImagen(cursor.getBlob(5));

            lista.add(contactos);

        }
        cursor.close();
        filllist();
        //  txtBuscar.setOnQueryTextListener(this);
    }
    private void filllist() {
        ArregloContactos=new ArrayList<String>();
        for(int i=0;i<lista.size();i++){
            ArregloContactos.add(lista.get(i).getNombreContacto()+" | "+
                    lista.get(i).getNumeroContacto()+" | ");

        }
    }
    public void Irliscon(View v) {
        Intent pagina = new Intent(getApplicationContext(),Activitylist.class);
        startActivity(pagina);
    }
}