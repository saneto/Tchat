package com.example.aymane.tchat.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by aymane on 14/02/2016.
 */
public class CLient2 extends Thread
{
    private boolean send=false;
    private boolean instance=false;
    private boolean read=false;
    private boolean marqueur=false;
    private boolean finish=false;
    private PrintWriter output;
    private BufferedReader input;
    private String  MSG2;
    private Socket s= null;
    private String MSG;
   private String ip;
    private int port;
    public String getMSG2()
    {
        return MSG2;
    }
    public CLient2()
    {
        ip="192.168.0.38";
        port = 12345;
        this.send=false;
        this.instance=true;
        this.read=false;
        this.start();



    }
    public CLient2(String ip,int port)
    {
        this.ip=ip;
        this.port = port;
        this.send=false;
        this.instance=true;
        this.read=false;
    }
    @Override
    public void run()
    {

        finish=false;
        marqueur = false;
        if(instance)
        {
            System.out.println("FIN DU THREA +5 " + this.getState()+" "+this.getPriority());
            try {
                s = new Socket(ip, port);
                output = new PrintWriter(s.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                marqueur = true;
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("FIN DU THREA + 7" + this.getState()+" "+this.getPriority());
        }else if(send)
        {
            System.out.println("SEND" + this.getState()+" "+this.getPriority());
            output.println(MSG);
            marqueur = true;
            finish=true;
        }else if(read)
        {
            try {
                MSG2 = input.readLine();
                System.out.println(MSG2 ) ;
                finish=true;
                this.interrupt();

        } catch (IOException e) {
            e.printStackTrace();
                this.interrupt();
        }
        }
        System.out.println("FIN DU THREA + 9" + this.getState()+" "+this.getPriority());
    }
    public void SEND(String send)
    {
        System.out.println("SEND" + this.getState()+" "+this.getPriority());
        MSG=send;
        this.send=true;
        this.instance=false;
        this.read=false;
        this.run();
    }
    public void Read()
    {
        System.out.println("READ " + this.getState()+" "+this.getPriority());
        this.send=false;
        this.instance=false;
        this.read=true;
        this.run();
    }
    public boolean isMarqueur() {
        return marqueur;
    }

    public void setMarqueur(boolean marqueur) {
        this.marqueur = marqueur;
    }
}
