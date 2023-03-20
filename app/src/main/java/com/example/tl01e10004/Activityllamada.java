package com.example.tl01e10004;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activityllamada extends AppCompatActivity {
TextView Nomblla,Numlla;
    String nbrll,nmll;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityllamada);
    Nomblla=(TextView) findViewById(R.id.txtnomll);
    Numlla=(TextView) findViewById(R.id.txtnumll);
        nbrll=getIntent().getExtras().getString("Nnllamar");
        nmll=getIntent().getExtras().getString("Numllamar");
        Nomblla.setText(nbrll);
        Numlla.setText(nmll);
    }
    public void Irliscon(View v) {
        Intent pagina = new Intent(getApplicationContext(),Activitylist.class);
        startActivity(pagina);
    }
    public void llamar(View v) {

        Intent llamada = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Numlla.getText().toString()));
        startActivity(llamada);

    }
}