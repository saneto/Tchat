package com.example.aymane.tchat;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.aymane.tchat.Socket.Tchat;
import com.example.aymane.tchat.persistance.persistance;


public class AffichageForum extends AppCompatActivity {

    private String MSG;
    private ArrayAdapter<String> adapter;
    private ListView lv;
    @Override
    //on récupére le contenu du forum et on affiche les 50 derniers message
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_forum);
        lv=(ListView)findViewById(R.id.listView6);

        adapter = new Tchat(getApplicationContext(),R.layout.textviewchat);
        lv.setAdapter(adapter);
        ((persistance)this.getApplication()).send('L', "Lecture Forum");
        ((persistance)this.getApplication()).Read();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MSG=((persistance) this.getApplication()).getMSG();
        System.out.println(MSG);
        int i=0;
        while(i!=MSG.length())
        {
            adapter.add(MSG.substring(i,MSG.indexOf("/",i)));
            adapter.notifyDataSetChanged();
            i=MSG.indexOf("/",i)+1;
        }
    }


    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();

    }
}
   /* void SendForum(View v) {
        this.finish();import android.content.Intent;import android.view.View;
    }*/
