package com.example.aymane.tchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aymane.tchat.persistance.persistance;

import java.util.ArrayList;
//partie des selections et ajout des contacts
public class ChoixNom extends AppCompatActivity {

    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private String S;
    private String MSG;
    private EditText ET;
    private Button B1;
    private Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_nom);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        list=new ArrayList<String>();
        spinner=(Spinner) findViewById(R.id.spinner);
        list.add("Charles");
        list.add("Stiven");
        list.add("Vincen");
        list.add("André");
        list.add("Aymane");
        adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item_besoin, list);
        spinner.setAdapter(adapter);
        ET=(EditText)findViewById(R.id.editText6);
        B1=(Button)findViewById(R.id.button2);
        ET.setVisibility(View.GONE);
        B1.setVisibility(View.GONE);

    }
    protected void onResume()
    {

        super.onResume();
        //
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                S = (String) parent.getItemAtPosition(position);
                Toast.makeText(ChoixNom.this, S, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //methode de l'événement charger d'ouvrir le chat
    public void Valider(View v)
    {
        if(((persistance)this.getApplication()).ConnexionPRet())
        {
            //vérification de la présence de la personne
            ((persistance)this.getApplication()).send('R', S);
            //lecture de la réponse du serveur
            ((persistance) this.getApplication()).Read();
            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //recupération des donnés envoyer par le serveur
            MSG=((persistance)this.getApplication()).getMSG();
            //vérification des résultats envoyer par le serveur
            if(MSG!=null)
            {
                //si le message est egale a Pr alors on passe a la phase suivante sinon on reste et on affiche un message
                if(MSG.equals("Pr"))
                {
                    in=new Intent(this,Chat.class);
                    in.putExtra("Nom", S);
                    startActivity(in);
                }else
                {
                    Toast.makeText(this,"Aucun étudiant du nom de "+S+" n'est présent sur la liste",Toast.LENGTH_SHORT).show();
                }
            }else
            {
                Toast.makeText(this,"Un probléme est survenue veuilez recommencer",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Impossible de joindre le serveur", Toast.LENGTH_LONG).show();
        }


    }
    //l'ajout des contats a la liste
    public void AjouterContact(View v)
    {
        ET.setVisibility(View.VISIBLE);
        B1.setVisibility(View.VISIBLE);

    }
    //Activer les champs pour l'ajout des contacts
    public void Valider2(View v)
    {
        if(!ET.getText().toString().isEmpty())
        {
            adapter.add(ET.getText().toString());
            adapter.notifyDataSetChanged();
            ET.setVisibility(View.GONE);
            B1.setVisibility(View.GONE);
        }else
        {
            Toast.makeText(this,"Veuillez remplir le champ",Toast.LENGTH_LONG);
        }

    }
}
 /*if(MSG.contains("/"))
            {
                System.out.println(MSG+"lpl 2");
                if(MSG.substring(0,MSG.indexOf("/")).equals("Connecter"))
                {
                /System.out.println(MSG);

            }else if(MSG.substring(0,MSG.indexOf("/")).equals("Deconnecter"))
            {
                Toast.makeText(this,"L'étudiant que vous essayez de joindre est déconnecté pour le moment",Toast.LENGTH_SHORT).show();
            }else if(MSG.substring(0,MSG.indexOf("/")).equals("Impossible"))
            {
                Toast.makeText(this,"L'étudiant que vous essayez de joindre n'existe pas",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Erreur de format",Toast.LENGTH_SHORT).show();
            }
            }else{
                Toast.makeText(this,"Un probléme est survenue veuilez recommencer",Toast.LENGTH_SHORT).show();
            }*/
      /*  if(!((persistance)this.getApplication()).isEtat())
        {
            try {
                ((persistance)this.getApplication()).send("toto", ((persistance)this.getApplication()).getNom(), ((persistance)this.getApplication()).getMDP());
                Thread.sleep(200l);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
 /*  @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        S=(String)parent.getItemAtPosition(position);
        Toast.makeText(this,S,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        S="Test";
    }*/
/*implements AdapterView.OnItemSelectedListener */