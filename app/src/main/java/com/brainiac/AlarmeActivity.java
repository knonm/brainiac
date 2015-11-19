package com.brainiac;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brainiac.model.Alarme;
import com.brainiac.model.Evento;
import com.brainiac.model.EventoHorario;
import com.brainiac.model.EventoLugar;
import com.brainiac.model.dao.AlarmeDAO;
import com.brainiac.model.sqlite.BrainiacDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmeActivity extends AppCompatActivity {

    public static final String ALARME_KEY = "ALARME_KEY";
    public static final int EDITAR = 1;
    public static final int INCLUIR = 2;

    private SQLiteOpenHelper dbHelper;

    private AlarmeDAO alarmeDAO;

    private Alarme alarme;
    private EventoHorario eventoHorario;
    private EventoLugar eventoLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getFlags() == EDITAR) {
            alarme = getIntent().getParcelableExtra(ALARME_KEY);
            eventoLugar = alarme.getEvento().getEventoLugar();
            eventoHorario = alarme.getEvento().getEventoHorario();

            EditText edTitulo = (EditText) findViewById(R.id.editText);
            CheckBox chkDom = (CheckBox) findViewById(R.id.checkBox);
            CheckBox chkSeg = (CheckBox) findViewById(R.id.checkBox2);
            CheckBox chkTer = (CheckBox) findViewById(R.id.checkBox3);
            CheckBox chkQua = (CheckBox) findViewById(R.id.checkBox4);
            CheckBox chkQui = (CheckBox) findViewById(R.id.checkBox5);
            CheckBox chkSex = (CheckBox) findViewById(R.id.checkBox6);
            CheckBox chkSab = (CheckBox) findViewById(R.id.checkBox7);
            EditText edLugar = (EditText) findViewById(R.id.editText2);
            EditText edHorario = (EditText) findViewById(R.id.editText3);

            edTitulo.setText(alarme.getTitulo());

            if(eventoLugar != null) {
                edLugar.setText(eventoLugar.getNomeLugar());
            }

            if(eventoHorario != null) {
                DateFormat df;

                if(eventoHorario.getData_evento() != null) {
                    df = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
                    //edHorario.setText(df.format(eventoHorario.getData_evento()));
                }

                df = new SimpleDateFormat(EventoHorario.HOUR_FORMAT);
                edHorario.setText(df.format(eventoHorario.getHorario()));

                chkDom.setChecked(eventoHorario.isRecDom());
                chkSeg.setChecked(eventoHorario.isRecSeg());
                chkTer.setChecked(eventoHorario.isRecTer());
                chkQua.setChecked(eventoHorario.isRecQua());
                chkQui.setChecked(eventoHorario.isRecQui());
                chkSex.setChecked(eventoHorario.isRecSex());
                chkSab.setChecked(eventoHorario.isRecSab());
            }
        } else if (getIntent().getFlags() == INCLUIR) {
            alarme = new Alarme();
        }

        Button btnHorario = (Button) findViewById(R.id.button);
        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventoHorario = new EventoHorario();
                eventoHorario.setData_evento(new Date(System.currentTimeMillis()));
                eventoHorario.setHorario(new Date(System.currentTimeMillis()));

            }
        });

        Button btnLocal = (Button) findViewById(R.id.button2);
        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventoLugar = new EventoLugar();
                eventoLugar.setNomeLugar("Lugar Teste");
                eventoLugar.setLatitude(14.0042F);
                eventoLugar.setLongitude(13.1445F);
            }
        });

        Button btnSalvar = (Button) findViewById(R.id.button3);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarme.setTitulo(((TextView) AlarmeActivity.this.findViewById(R.id.editText)).getText().toString());

                Evento evento = new Evento();

                if(eventoHorario != null) {
                    eventoHorario.setRecDom(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox)).isChecked());
                    eventoHorario.setRecSeg(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox2)).isChecked());
                    eventoHorario.setRecTer(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox3)).isChecked());
                    eventoHorario.setRecQua(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox4)).isChecked());
                    eventoHorario.setRecQui(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox5)).isChecked());
                    eventoHorario.setRecSex(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox6)).isChecked());
                    eventoHorario.setRecSab(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox7)).isChecked());

                    evento.setEventoHorario(eventoHorario);
                }

                if(eventoLugar != null) {
                    evento.setEventoLugar(eventoLugar);
                }

                alarme.setEventos(evento);

                if(AlarmeActivity.this.getIntent().getFlags() == AlarmeActivity.EDITAR) {
                    alarmeDAO.atualizar(alarme);
                } else if (AlarmeActivity.this.getIntent().getFlags() == AlarmeActivity.INCLUIR) {
                    alarmeDAO.inserir(alarme);
                }

                AlarmeActivity.this.finish();
            }
        });

        Button btnCancelar = (Button) findViewById(R.id.button4);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmeActivity.this.finish();
            }
        });

        Button btnExcluir = (Button) findViewById(R.id.button5);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlarmeActivity.this.getIntent().getFlags() == AlarmeActivity.EDITAR) {
                    alarmeDAO.excluir(alarme);
                }
                AlarmeActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.dbHelper = new BrainiacDbHelper(this);
        this.alarmeDAO = new AlarmeDAO(this.dbHelper);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.dbHelper.close();
    }
}
