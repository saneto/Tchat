package com.example.aymane.tchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.aymane.tchat.shortestpath.Graph;
import com.example.aymane.tchat.shortestpath.Node;

import java.util.ArrayList;


public class TwoNodesActivity extends ActionBarActivity {

	Spinner spinner1 ,spinner2,Etage;
	Graph routeGraph;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_nodes);

		spinner1 = (Spinner) findViewById(R.id.Sp1);
		spinner2 = (Spinner) findViewById(R.id.Sp2);
		Etage = (Spinner) findViewById(R.id.spinner);
		ArrayList<String> EtageList = new ArrayList<String>();
		EtageList.add("Etage 5");
		EtageList.add("Etage 6");
		EtageList.add("Etage 7");
		ArrayAdapter<String> EtageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, EtageList);
		EtageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Etage.setAdapter(EtageAdapter);
		Etage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				System.out.println("choix etage " + parent.getSelectedItem().toString());
				LoadDestinationsList(parent.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		LoadDestinationsList("Etage 5");
	}


	public void search(View v){

		if(spinner1.getSelectedItem()!= spinner2.getSelectedItem())
		{
			Intent intent = new Intent(getApplicationContext(), MapActivity.class);
			intent.putExtra("depart", spinner1.getSelectedItem().toString());
			intent.putExtra("arrivee", spinner2.getSelectedItem().toString());
			intent.putExtra("etage", Etage.getSelectedItem().toString());
			startActivity(intent);
		}else Toast.makeText(getApplicationContext(), "La salle de départ doit être différente de la salle d'arrivée !", Toast.LENGTH_LONG).show();

	}
	public void LoadDestinationsList(String Etage){
		System.out.println("choix etage " + Etage);
		switch(Etage) {
			case "Etage 5":
				LoadGraphFromFile("/smartcampus/route_etage5.osm");
				break;
			case "Etage 6":
				LoadGraphFromFile("/smartcampus/route_etage6.osm");
				break;
			case "Etage 7":
				LoadGraphFromFile("/smartcampus/route_etage7.osm");
				break;
		}
		ArrayList<String> list = new ArrayList<String>();
		for (Node node : routeGraph.Nodes) {
			System.out.println("choix point  " + node.name);
			if (node.name != "Standard node"){
				System.out.println("choix point  " + node.name);
				list.add(node.name);
			}
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner1.setAdapter(dataAdapter);
		spinner2.setAdapter(dataAdapter);
	}
	public void LoadGraphFromFile(String File){
		routeGraph = Graph.getGraphFromOsm(Environment.getExternalStorageDirectory() + File);
	}

}