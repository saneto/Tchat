package com.example.aymane.tchat.persistance;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.aymane.tchat.Socket.CLient2;
import com.example.aymane.tchat.Socket.Client;

import java.io.IOException;

/**
 * Created by aymane on 13/02/2016.
 */
public class persistance extends Application
{
    private String Nom;
    private String Mdp;
    private String Position;
    private boolean etat=false;
    private String Addr="192.168.0.38";
    private int port=12345;
    private Client C;
    public String getNom()
    {
        return Nom;
    }
    public String getMDP()
    {
        return Mdp;
    }
    public void StartPer() throws  IOException
    {

        C=new Client(Addr,port);
    }
    public void setPersonne(String Nom, String MDP)
    {
        this.Nom=Nom;
        this.Mdp=MDP;
        Position="B0-01";
    }

    public boolean ConnexionPRet()
    {
        return C.IsConnect();
    }
    public void send(String service,String id,String mdp)
    {
        try
        {
            etat=true;
            this.C.Identification(service,id,mdp);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void send(char service,String id)
    {
        C.send(service,Nom,id);

    }
    public void END(){
        C.Close(etat);
    }
    public String Read()
    {
        return C.Read();
    }
    public String getMSG()
    {
        return C.GETMS12();
    }
    public void setport(int port)
    {
        this.port=port;
    }
    public void setADDR(String Addr)
    {
       this.Addr=Addr;
    }
    public int getport()
    {
        return port;
    }
    public String getADDR()
    {
        return Addr;
    }
    public boolean VerifSo()
    {
        return C.VerificationDuSocket();
    }

}
