package com.example.aymane.tchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aymane.tchat.persistance.persistance;


public class Forum extends AppCompatActivity
{

    private Intent i;
    int p;
    String add;
    EditText ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_forum);
        ET=(EditText)findViewById(R.id.editText2);


    }
    protected  void onStart()
    {
        super.onStart();

    }
    protected void onStop()
    {
        super.onStop();
    }

    protected void onResume()
    {
        super.onResume();
    }
    //Ajout des donnés au forum
    public void SendFo(View v)
    {
        if(((persistance)this.getApplication()).ConnexionPRet())
        {
            ((persistance)this.getApplication()).send('F', ET.getText().toString());
        }
        else{
            Toast.makeText(this, "Impossible de joindre le serveur", Toast.LENGTH_LONG).show();
            this.finish();
        }


    }
    //Affiche le contenu du forum
    public void ADF(View v)
    {
         i=new Intent(getBaseContext(),AffichageForum.class);
        startActivity(i);
    }
}
