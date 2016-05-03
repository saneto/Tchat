package com.example.aymane.tchat.Socket;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

/**
 * Created by aymane on 06/02/2016.
 */
public class Client extends Thread
{
    private Socket s= null;
    public static String MS12=null;
    private PrintWriter output;
    private BufferedReader input;
    private String st;
    private Date D;
    String MS1;

    public Client(final String ip, final int port)throws IOException
    {
        new Thread() {
            @Override

            public void run() {
                try {
                    s = new Socket(ip,  port);
                    output = new PrintWriter(s.getOutputStream(), true);
                    input = new BufferedReader(new InputStreamReader(s.getInputStream()));

                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void Close(boolean close)
    {
       if(!close)
       {
           output.println("E/");
           output.flush();
           output.close();
           try {
               input.close();
               s.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

    }
    public boolean IsConnect()
    {
        return  s.isConnected();
    }
    public boolean VerificationDuSocket()
    {
        if(s==null)
        {
            return false;
        }else
        {
            return true;
        }
    }
    public boolean Identification(String service,String id,String mdp) throws IOException
    {
        MS1=id+"@"+mdp+";$14000{B0-01}";
        output.println(MS1);
        output.flush();
        MS12="";
        new Thread() {
             @Override
            public void run() {
                try {
                   MS12 = input.readLine();
                    System.out.println(MS12 ) ;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return false;
    }
    public String GETMS12()
    {
        return MS12;
    }
    public void send(char Service,String Nom, String fonction)
    {
        switch(Service)
        {
            //Fermeture de la connexion
            case 'E':
                output.println(Service+"~"+Nom+":"+fonction);
                output.flush();
                this.close();
                break;
            case 'A':
                output.println(Service+"~"+Nom+":"+fonction);
                output.flush();
                break;
            case 'B':
                output.println(Service+"~"+Nom+":"+fonction);
                output.flush();
                break;
            case 'L':
                output.println(Service+"~");
                output.flush();
                break;
            case 'F':
                output.println(Service+"~"+Nom+":"+fonction);
                output.flush();
                break;
            case 'R':
                output.println(Service+"~"+Nom+":"+fonction);
                output.flush();
                break;
            case 'P':
                output.println(Service+"~"+Nom+"/"+fonction);
                output.flush();
                break;
        }
    }
    public void close()
    {
        output.close();
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String Read()
    {
        System.out.println("COucou Reand");
        new Thread() {
            @Override
            public void run() {
                String Test1;
                try {
                     Test1= input.readLine();
                    System.out.println(Test1);

                    if(Test1!=null)
                    {
                        MS12=Test1;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
            return MS12;
    }
    public void fermeture()
    {
        try {
            s.close();
        } catch (IOException e) {
        }
    }

}
