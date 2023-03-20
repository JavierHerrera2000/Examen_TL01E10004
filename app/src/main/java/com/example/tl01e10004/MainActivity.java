package com.example.tl01e10004;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tl01e10004.configuracion.SQLiteConexion;
import com.example.tl01e10004.configuracion.Transacciones;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    ImageView Imagen;
    EditText nombrecontacto,numcontacto,notacontacto;
    Spinner spnlistapaises;
    String pa;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombrecontacto=(EditText)findViewById(R.id.txtnomcon);
        numcontacto=(EditText)findViewById(R.id.txtnumcon);
        notacontacto=(EditText)findViewById(R.id.txtnotcon);



        spnlistapaises=(Spinner) findViewById(R.id.spnpais);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.paises, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnlistapaises.setAdapter(adapter);
        Imagen=(ImageView) findViewById(R.id.ImagenContacto);
        Imagen.setImageResource(R.drawable.icono_persona);

    }

    public void AgregarContactos(View view)
    {
            if(nombrecontacto.getText().toString().isEmpty()){
                Toast.makeText(this,"No se ha escrito Ningun Nombre de Contacto", Toast.LENGTH_SHORT).show();
            }else{

                if(numcontacto.getText().toString().isEmpty()){
                    Toast.makeText(this,"No se ha escrito Ningun Numero", Toast.LENGTH_SHORT).show();

                }else {

                    try {
                        int posc;
                        posc=spnlistapaises.getSelectedItemPosition();
                        if(posc==0){
                            pa="504";
                        }
                        if(posc==1){
                            pa="506";
                        }if(posc==2){
                            pa="502";
                        }
                        if(posc==3){
                            pa="503";
                        }

                        Bitmap bitmap = ((BitmapDrawable) Imagen.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            SQLiteConexion conexion= new SQLiteConexion(this,
                                    Transacciones.NameDatabase,
                                    null,
                                    1);

                            SQLiteDatabase db= conexion.getWritableDatabase();
                            ContentValues valores = new ContentValues();
                            valores.put(Transacciones.Pais,pa);
                            valores.put(Transacciones.NombreContacto,nombrecontacto.getText().toString());
                            valores.put(Transacciones.NumeroContacto,numcontacto.getText().toString());
                            valores.put(Transacciones.NotaContacto,notacontacto.getText().toString());
                            valores.put(Transacciones.imagen,imageInByte);
                            Long resultado=db.insert(Transacciones.tablacontact,Transacciones.id,valores);
                            Toast.makeText(this,"Salvacion Exitosa", Toast.LENGTH_SHORT).show();
                            CleanPantalla();



                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }



    }

    private void CleanPantalla() {
        nombrecontacto.setText("");
        numcontacto.setText("");
        notacontacto.setText("");
        Imagen.setImageResource(R.drawable.icono_persona);
    }

    public void onclick(View view){
        cargarImagen ();
    }

    private void cargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("Image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            Imagen.setImageURI(path);
        }

    }
    public void Irsalvcon(View v) {
        Intent pagina = new Intent(getApplicationContext(),Activitylist.class);
        startActivity(pagina);
    }
}