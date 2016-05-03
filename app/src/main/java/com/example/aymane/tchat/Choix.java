package com.example.aymane.tchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.aymane.tchat.persistance.persistance;

public class Choix extends AppCompatActivity {

    Intent i;
    String nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
    }
    //accés au contact
    public void Contact(View v)
    {
        Intent i=new Intent(getBaseContext(),ChoixNom.class);
        startActivity(i);
    }
    //accés au forum
    public void Forum(View v)
    {
        Intent i=new Intent(getBaseContext(),Forum.class);
        startActivity(i);
    }
   /* public void iti(View v)
    {
        Intent i=new Intent(getBaseContext(),TwoNodesActivity.class);
        startActivity(i);
    }*/
    //accés a la map
    public void map(View v)
    {
        Intent i=new Intent(getBaseContext(),MapActivity.class);
        //i.putExtra("Prov", "Choix");
        startActivity(i);
    }
    //methode de déconnexion
    public void deconnexion(View v)
    {
        ((persistance)this.getApplication()).send('E',"Fin");
        this.finish();
    }
}
