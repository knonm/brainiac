package com.brainiac;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brainiac.model.EventoLugar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LugarActivity extends AppCompatActivity {

    public static final String LUGAR_KEY = "LUGAR_KEY";
    public static final int EDITAR = 1;
    public static final int INCLUIR = 2;

    private EventoLugar eventoLugar;

    private MapFragment mapFragment;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMap().setMyLocationEnabled(true);

        if(getIntent().getFlags() == EDITAR) {
            eventoLugar = getIntent().getParcelableExtra(LUGAR_KEY);

            EditText edNomeLugar = (EditText) findViewById(R.id.editText4);
            edNomeLugar.setText(eventoLugar.getNomeLugar());

            marker = mapFragment.getMap().addMarker(new MarkerOptions().draggable(false).position(new LatLng(eventoLugar.getLatitude(), eventoLugar.getLongitude())));
        } else if (getIntent().getFlags() == INCLUIR) {
            eventoLugar = new EventoLugar();
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

            }
        });

        mapFragment.getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }
                marker = mapFragment.getMap().addMarker(new MarkerOptions().position(latLng).title("Local").draggable(false));
            }
        });

        Button btnSalvar = (Button) findViewById(R.id.button6);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnCancelar = (Button) findViewById(R.id.button7);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnExcluir = (Button) findViewById(R.id.button8);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //mapFragment.
    }
}
