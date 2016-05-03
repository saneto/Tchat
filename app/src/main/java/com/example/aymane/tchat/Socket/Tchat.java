package com.example.aymane.tchat.Socket;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aymane.tchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zair on 08/02/2016.
 */
public class Tchat extends ArrayAdapter<String>
{
    private List<String> Str=new ArrayList<String>();
    private TextView TV3;

    public void add(String St)
    {
        Str.add(St);
        super.add(St);
    }
    public Tchat(Context context,int st)
    {
        super(context,st);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.textviewchat,parent, false);
        }


        String str= getItem(position);
        TV3=(TextView) convertView.findViewById(R.id.textView2);
        TV3.setText(str);
        TV3.setMovementMethod(new ScrollingMovementMethod() );
        return convertView;
    }

}
