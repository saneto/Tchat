package com.example.aymane.tchat.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aymane on 06/02/2016.
 */
public class Serveur
{
    private ServerSocket socketserver;
    private Socket socker;
    private BufferedReader input;
    private PrintWriter output;
    String Msg;
    public Serveur(int port)
    {
        try {
            socketserver = new ServerSocket(port);
            socker = socketserver.accept();
            input = new BufferedReader(new InputStreamReader(socker.getInputStream()));
            output = new PrintWriter(socker.getOutputStream(), true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
   /* public Serveur(String Ad,int port,String service,)
    {
        try {
            socketserver = new ServerSocket(port);
            socker = socketserver.accept();
            input = new BufferedReader(new InputStreamReader(socker.getInputStream()));
            output = new PrintWriter(socker.getOutputStream(), true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/

    public String Read() {
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void Writte(String MSG) {
       output.println(MSG);
    }
    public void close()
    {

        try {
            socker.close();
            socketserver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
