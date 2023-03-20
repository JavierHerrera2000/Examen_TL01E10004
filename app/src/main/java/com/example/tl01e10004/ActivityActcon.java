package com.example.tl01e10004;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tl01e10004.configuracion.SQLiteConexion;
import com.example.tl01e10004.configuracion.Transacciones;
import com.example.tl01e10004.tablas.Contactos;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ActivityActcon extends AppCompatActivity {
    SQLiteConexion conexion;
    ListView listacontactos;
    ArrayList<Contactos> lista;
    ArrayList<String> ArregloContactos;
    ImageView Imgac;
    EditText nomact,numact,notact,idac;

    int  posicion;
    String Campoid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actcon);
        posicion=getIntent().getExtras().getInt("AcId");
        conexion=new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        try {
            ObtenerlistaPersonas();

            ArrayAdapter adp= new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloContactos);
            listacontactos.setAdapter(adp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Campoid=String.valueOf(lista.get(posicion).getId());
        Imgac=(ImageView)findViewById(R.id.imgac);
        nomact=(EditText)findViewById(R.id.txtacnom);
        numact=(EditText)findViewById(R.id.txtacnumcon);
        notact=(EditText)findViewById(R.id.txtacnotcon);
        nomact.setText(lista.get(posicion).getNombreContacto());
        numact.setText(lista.get(posicion).getNumeroContacto());
        notact.setText(lista.get(posicion).getNotaContacto());
        Bitmap bitmap= BitmapFactory.decodeByteArray(lista.get(posicion).getImagen(), 0, lista.get(posicion).getImagen().length);
        Imgac.setImageBitmap(bitmap);

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
        db.close();
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
    public void Accon(View v) {
        if(nomact.getText().toString().isEmpty()){
            Toast.makeText(this,"No se ha escrito Ningun Nombre de Contacto", Toast.LENGTH_SHORT).show();
        }else{
            if(numact.getText().toString().isEmpty()){
                Toast.makeText(this,"No se ha escrito Ningun Numero", Toast.LENGTH_SHORT).show();

            }else {
                Bitmap bitmap = ((BitmapDrawable) Imgac.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                SQLiteDatabase db =conexion.getWritableDatabase();
                Contactos contactos=null;
                try {
                   // db.execSQL("UPDATE "+Transacciones.tablacontact+" SET "+Transacciones.NombreContacto+" = '"+nomact.getText()+"', "+Transacciones.NumeroContacto+" = '"+numact.getText()+"', "+Transacciones.NotaContacto+" = '"+notact.getText()+"', "+Transacciones.imagen+" = '"+imageInByte+"' WHERE "+Transacciones.id+" = '"+lista.get(posicion).getId()+"'");
                    //Toast.makeText(getApplication(),"Contacto Actualizado",Toast.LENGTH_SHORT).show();
                    String[] parametros={Campoid};
                    ContentValues valores = new ContentValues();
                    valores.put(Transacciones.NombreContacto,nomact.getText().toString());
                    valores.put(Transacciones.NumeroContacto,numact.getText().toString());
                    valores.put(Transacciones.NotaContacto,notact.getText().toString());
                    valores.put(Transacciones.imagen,imageInByte);
                    db.update(Transacciones.tablacontact,valores,Transacciones.id+"=?",parametros);
                    Toast.makeText(this,"Actualizacion Exitosa", Toast.LENGTH_SHORT).show();
                    Intent pagina = new Intent(getApplicationContext(),Activitylist.class);
                    startActivity(pagina);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                finally {
                    db.close();
                }
            }
        }

    }
    public void Actimg(View v) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("Image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            Imgac.setImageURI(path);
        }

    }
}