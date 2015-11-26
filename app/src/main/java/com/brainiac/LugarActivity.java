package com.brainiac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.brainiac.model.EventoLugar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LugarActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String LUGAR_KEY = "LUGAR_KEY";
    public static final int EDITAR = 1;
    public static final int INCLUIR = 2;

    public static final int FLAG_ACTIVITY = 10;
    public static final int RESULT_CODE_SALVAR = 1;
    public static final int RESULT_CODE_CANCELAR = 2;
    public static final int RESULT_CODE_EXCLUIR = 3;

    private EventoLugar eventoLugar;

    private MapFragment mapFragment;
    private Marker marker;
    private EditText edNomeLugar;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMap().setMyLocationEnabled(true);

        edNomeLugar = (EditText) findViewById(R.id.editText4);

        if(getIntent().getFlags() == EDITAR) {
            eventoLugar = getIntent().getParcelableExtra(LUGAR_KEY);
            edNomeLugar.setText(eventoLugar.getNomeLugar());
        } else if (getIntent().getFlags() == INCLUIR) {
            eventoLugar = new EventoLugar();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng userLocation = new LatLng(eventoLugar.getLatitude(), eventoLugar.getLongitude());
                if (marker != null) {
                    marker.remove();
                }
                marker = mapFragment.getMap().addMarker(new MarkerOptions().draggable(false).position(userLocation));
            }
        });

        mapFragment.getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null) {
                    marker.remove();
                }
                marker = mapFragment.getMap().addMarker(new MarkerOptions().draggable(false).position(latLng));
            }
        });

        Button btnSalvar = (Button) findViewById(R.id.button6);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOk = true;

                try {
                    validarCampos();
                } catch (LugarActivityValidaException e) {
                    isOk = false;

                    AlertDialog.Builder builder = new AlertDialog.Builder(LugarActivity.this);
                    builder.setMessage(e.getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

                if(isOk) {
                    eventoLugar.setNomeLugar(edNomeLugar.getText().toString());
                    eventoLugar.setLatitude((float) marker.getPosition().latitude);
                    eventoLugar.setLongitude((float) marker.getPosition().longitude);

                    Intent it = new Intent(LugarActivity.this, AlarmeActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(LugarActivity.LUGAR_KEY, eventoLugar);

                    it.putExtras(bundle);
                    it.setFlags(LugarActivity.RESULT_CODE_SALVAR);

                    LugarActivity.this.setResult(LugarActivity.FLAG_ACTIVITY, it);

                    LugarActivity.this.finish();
                }
            }
        });

        Button btnCancelar = (Button) findViewById(R.id.button7);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LugarActivity.this, AlarmeActivity.class);
                it.setFlags(LugarActivity.RESULT_CODE_CANCELAR);
                LugarActivity.this.setResult(FLAG_ACTIVITY, it);
                LugarActivity.this.finish();
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (getIntent().getFlags() == LugarActivity.INCLUIR) {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
            if (mLastLocation != null) {
                eventoLugar.setLatitude((float) mLastLocation.getLatitude());
                eventoLugar.setLongitude((float) mLastLocation.getLongitude());
            }
            if(marker != null) {
                marker.remove();
            }
            marker = mapFragment.getMap().addMarker(new MarkerOptions().draggable(false).position(new LatLng(eventoLugar.getLatitude(), eventoLugar.getLongitude())));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void validarCampos() throws LugarActivityValidaException {
        if(this.edNomeLugar.getText() == null || this.edNomeLugar.getText().length() <= 0) {
            throw new LugarActivityValidaException("Preencha o nome do lugar escolhido.");
        }
    }

    private class LugarActivityValidaException extends Exception {

        public LugarActivityValidaException(String msg) {
            super(msg);
        }
    }
}
