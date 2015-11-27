package com.brainiac;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Instrumentation;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.text.method.Touch;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

@SuppressWarnings("unused")
public class AlarmeActivityTest extends ActivityInstrumentationTestCase2<AlarmeActivity> {

    private AlarmeActivity alarmeActivity;
    private EditText edTitulo;
    private Button btnSalvar;
    private Button btnHorario;
    private RadioButton radioBtnData;

    public AlarmeActivityTest() {
        super(AlarmeActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp(); // Teste push
        Intent it = new Intent(getInstrumentation().getContext(), AlarmeActivity.class);
        it.putExtra(AlarmeActivity.INCLUIR_KEY, AlarmeActivity.INCLUIR);
        setActivityIntent(it);
        alarmeActivity = getActivity();
        edTitulo = (EditText) alarmeActivity.findViewById(R.id.activity_alarme_txt_titulo);
        btnSalvar = (Button) alarmeActivity.findViewById(R.id.activity_alarme_btn_salvar);
        btnHorario = (Button) alarmeActivity.findViewById(R.id.activity_alarme_btn_horario);
        radioBtnData = (RadioButton) alarmeActivity.findViewById(R.id.activity_alarme_radio_btn_data);
    }

    public void testPreconditions() {
        assertNotNull("alarmeActivity is null", alarmeActivity);
        assertNotNull("edTitulo is null", edTitulo);
        assertNotNull("btnSalvar is null", btnSalvar);
        assertNotNull("btnHorario is null", btnHorario);
        assertNotNull("radioBtnData is null", radioBtnData);
    }

    @MediumTest
    public void testTituloVazio() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                edTitulo.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("");
        getInstrumentation().waitForIdleSync();

        TouchUtils.clickView(this, btnSalvar);
        assertTrue(alarmeActivity.isMsgErro());
    }

    @MediumTest
    public void testHorarioLocal() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                edTitulo.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("TESTCASE");
        getInstrumentation().waitForIdleSync();

        TouchUtils.clickView(this, btnSalvar);
        assertTrue(alarmeActivity.isMsgErro());
    }

    @MediumTest
    public void testHorarioSemana() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                edTitulo.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("TESTCASE");
        getInstrumentation().waitForIdleSync();

        TouchUtils.clickView(this, btnHorario);
        TouchUtils.clickView(this, alarmeActivity.getTimePickerDialog().getButton(TimePickerDialog.BUTTON_POSITIVE));

        TouchUtils.clickView(this, btnSalvar);
        assertTrue(alarmeActivity.isMsgErro());
    }

    @MediumTest
    public void testHorarioData() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                edTitulo.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("TESTCASE");
        getInstrumentation().waitForIdleSync();

        TouchUtils.clickView(this, btnHorario);
        TouchUtils.clickView(this, alarmeActivity.getTimePickerDialog().getButton(DialogInterface.BUTTON_POSITIVE));

        TouchUtils.clickView(this, radioBtnData);
        TouchUtils.clickView(this, alarmeActivity.getDatePickerDialog().getButton(DialogInterface.BUTTON_POSITIVE));

        TouchUtils.clickView(this, btnSalvar);

        assertFalse(alarmeActivity.isActive());
    }
}
