package com.brainiac.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brainiac.model.EventoHorario;
import com.brainiac.model.sqlite.BrainiacContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by matheus on 18/11/2015.
 */
public class EventoHorarioDAO {

    private SQLiteOpenHelper dbHelper;

    public EventoHorarioDAO(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long inserir(EventoHorario eventoHorario) {
        long idEventoHorario;
        DateFormat df;

        ContentValues values = new ContentValues();

        if(eventoHorario.getData_evento() != null) {
            df = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
            values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_DATA_EVENTO, df.format(eventoHorario.getData_evento()));
        } else {
            values.putNull(BrainiacContract.BDEventoHorario.COLUMN_NAME_DATA_EVENTO);
        }

        df = new SimpleDateFormat(EventoHorario.HOUR_FORMAT);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_HORARIO, df.format(eventoHorario.getHorario()));

        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_DOM, eventoHorario.isRecDom()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEG, eventoHorario.isRecSeg()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_TER, eventoHorario.isRecTer()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUA, eventoHorario.isRecQua()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUI, eventoHorario.isRecQui()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEX, eventoHorario.isRecSex()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SAB, eventoHorario.isRecSab()? 1 : 0);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        idEventoHorario = db.insert(BrainiacContract.BDEventoHorario.TABLE_NAME, null, values);

        db.close();

        return idEventoHorario;
    }

    public void excluir(EventoHorario eventoHorario) {
        String selection = BrainiacContract.BDEventoHorario.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(eventoHorario.getId()) };

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(BrainiacContract.BDEventoHorario.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public void atualizar(EventoHorario eventoHorario) {
        ContentValues values = new ContentValues();
        DateFormat df;

        if(eventoHorario.getData_evento() != null) {
            df = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
            values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_DATA_EVENTO, df.format(eventoHorario.getData_evento()));
        } else {
            values.putNull(BrainiacContract.BDEventoHorario.COLUMN_NAME_DATA_EVENTO);
        }

        df = new SimpleDateFormat(EventoHorario.HOUR_FORMAT);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_HORARIO, df.format(eventoHorario.getHorario()));

        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_DOM, eventoHorario.isRecDom()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEG, eventoHorario.isRecSeg()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_TER, eventoHorario.isRecTer()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUA, eventoHorario.isRecQua()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUI, eventoHorario.isRecQui()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEX, eventoHorario.isRecSex()? 1 : 0);
        values.put(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SAB, eventoHorario.isRecSab()? 1 : 0);

        String selection = BrainiacContract.BDEventoHorario.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(eventoHorario.getId()) };

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update(BrainiacContract.BDEventoHorario.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    public EventoHorario consultar(long id) {
        DateFormat df;

        String[] projection = {
                BrainiacContract.BDEventoHorario.COLUMN_NAME_ID,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_DATA_EVENTO,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_HORARIO,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_DOM,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEG,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_TER,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUA,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUI,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEX,
                BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SAB
        };

        String selection = BrainiacContract.BDEventoHorario.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(BrainiacContract.BDEventoHorario.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        EventoHorario eventoHorario = null;

        if(c.moveToFirst()) {
            eventoHorario = new EventoHorario();

            eventoHorario.setId(c.getLong(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_ID)));

            df = new SimpleDateFormat(EventoHorario.DATE_FORMAT);
            try {
                eventoHorario.setData_evento(df.parse(c.getString(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_DATA_EVENTO))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            df = new SimpleDateFormat(EventoHorario.HOUR_FORMAT);
            try {
                eventoHorario.setHorario(df.parse(c.getString(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_HORARIO))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            eventoHorario.setRecDom(c.getInt(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_DOM)) == 1);
            eventoHorario.setRecSeg(c.getInt(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEG)) == 1);
            eventoHorario.setRecTer(c.getInt(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_TER)) == 1);
            eventoHorario.setRecQua(c.getInt(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUA)) == 1);
            eventoHorario.setRecQui(c.getInt(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUI)) == 1);
            eventoHorario.setRecSex(c.getInt(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEX)) == 1);
            eventoHorario.setRecSab(c.getInt(c.getColumnIndexOrThrow(BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SAB)) == 1);
        }

        c.close();
        db.close();

        return eventoHorario;
    }
}
