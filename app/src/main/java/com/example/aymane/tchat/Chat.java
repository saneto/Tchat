package com.example.aymane.tchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.aymane.tchat.Socket.Tchat;
import com.example.aymane.tchat.persistance.persistance;


public class Chat extends AppCompatActivity {

    private Intent I;
    private EditText ET;
    private String Nom;
    private ArrayAdapter<String> adapter;
    private ListView lv;
    private String ListMSG;
    @Override
    //dans un 1er temps on lit les messages stocké dans le serveur puis on les affiches
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        I = getIntent();
        Nom = I.getStringExtra("Nom");
        lv = (ListView) findViewById(R.id.listView);
        adapter = new Tchat(getApplicationContext(),R.layout.textviewchat);
        lv.setAdapter(adapter);
        ET = (EditText) findViewById(R.id.editText);
        ((persistance)this.getApplication()).send('B', Nom);
        ((persistance) this.getApplication()).Read();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListMSG=((persistance)this.getApplication()).getMSG();
        System.out.println(ListMSG.length());
        int i=0;
        while(i!= ListMSG.length())
        {
            adapter.add( ListMSG.substring(i, ListMSG.indexOf("/",i)));
            adapter.notifyDataSetChanged();
            i= ListMSG.indexOf("/",i)+1;
        }
    }
    //envoi les messages au serveur et récupere les messages stocké
    public void SendChat(View v)
    {
        String Str=ET.getText().toString();
        ((persistance)this.getApplication()).send('A',Nom+"$"+Str);
        ((persistance)this.getApplication()).send('B',Nom);
        ((persistance) this.getApplication()).Read();
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListMSG=((persistance)this.getApplication()).getMSG();
        int i=0;
        while(i!= ListMSG.length())
        {
            adapter.add( ListMSG.substring(i, ListMSG.indexOf("/",i)));
            adapter.notifyDataSetChanged();
            i= ListMSG.indexOf("/",i)+1;
        }
    }

    protected void onResume() {
        super.onResume();


    }
    protected void onStart()
    {
        super.onStart();

    }
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }
}
// private Serveur SRV2;
// private Thread serverThread = null;
// private ServerSocket serverSocket;
// public static final int PORT = 14000;
// private int Port;
//private Socket socket = null;
//private Handler updateConversationHandler;
//private PrintWriter ou;
//private Socket SSS;
//  private String AdIp;
/* public class Serveur implements Runnable
    {
        private Socket clientSocket;
        private BufferedReader input;
        private boolean Running=true;
        public Serveur(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
            try {
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run()
        {
            while (Running) {
                try {
                    String read = input.readLine();
                    if(this.clientSocket.isConnected())
                    {
                        updateConversationHandler.post(new updateUIThread(read));
                    }else
                    {
                        Running=false;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close() {

            try {
                Running=false;
                input.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        class updateUIThread implements Runnable {
            private String msg;

            public updateUIThread(String str) {

                this.msg = str;
            }

            @Override
            public void run()
            {
                adapter.add(msg);
                adapter.notifyDataSetChanged();
            }

        }
    }*/
  /*try {
            ou.close();
            SSS.close();
            SRV2.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
 /*  try {
            if(ou!=null)
            {
                ou.close();
            }
            if(SSS!=null)
            {

                SSS.close();
            }
            if(SRV2!=null)
            {
                SRV2.close();
            }
            if(socket!=null)
            {
                socket.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        /*ou.println(((persistance)this.getApplication()).getNom()+":"+ET.getText().toString());
        ou.flush();
        if(ET.getText().toString().equals("END"))
        {
            try {
                if(ou!=null)
                {
                    ou.close();
                }
                if(SSS!=null)
                {

                    SSS.close();
                }
                if(SRV2!=null)
                {
                    SRV2.close();
                }
                if(socket!=null)
                {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
//updateConversationHandler = new Handler();
        /* this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();*/
     /*   new Thread() {
            @Override

            public void run()
            {
                try {
                    SSS=new Socket(AdIp,Port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(SSS!= null)
        {
            try {
                ou=new PrintWriter(SSS.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread() {
                @Override

                public void run()
                {

                    try {
                        serverSocket = new ServerSocket(PORT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            if(serverSocket!=null)
                            {
                                socket = serverSocket.accept();
                                SRV2 = new Serveur(socket);
                                new Thread(SRV2).start();
                            }else
                            {
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.start();

        }else
        {
             SSS=null;

            Chat.this.finish();
        }
        try {
            Thread.sleep(200l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
import android.graphics.Color;
import android.widget.Toast;
import com.example.aymane.tchat.persistance.persistance;
import android.os.Handler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
        */
        /*Nom = I.getStringExtra("Nom");
        System.out.println(Nom);
        AdIp = I.getStringExtra("Adrr");
        System.out.println(AdIp);
        String r=I.getStringExtra("port");
        System.out.println(r);
        Port=Integer.valueOf(r);
        this.setTitle(Nom);*/