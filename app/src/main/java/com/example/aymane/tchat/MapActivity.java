package com.example.aymane.tchat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.aymane.tchat.shortestpath.Graph;
import com.example.aymane.tchat.shortestpath.Node;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.Color;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private TileCache tileCache;
    private TileRendererLayer tileRendererLayer;
    private Graph routeGraph;
    private ArrayList<Node> PathNodesList = new ArrayList<Node>();
    private String S;
    private String depart;
    private String arrivee;
    private String etage;
    private Spinner SP;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    @Override
    //instantiation de la map
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidGraphicFactory.createInstance(this.getApplication());
        setContentView(R.layout.activity_map);

        mapView= (MapView)findViewById(R.id.iutmap);

        list=new ArrayList<String>();
        SP=(Spinner) findViewById(R.id.S1);
        list.add("Etage5");
        list.add("Etage6");
        list.add("Etage7");
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_besoin, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP.setAdapter(adapter);
       /*  if(i.getStringExtra("Prov").equals("Iti"))
        {
            dep=i.getStringExtra("depart");
            dep=i.getStringExtra("arrivee");
            this.Dessiner();
        }*/

    }
    //
    //affichage de la map de l'étage 7
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        depart = intent.getStringExtra("depart");
        arrivee = intent.getStringExtra("arrivee");
        etage = intent.getStringExtra("etage");
        if(depart != null && arrivee != null && etage != null)
        {
            DrawPath(depart, arrivee, etage);
        }else{
            changementEtage("/smartcampus/iut-etage7.map");
        }
        SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                S = (String) parent.getItemAtPosition(position);
                Toast.makeText(MapActivity.this, S, Toast.LENGTH_SHORT).show();
                switch (S) {
                    case "Etage5":
                        changementEtage("/smartcampus/iut-etage5.map");
                        break;
                    case "Etage6":
                        changementEtage("/smartcampus/iut-etage6.map");
                        break;
                    case "Etage7":
                        changementEtage("/smartcampus/iut-etage7.map");
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onResume()
    {
        super.onResume();

    }
    public void onDestroy() {
        super.onDestroy();
        mapView.destroyAll();
    }
    //La partie charger du changement d'étage
    public void changementEtage(String CH)
    {
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.getMapZoomControls().setZoomLevelMin((byte) 17);
        mapView.getMapZoomControls().setZoomLevelMax((byte) 20);

        tileCache = AndroidUtil.createTileCache(this, "mapcache",
                mapView.getModel().displayModel.getTileSize(), 1f,
                mapView.getModel().frameBufferModel.getOverdrawFactor());
        mapView.getModel().mapViewPosition
                .setCenter(new LatLong(48.841751, 2.2684444));
        mapView.getModel().mapViewPosition.setZoomLevel((byte) 19);
        File file = new File(Environment.getExternalStorageDirectory(),CH);
       System.out.println(CH+file.toString());
        MapDataStore mapDataStore = new MapFile(file);
        tileRendererLayer= new TileRendererLayer(tileCache, mapDataStore, mapView.getModel().mapViewPosition, false, true, AndroidGraphicFactory.INSTANCE)
        {
            @Override
            public boolean onLongPress(LatLong tapLatLong,Point layerXY, Point tapXY)
            {
                Drawable drawable;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    drawable = getDrawable(R.drawable.marker);
                else
                    drawable = getResources().getDrawable(R.drawable.marker);

                Bitmap bitmap= AndroidGraphicFactory.convertToBitmap(drawable);
                bitmap.scaleTo(130, 130);

                Marker marker= new Marker(tapLatLong, bitmap, 0, -bitmap.getHeight() / 20)
                {

                    public boolean onTap(LatLong geoPoint,Point viewPosition,Point tapPoint)
                    {
                        if(contains(viewPosition, tapPoint)) {
                            Toast.makeText(MapActivity.this, "The Marker wastapped" + geoPoint.toString(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                };
                mapView.getLayerManager().getLayers().add(marker);
                return false;
            }
        };
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
        mapView.getLayerManager().getLayers().add(tileRendererLayer);
    }
    //déssiner les chemins
    public void DrawPath(String depart, String arrivee, String etage)
    {
        Paint paint = AndroidGraphicFactory.INSTANCE.createPaint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setStyle(Style.STROKE);

        Polyline polyline = new Polyline(paint,AndroidGraphicFactory.INSTANCE);

        List<LatLong> coordinateList = polyline.getLatLongs();
        switch(etage)
        {
            case "Etage5":
                changementEtage("/smartcampus/iut-etage5.map");
                break;
            case "Etage6":
                changementEtage("/smartcampus/iut-etage6.map");
                break;
            case "Etage7":
                changementEtage("/smartcampus/iut-etage7.map");
                break;
        }
        Node node1 = routeGraph.getNodeByName(depart);
        mapView.getModel().mapViewPosition.setCenter(node1.getlatLong());
        Node node2 = routeGraph.getNodeByName(arrivee);
        PathNodesList = routeGraph.shorterPath(node1, node2);
        for (Node node : PathNodesList)
        {
            coordinateList.add(node.getlatLong());
        }

        mapView.getLayerManager().getLayers().add(polyline);
    }

}
