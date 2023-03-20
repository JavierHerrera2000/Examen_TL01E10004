package com.example.tl01e10004;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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
    ArrayList<Contactos>listaoriginal;
    ArrayList<String> ArregloContactos;
    ArrayAdapter adp;

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

          adp= new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloContactos);
            listacontactos.setAdapter(adp);
            listaoriginal.addAll(lista);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        listacontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String contselect= (String)listacontactos.getItemAtPosition(position);
                final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(Activitylist.this);
                alertOpciones.setTitle("Accion")
                        .setMessage("Desea llamar a "+contselect)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent pagina = new Intent(getApplicationContext(),Activityllamada.class);
                        pagina.putExtra("Nnllamar",lista.get(position).getNombreContacto());
                        pagina.putExtra("Numllamar","+"+lista.get(position).getPais()+lista.get(position).getNumeroContacto());
                        startActivity(pagina);
                        Toast.makeText(getApplication(),"Procede a llamar",Toast.LENGTH_SHORT).show();
                    }

                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertOpciones.create();
                alertOpciones.show();
            }

        });
        txtBuscar.setOnQueryTextListener(this);
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

    }


    private void filllist() {
        ArregloContactos=new ArrayList<String>();
        for(int i=0;i<lista.size();i++){
            ArregloContactos.add(lista.get(i).getNombreContacto()+" | "+
                    lista.get(i).getNumeroContacto()+" | "+lista.get(i).getNotaContacto()+" | ");

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        try {
            adp.getFilter().filter(newText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }




    public void Iringcon(View v) {
        Intent pagina = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(pagina);
    }
    public void Compcon(View v) {
        try {
            final CharSequence[] opciones =ArregloContactos.toArray(new String[0]);
            final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(Activitylist.this);
            alertOpciones.setTitle("Accion");
            alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent compartir = new Intent();
                    compartir.setAction(Intent.ACTION_SEND);
                    compartir.setType("text/pain");
                    compartir.putExtra(Intent.EXTRA_SUBJECT,"Contacto de "+lista.get(which).getNombreContacto());
                    compartir.putExtra(Intent.EXTRA_TEXT,lista.get(which).getNombreContacto()+lista.get(which).getNumeroContacto());
                    Intent share=Intent.createChooser(compartir,null);
                    startActivity(share);
                }
            });
            alertOpciones.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
   public void Verim(View v) {
       try {
           final CharSequence[] opciones =ArregloContactos.toArray(new String[0]);
           final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(Activitylist.this);
           alertOpciones.setTitle("Accion");
           alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   Intent i= new Intent(getApplicationContext(),ActivityVerimg.class);
                   i.putExtra("Nimg",lista.get(which).getNombreContacto().toString());
                   i.putExtra("Pimg",which);
                   startActivity(i);
               }
           });
           alertOpciones.show();
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
    }
    public void Actc(View v) {
        try {
            final CharSequence[] opciones =ArregloContactos.toArray(new String[0]);
            final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(Activitylist.this);
            alertOpciones.setTitle("Accion");
            alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i= new Intent(getApplicationContext(),ActivityActcon.class);
                    i.putExtra("AcId",which);
                    startActivity(i);
                }
            });
            alertOpciones.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void Elmc(View v) {
        try {
            final CharSequence[] opciones =ArregloContactos.toArray(new String[0]);
            final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(Activitylist.this);
            alertOpciones.setTitle("Accion");
            alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    final AlertDialog.Builder alertelm=new AlertDialog.Builder(Activitylist.this);
                alertelm.setTitle("ELIMINACION DE CONTACTO")
                        .setMessage("Desea Eliminar a"+lista.get(i).getNombreContacto())
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db =conexion.getWritableDatabase();
                                Contactos contactos=null;
                                try {
                        db.execSQL("DELETE FROM "+Transacciones.tablacontact+" WHERE "+Transacciones.id+" = '"+lista.get(i).getId()+"'")    ;
                                Toast.makeText(getApplication(),"Contacto Eliminado",Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                finally {
                                    db.close();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    alertelm.create();
                    alertelm.show();
                }
            });
            alertOpciones.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ObtenerlistaPersonas();

        ArrayAdapter adp= new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloContactos);
        listacontactos.setAdapter(adp);
    }
}