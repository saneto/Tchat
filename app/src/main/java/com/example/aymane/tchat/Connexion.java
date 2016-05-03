package com.example.aymane.tchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aymane.tchat.persistance.persistance;

import java.io.IOException;
import java.net.Socket;

public class Connexion extends Activity {

    private Intent intent;
    private EditText pseudo;
    private EditText Mdp;
    private Button CH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        CH=(Button)findViewById(R.id.Chat);
        CH.setEnabled(false);


    }
    public void onResume()
    {
        super.onResume();
        CH.setEnabled(false);
    }
    //La methode de l'événement onClick charger de la connexion avec le serveur et vérifier aussi sa présence et
    // s'occupe d'activer le bouton de l'événement de connexion de l'application
    public void Forum(View v)
    {

        try {
            //permet de se connecter au serveur
            ((persistance)this.getApplication()).StartPer();
            //met en pause le thread pricipale
            Thread.sleep(200l);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //la vérification de l'instantiation du socket
        if(((persistance)this.getApplication()).VerifSo())
        {
            //vérifier si la conenxion a eu lieu
            if(((persistance)this.getApplication()).ConnexionPRet())
            {
                Toast.makeText(this, "Serveur trouvé", Toast.LENGTH_LONG).show();
                //active le bouton connexion de l'application
                CH.setEnabled(true);
            }
            else{
                Toast.makeText(this, "Impossible de joindre le serveur", Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(this, "Une anomalie a eu lieu veuillez vérifier votre connexion ou le serveur", Toast.LENGTH_LONG).show();
        }


    }
    protected void onDestroy()
    {
        super.onDestroy();
        ((persistance)this.getApplication()).END();

    }
    //permet de joindre l'écran de paramétrage réseau
    public void PARAM(View v)
    {
        intent = new Intent(Connexion.this,Parametrage.class);
        startActivity(intent);
    }
    //methode de l'événement qui permet d'accéder au fonctionnalité de l'application
    public void Chat(View v)
    {
        if(((persistance)this.getApplication()).ConnexionPRet())
        {
            pseudo=(EditText) findViewById(R.id.editText);
            Mdp=(EditText) findViewById(R.id.editText2);
            //on vérifier si les champs identifait et mot de passe on était bien saisi
            if(pseudo.getText().toString().isEmpty()||Mdp.getText().toString().isEmpty())
            {
                Toast.makeText(Connexion.this, "Champ Pseudo ou mdp vide",Toast.LENGTH_SHORT).show();
                return;
            }else
            {

                try {
                    //on envoi au requete au serveur pour confirmer la connexion
                    ((persistance)this.getApplication()).send("toto", pseudo.getText().toString(), Mdp.getText().toString());
                    Thread.sleep(200l);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String MRETS= ((persistance)this.getApplication()).getMSG();

                if( MRETS.equals("OK"))
                {
                    Toast.makeText(Connexion.this, "Bienvenue à l'iut PARISDESCARTES",Toast.LENGTH_SHORT).show();
                    intent = new Intent(Connexion.this,Choix.class);
                    //modification des valeurs des variable locale
                    ((persistance)this.getApplication()).setPersonne(pseudo.getText().toString(), Mdp.getText().toString());
                    startActivity(intent);
                }else
                {
                    Toast.makeText(Connexion.this,"Identifiant ou Mdp erroner",Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            CH.setEnabled(false);
            Toast.makeText(this, "Connexion perdu", Toast.LENGTH_LONG).show();
        }
    }

}
