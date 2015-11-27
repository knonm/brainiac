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
import com.brainiac.model.EventoHorario;
import com.brainiac.model.EventoLugar;
import com.brainiac.model.dao.AlarmeDAO;
import com.brainiac.model.sqlite.BrainiacDbHelper;

import java.text.ParseException;
import java.util.Calendar;
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

    // Para validacao nos casos de teste
    private boolean isMsgErro;

    // UI
    private CheckBox chkDom;
    private CheckBox chkSeg;
    private CheckBox chkTer;
    private CheckBox chkQua;
    private CheckBox chkQui;
    private CheckBox chkSex;
    private CheckBox chkSab;
    private EditText edData;
    private EditText edLugar;
    private EditText edHorario;
    private RadioButton radioBtnSemana;
    private RadioButton radioBtnData;
    private TextView tvTitulo;

    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // Instanciando elementos da interface
        this.chkDom = (CheckBox) findViewById(R.id.checkBox);
        this.chkSeg = (CheckBox) findViewById(R.id.checkBox2);
        this.chkTer = (CheckBox) findViewById(R.id.checkBox3);
        this.chkQua = (CheckBox) findViewById(R.id.checkBox4);
        this.chkQui = (CheckBox) findViewById(R.id.checkBox5);
        this.chkSex = (CheckBox) findViewById(R.id.checkBox6);
        this.chkSab = (CheckBox) findViewById(R.id.checkBox7);
        Button btnLocal = (Button) findViewById(R.id.activity_alarme_btn_lugar);
        Button btnHorario = (Button) findViewById(R.id.activity_alarme_btn_horario);
        Button btnSalvar = (Button) findViewById(R.id.activity_alarme_btn_salvar);
        Button btnCancelar = (Button) findViewById(R.id.button4);
        Button btnExcluir = (Button) findViewById(R.id.button5);
        Button btnExcluirLocal = (Button) findViewById(R.id.button12);
        Button btnExcluirHorario = (Button) findViewById(R.id.button13);
        this.edData = (EditText) findViewById(R.id.editText5);
        this.edLugar = (EditText) findViewById(R.id.editText2);
        this.edHorario = (EditText) findViewById(R.id.editText3);
        this.radioBtnSemana = (RadioButton) findViewById(R.id.radio_btn_semana);
        this.radioBtnData = (RadioButton) findViewById(R.id.activity_alarme_radio_btn_data);
        this.tvTitulo = (TextView) AlarmeActivity.this.findViewById(R.id.activity_alarme_txt_titulo);

        if(getIntent().getFlags() == EDITAR) {
            this.alarme = getIntent().getParcelableExtra(ALARME_KEY);
            this.eventoLugar = this.alarme.getEvento().getEventoLugar();
            this.eventoHorario = this.alarme.getEvento().getEventoHorario();

            this.tvTitulo.setText(this.alarme.getTitulo());

            if(this.eventoLugar != null) {
                this.edLugar.setText(this.eventoLugar.getNomeLugar());
            }

            if(eventoHorario != null) {
                if(eventoHorario.getData_evento() == null) {
                    this.habilitarRadioSemana();
                    this.chkDom.setChecked(this.eventoHorario.isRecDom());
                    this.chkSeg.setChecked(this.eventoHorario.isRecSeg());
                    this.chkTer.setChecked(this.eventoHorario.isRecTer());
                    this.chkQua.setChecked(this.eventoHorario.isRecQua());
                    this.chkQui.setChecked(this.eventoHorario.isRecQui());
                    this.chkSex.setChecked(this.eventoHorario.isRecSex());
                    this.chkSab.setChecked(this.eventoHorario.isRecSab());
                } else {
                    this.habilitarRadioData();
                    this.edData.setText(EventoHorario.dataEventoToString(this.eventoHorario.getData_evento()));
                }

                this.edHorario.setText(EventoHorario.horaToString(this.eventoHorario.getHorario()));
            } else {
                this.habilitarHorario(false);
            }
        } else if (getIntent().getFlags() == INCLUIR) {
            this.alarme = new Alarme();
            this.eventoLugar = null;
            btnExcluir.setEnabled(false);
            this.habilitarHorario(false);
        }

        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        AlarmeActivity.this.habilitarHorario(true);
                        String horario = String.format("%02d:%02d", hourOfDay, minute);

                        try {
                            AlarmeActivity.this.eventoHorario.setHorario(EventoHorario.stringToHora(horario));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        AlarmeActivity.this.edHorario.setText(horario);
                    }
                };
                AlarmeActivity.this.timePickerDialog = new TimePickerDialog(AlarmeActivity.this, listener, 12, 0, true);
                AlarmeActivity.this.timePickerDialog.show();
            }
        });

        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AlarmeActivity.this, LugarActivity.class);

                if (AlarmeActivity.this.eventoLugar != null) {
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
                AlarmeActivity.this.isMsgErro = false;

                try {
                    AlarmeActivity.this.validarCampos();
                } catch (AlarmeActivityValidaException e) {
                    AlarmeActivity.this.isMsgErro = true;

                    AlertDialog.Builder builder = new AlertDialog.Builder(AlarmeActivity.this);
                    builder.setMessage(e.getMessage()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }

                if (!AlarmeActivity.this.isMsgErro) {
                    AlarmeActivity.this.alarme.setTitulo(AlarmeActivity.this.tvTitulo.getText().toString());

                    if (AlarmeActivity.this.eventoHorario != null) {
                        if (AlarmeActivity.this.radioBtnSemana.isChecked()) {
                            AlarmeActivity.this.eventoHorario.setRecDom(AlarmeActivity.this.chkDom.isChecked());
                            AlarmeActivity.this.eventoHorario.setRecSeg(AlarmeActivity.this.chkSeg.isChecked());
                            AlarmeActivity.this.eventoHorario.setRecTer(AlarmeActivity.this.chkTer.isChecked());
                            AlarmeActivity.this.eventoHorario.setRecQua(AlarmeActivity.this.chkQua.isChecked());
                            AlarmeActivity.this.eventoHorario.setRecQui(AlarmeActivity.this.chkQui.isChecked());
                            AlarmeActivity.this.eventoHorario.setRecSex(AlarmeActivity.this.chkSex.isChecked());
                            AlarmeActivity.this.eventoHorario.setRecSab(AlarmeActivity.this.chkSab.isChecked());
                        }
                    }

                    AlarmeActivity.this.alarme.getEvento().setEventoLugar(eventoLugar);
                    AlarmeActivity.this.alarme.getEvento().setEventoHorario(eventoHorario);

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
                if (AlarmeActivity.this.getIntent().getFlags() == AlarmeActivity.EDITAR) {
                    AlarmeActivity.this.alarmeDAO.excluir(AlarmeActivity.this.alarme);
                }
                AlarmeActivity.this.finish();
            }
        });

        btnExcluirLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmeActivity.this.edLugar.setText("");
                AlarmeActivity.this.eventoLugar = null;
            }
        });

        btnExcluirHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmeActivity.this.habilitarHorario(false);
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

    private void validarCampos() throws AlarmeActivityValidaException {
        if(tvTitulo.getText() == null || tvTitulo.getText().length() <= 0) {
            throw new AlarmeActivityValidaException("O título do alarme está vazio.");
        }

        if(eventoHorario == null && eventoLugar == null) {
            throw new AlarmeActivityValidaException("Preencha o local ou o horário do alarme.");
        }

        if(this.eventoHorario != null) {
            if(radioBtnSemana.isChecked() && !chkDom.isChecked() && !chkSeg.isChecked() && !chkTer.isChecked() &&
                    !chkQua.isChecked() && !chkQui.isChecked() && !chkSex.isChecked() &&
                    !chkSab.isChecked()) {
                throw new AlarmeActivityValidaException("Preencha pelo menos um dos checkbox de dias da semana.");
            } else if(radioBtnData.isChecked()) {
                if(this.eventoHorario.getData_evento() == null) {
                    throw new AlarmeActivityValidaException("Preencha a data do alarme.");
                }
                try {
                    EventoHorario.stringToDataEvento(edData.getText().toString());
                } catch (Exception e) {
                    throw new AlarmeActivityValidaException("Preencha a data com o formato correto (" + EventoHorario.STRING_DATE_FORMAT + ").");
                }
            }
        }
    }

    private void habilitarHorario(boolean habilitar) {
        this.radioBtnSemana.setEnabled(habilitar);
        this.radioBtnData.setEnabled(habilitar);
        this.chkDom.setEnabled(habilitar);
        this.chkSeg.setEnabled(habilitar);
        this.chkTer.setEnabled(habilitar);
        this.chkQua.setEnabled(habilitar);
        this.chkQui.setEnabled(habilitar);
        this.chkSex.setEnabled(habilitar);
        this.chkSab.setEnabled(habilitar);

        if(habilitar) {
            this.eventoHorario = new EventoHorario();
            this.edData.setText("");
            habilitarRadioSemana();
        } else {
            this.eventoHorario = null;
            this.radioBtnData.setChecked(false);
            this.radioBtnSemana.setChecked(false);
            this.chkDom.setChecked(false);
            this.chkSeg.setChecked(false);
            this.chkTer.setChecked(false);
            this.chkQua.setChecked(false);
            this.chkQui.setChecked(false);
            this.chkSex.setChecked(false);
            this.chkSab.setChecked(false);
            this.edData.setText("");
            this.edHorario.setText("");
        }
    }

    private void habilitarRadioSemana() {
        chkDom.setEnabled(true);
        chkSeg.setEnabled(true);
        chkTer.setEnabled(true);
        chkQua.setEnabled(true);
        chkQui.setEnabled(true);
        chkSex.setEnabled(true);
        chkSab.setEnabled(true);
        radioBtnData.setChecked(false);
        radioBtnSemana.setChecked(true);
    }

    private void habilitarRadioData() {
        chkDom.setEnabled(false);
        chkSeg.setEnabled(false);
        chkTer.setEnabled(false);
        chkQua.setEnabled(false);
        chkQui.setEnabled(false);
        chkSex.setEnabled(false);
        chkSab.setEnabled(false);
        radioBtnData.setChecked(true);
        radioBtnSemana.setChecked(false);
    }

    @SuppressWarnings("unused")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        if(view.getId() == R.id.radio_btn_semana && checked) {
            this.habilitarRadioSemana();
            this.edData.setText("");
            this.eventoHorario.setData_evento(null);
        } else if (view.getId() == R.id.activity_alarme_radio_btn_data && checked) {
            this.habilitarRadioData();
            this.chkDom.setChecked(false);
            this.chkSeg.setChecked(false);
            this.chkTer.setChecked(false);
            this.chkQua.setChecked(false);
            this.chkQui.setChecked(false);
            this.chkSex.setChecked(false);
            this.chkSab.setChecked(false);

            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String data = String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear+1, year);
                    try {
                        AlarmeActivity.this.eventoHorario.setData_evento(EventoHorario.stringToDataEvento(data));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    AlarmeActivity.this.edData.setText(data);
                }
            };
            GregorianCalendar cal = new GregorianCalendar();

            this.datePickerDialog =
                    new DatePickerDialog(AlarmeActivity.this, listener, cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            this.datePickerDialog.show();
        }
    }

    public DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }

    public TimePickerDialog getTimePickerDialog() {
        return timePickerDialog;
    }

    public boolean isMsgErro() {
        return isMsgErro;
    }

    private class AlarmeActivityValidaException extends Exception {

        public AlarmeActivityValidaException(String msg) {
            super(msg);
        }
    }
}
