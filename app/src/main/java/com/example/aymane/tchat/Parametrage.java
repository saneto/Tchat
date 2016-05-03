package com.example.aymane.tchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aymane.tchat.persistance.persistance;

public class Parametrage extends AppCompatActivity {

    private EditText ET1;
    private boolean changement=false;
    private EditText ET2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametrage);
        ET1=(EditText)findViewById(R.id.editText4);
        ET2=(EditText)findViewById(R.id.editText5);
    }

    public void Sauvegarder(View v)
    {
        if(!ET1.getText().toString().isEmpty())
        {
            ((persistance)this.getApplication()).setADDR(ET1.getText().toString());
            changement=true;
        }
        if(!ET2.getText().toString().isEmpty())
        {
            changement=true;
            ((persistance)this.getApplication()).setport(Integer.valueOf(ET2.getText().toString()));
        }
        if(changement)
        {
            this.finish();
        }else
        {
            Toast.makeText(this,"Veuillez remplir l'un des 2 champs",Toast.LENGTH_SHORT);
        }
    }
}
