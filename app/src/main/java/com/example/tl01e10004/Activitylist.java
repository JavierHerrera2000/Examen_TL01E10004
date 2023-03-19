package com.example.tl01e10004;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tl01e10004.configuracion.SQLiteConexion;
import com.example.tl01e10004.configuracion.Transacciones;
import com.example.tl01e10004.tablas.Contactos;

import java.util.ArrayList;

public class Activitylist extends AppCompatActivity implements  SearchView.OnQueryTextListener {
    Button btncompcont,btnelimcont,btnverimg,btnactcont;
    SearchView txtBuscar;
    SQLiteConexion conexion;
    ListView listacontactos;
    ArrayList<Contactos> lista;
    ArrayList<String> ArregloContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitylist);
        conexion=new SQLiteConexion(this,Transacciones.NameDatabase,null,1);
        listacontactos=(ListView) findViewById(R.id.listacontactos);
        txtBuscar=(SearchView)findViewById(R.id.txtbuscar);
        btnactcont=(Button) findViewById(R.id.btnactcon);
        btncompcont=(Button) findViewById(R.id.btncompcon);
        btnelimcont=(Button) findViewById(R.id.btneliminarcont);
        btnverimg=(Button) findViewById(R.id.btnverimagen);
        try {
            ObtenerlistaPersonas();

            ArrayAdapter adp= new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloContactos);
            listacontactos.setAdapter(adp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        listacontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contselect= (String)listacontactos.getItemAtPosition(position);
                final CharSequence[] opciones ={"NO","sI"};
                final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(Activitylist.this);
                alertOpciones.setTitle("Accion");
                alertOpciones.setMessage("Desea llamar a "+contselect);
                alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }

        });
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
            ArregloContactos.add(lista.get(i).getId()+" | "+
                    lista.get(i).getNombreContacto()+" | "+
                    lista.get(i).getNumeroContacto()+" | "+
                    lista.get(i).getId()+" | ");

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}