package com.brainiac;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.brainiac.model.Alarme;
import com.brainiac.model.Evento;
import com.brainiac.model.EventoHorario;
import com.brainiac.model.EventoLugar;
import com.brainiac.model.dao.AlarmeDAO;
import com.brainiac.model.sqlite.BrainiacDbHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // Instanciando elementos da interface
        CheckBox chkDom = (CheckBox) findViewById(R.id.checkBox);
        CheckBox chkSeg = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox chkTer = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox chkQua = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox chkQui = (CheckBox) findViewById(R.id.checkBox5);
        CheckBox chkSex = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox chkSab = (CheckBox) findViewById(R.id.checkBox7);
        Button btnLocal = (Button) findViewById(R.id.button);
        Button btnHorario = (Button) findViewById(R.id.button2);
        Button btnSalvar = (Button) findViewById(R.id.button3);
        Button btnCancelar = (Button) findViewById(R.id.button4);
        Button btnExcluir = (Button) findViewById(R.id.button5);
        Button btnExcluirLocal = (Button) findViewById(R.id.button12);
        Button btnExcluirHorario = (Button) findViewById(R.id.button13);
        final EditText edData = (EditText) findViewById(R.id.editText5);
        EditText edLugar = (EditText) findViewById(R.id.editText2);
        final EditText edHorario = (EditText) findViewById(R.id.editText3);
        final RadioButton radioBtnSemana = (RadioButton) findViewById(R.id.radio_btn_semana);
        final RadioButton radioBtnData = (RadioButton) findViewById(R.id.radio_btn_data);

        if(getIntent().getFlags() == EDITAR) {
            alarme = getIntent().getParcelableExtra(ALARME_KEY);
            eventoLugar = alarme.getEvento().getEventoLugar();
            eventoHorario = alarme.getEvento().getEventoHorario();

            ((EditText) findViewById(R.id.editText)).setText(alarme.getTitulo());

            if(eventoLugar != null) {
                edLugar.setText(eventoLugar.getNomeLugar());
            }

            if(eventoHorario != null) {
                DateFormat df;

                if(eventoHorario.getData_evento() == null) {
                    radioBtnSemana.setChecked(true);
                    chkDom.setChecked(eventoHorario.isRecDom());
                    chkSeg.setChecked(eventoHorario.isRecSeg());
                    chkTer.setChecked(eventoHorario.isRecTer());
                    chkQua.setChecked(eventoHorario.isRecQua());
                    chkQui.setChecked(eventoHorario.isRecQui());
                    chkSex.setChecked(eventoHorario.isRecSex());
                    chkSab.setChecked(eventoHorario.isRecSab());
                } else {
                    radioBtnData.setChecked(true);
                    df = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
                    edData.setText(df.format(eventoHorario.getData_evento()));
                }

                df = new SimpleDateFormat(EventoHorario.HOUR_FORMAT);
                ((EditText) findViewById(R.id.editText3)).setText(df.format(eventoHorario.getHorario()));
            } else {
                radioBtnSemana.setEnabled(false);
                radioBtnData.setEnabled(false);
                edData.setEnabled(false);
            }
        } else if (getIntent().getFlags() == INCLUIR) {
            alarme = new Alarme();

            btnExcluir.setEnabled(false);
            radioBtnSemana.setEnabled(false);
            radioBtnData.setEnabled(false);

            edData.setEnabled(false);

            eventoHorario = null;
            eventoLugar = null;
        }

        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        eventoHorario = new EventoHorario();

                        SimpleDateFormat format = new SimpleDateFormat(EventoHorario.HOUR_FORMAT);
                        String horario = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                        try {
                            eventoHorario.setHorario(format.parse(horario));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        edHorario.setText(horario);
                        radioBtnSemana.setEnabled(true);
                        radioBtnData.setEnabled(true);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmeActivity.this, listener, 12, 0, true);
                timePickerDialog.show();
            }
        });

        edData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat format = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
                        String data = String.valueOf(year) + "/" + String.valueOf(dayOfMonth) + "/" + String.valueOf(year);
                        try {
                            eventoHorario.setHorario(format.parse(data));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        edData.setText(data);
                    }
                };
                SimpleDateFormat format = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
                GregorianCalendar cal = new GregorianCalendar();

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AlarmeActivity.this, listener, cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AlarmeActivity.this, LugarActivity.class);

                if(eventoLugar != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(LugarActivity.LUGAR_KEY, eventoLugar);

                    it.putExtras(bundle);
                    it.setFlags(LugarActivity.EDITAR);
                } else {
                    it.setFlags(LugarActivity.INCLUIR);
                }

                AlarmeActivity.this.startActivityForResult(it, LugarActivity.FLAG_ACTIVITY);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOk = true;

                CheckBox chkDom = (CheckBox) findViewById(R.id.checkBox);
                CheckBox chkSeg = (CheckBox) findViewById(R.id.checkBox2);
                CheckBox chkTer = (CheckBox) findViewById(R.id.checkBox3);
                CheckBox chkQua = (CheckBox) findViewById(R.id.checkBox4);
                CheckBox chkQui = (CheckBox) findViewById(R.id.checkBox5);
                CheckBox chkSex = (CheckBox) findViewById(R.id.checkBox6);
                CheckBox chkSab = (CheckBox) findViewById(R.id.checkBox7);
                RadioButton radioBtnSemana = (RadioButton) findViewById(R.id.radio_btn_semana);
                RadioButton radioBtnData = (RadioButton) findViewById(R.id.radio_btn_data);
                TextView tvTitulo = (TextView) AlarmeActivity.this.findViewById(R.id.editText);
                EditText edData = (EditText) findViewById(R.id.editText5);

                if(tvTitulo.getText() == null || tvTitulo.getText().length() <= 0) {
                    createAlert("O título do alarme está vazio.").show();
                    isOk = false;
                } else if (eventoHorario == null && eventoLugar == null) {
                    createAlert("Preencha o local ou o horário do alarme.").show();
                    isOk = false;
                } else if (eventoHorario != null) {
                    if(radioBtnSemana.isChecked() && !chkDom.isChecked() && !chkSeg.isChecked() && !chkTer.isChecked() &&
                            !chkQua.isChecked() && !chkQui.isChecked() && !chkSex.isChecked() &&
                            !chkSab.isChecked()) {
                        createAlert("Preencha pelo menos um dos checkbox de dias da semana.").show();
                        isOk = false;
                    } else if(radioBtnData.isChecked()) {
                        if((edData.getText() == null || edData.getText().length() <= 0)) {
                            createAlert("Preencha a data do alarme.").show();
                            isOk = false;
                        }

                        try {
                            SimpleDateFormat df = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
                            df.parse(edData.getText().toString());
                        } catch (Exception e) {
                            createAlert("Preencha a data com o formato correto (" + EventoHorario.DATE_FORMAT + ").").show();
                            isOk = false;
                        }
                    }
                }

                if(isOk) {
                    alarme.setTitulo(((TextView) AlarmeActivity.this.findViewById(R.id.editText)).getText().toString());

                    Evento evento = new Evento();

                    if (eventoHorario != null) {
                        if (radioBtnSemana.isChecked()) {
                            eventoHorario.setRecDom(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox)).isChecked());
                            eventoHorario.setRecSeg(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox2)).isChecked());
                            eventoHorario.setRecTer(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox3)).isChecked());
                            eventoHorario.setRecQua(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox4)).isChecked());
                            eventoHorario.setRecQui(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox5)).isChecked());
                            eventoHorario.setRecSex(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox6)).isChecked());
                            eventoHorario.setRecSab(((CheckBox) AlarmeActivity.this.findViewById(R.id.checkBox7)).isChecked());
                        } else if (radioBtnData.isChecked()) {
                            SimpleDateFormat df = new SimpleDateFormat(EventoHorario.DATE_FORMAT);

                            try {
                                eventoHorario.setData_evento(df.parse(edData.getText().toString()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        evento.setEventoHorario(eventoHorario);
                    }

                    if (eventoLugar != null) {
                        evento.setEventoLugar(eventoLugar);
                    }

                    alarme.setEventos(evento);

                    if (AlarmeActivity.this.getIntent().getFlags() == AlarmeActivity.EDITAR) {
                        alarmeDAO.atualizar(alarme);
                    } else if (AlarmeActivity.this.getIntent().getFlags() == AlarmeActivity.INCLUIR) {
                        alarmeDAO.inserir(alarme);
                    }

                    AlarmeActivity.this.finish();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmeActivity.this.finish();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AlarmeActivity.this.getIntent().getFlags() == AlarmeActivity.EDITAR) {
                    alarmeDAO.excluir(alarme);
                }
                AlarmeActivity.this.finish();
            }
        });

        btnExcluirLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edLugar = (EditText) findViewById(R.id.editText2);
                edLugar.setText("");

                eventoLugar = null;
            }
        });

        btnExcluirHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edHorario = (EditText) findViewById(R.id.editText3);
                edHorario.setText("");

                CheckBox chkDom = (CheckBox) findViewById(R.id.checkBox);
                CheckBox chkSeg = (CheckBox) findViewById(R.id.checkBox2);
                CheckBox chkTer = (CheckBox) findViewById(R.id.checkBox3);
                CheckBox chkQua = (CheckBox) findViewById(R.id.checkBox4);
                CheckBox chkQui = (CheckBox) findViewById(R.id.checkBox5);
                CheckBox chkSex = (CheckBox) findViewById(R.id.checkBox6);
                CheckBox chkSab = (CheckBox) findViewById(R.id.checkBox7);
                EditText edData = (EditText) findViewById(R.id.editText5);

                chkDom.setChecked(false);
                chkSeg.setChecked(false);
                chkTer.setChecked(false);
                chkQua.setChecked(false);
                chkQui.setChecked(false);
                chkSex.setChecked(false);
                chkSab.setChecked(false);
                edData.setText("");

                eventoHorario = null;

                radioBtnSemana.setEnabled(false);
                radioBtnData.setEnabled(false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == LugarActivity.FLAG_ACTIVITY) {
            EditText edLugar = (EditText) findViewById(R.id.editText2);

            if(data.getFlags() == LugarActivity.RESULT_CODE_EXCLUIR) {
                eventoLugar.setId(-1L);
                edLugar.setText("");
            } else if (data.getFlags() == LugarActivity.RESULT_CODE_SALVAR) {
                eventoLugar = data.getParcelableExtra(LugarActivity.LUGAR_KEY);
                edLugar.setText(eventoLugar.getNomeLugar());
            }

        }
    }

    private AlertDialog createAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmeActivity.this);
        builder.setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private void habilitarHorario(boolean habilitar) {

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton radioBtnSemana = (RadioButton) findViewById(R.id.radio_btn_semana);
        RadioButton radioBtnData = (RadioButton) findViewById(R.id.radio_btn_data);
        CheckBox chkDom = (CheckBox) findViewById(R.id.checkBox);
        CheckBox chkSeg = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox chkTer = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox chkQua = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox chkQui = (CheckBox) findViewById(R.id.checkBox5);
        CheckBox chkSex = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox chkSab = (CheckBox) findViewById(R.id.checkBox7);
        EditText edData = (EditText) findViewById(R.id.editText5);

        if(view.getId() == R.id.radio_btn_semana && checked) {
            chkDom.setEnabled(true);
            chkSeg.setEnabled(true);
            chkTer.setEnabled(true);
            chkQua.setEnabled(true);
            chkQui.setEnabled(true);
            chkSex.setEnabled(true);
            chkSab.setEnabled(true);
            edData.setEnabled(false);
            radioBtnData.setChecked(false);
        } else if (view.getId() == R.id.radio_btn_data && checked) {
            chkDom.setEnabled(false);
            chkSeg.setEnabled(false);
            chkTer.setEnabled(false);
            chkQua.setEnabled(false);
            chkQui.setEnabled(false);
            chkSex.setEnabled(false);
            chkSab.setEnabled(false);
            edData.setEnabled(true);
            radioBtnSemana.setChecked(false);
        }
    }
}
