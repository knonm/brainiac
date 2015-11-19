package com.brainiac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by Thiago Bonfiglio on 19/11/2015.
 */
public class HorarioActivity extends AppCompatActivity {

    private TimePicker hora = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        hora = (TimePicker) findViewById(R.id.timePicker);
        hora.is24HourView();
        hora.setEnabled(true);

        Button btnSalvar = (Button) findViewById(R.id.button10);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnCancelar = (Button) findViewById(R.id.button11);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnExcluir = (Button) findViewById(R.id.button9);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
