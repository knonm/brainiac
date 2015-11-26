package com.brainiac.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brainiac.model.EventoLugar;
import com.brainiac.model.sqlite.BrainiacContract;

public class EventoLugarDAO {

    private SQLiteOpenHelper dbHelper;

    public EventoLugarDAO(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long inserir(EventoLugar eventoLugar) {
        long idEventoLugar;

        ContentValues values = new ContentValues();

        values.put(BrainiacContract.BDEventoLugar.COLUMN_NAME_NOME_LUGAR, eventoLugar.getNomeLugar());
        values.put(BrainiacContract.BDEventoLugar.COLUMN_NAME_LATITUDE, eventoLugar.getLatitude());
        values.put(BrainiacContract.BDEventoLugar.COLUMN_NAME_LONGITUDE, eventoLugar.getLongitude());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        idEventoLugar = db.insert(BrainiacContract.BDEventoLugar.TABLE_NAME, null, values);

        db.close();

        return idEventoLugar;
    }

    public void excluir(EventoLugar eventoLugar) {
        String selection = BrainiacContract.BDEventoLugar.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(eventoLugar.getId()) };

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(BrainiacContract.BDEventoLugar.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public void atualizar(EventoLugar eventoLugar) {
        ContentValues values = new ContentValues();

        values.put(BrainiacContract.BDEventoLugar.COLUMN_NAME_NOME_LUGAR, eventoLugar.getNomeLugar());
        values.put(BrainiacContract.BDEventoLugar.COLUMN_NAME_LATITUDE, eventoLugar.getLatitude());
        values.put(BrainiacContract.BDEventoLugar.COLUMN_NAME_LONGITUDE, eventoLugar.getLongitude());

        String selection = BrainiacContract.BDEventoLugar.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(eventoLugar.getId()) };

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update(BrainiacContract.BDEventoLugar.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    public EventoLugar consultar(long id) {
        String[] projection = {
                BrainiacContract.BDEventoLugar.COLUMN_NAME_ID,
                BrainiacContract.BDEventoLugar.COLUMN_NAME_NOME_LUGAR,
                BrainiacContract.BDEventoLugar.COLUMN_NAME_LATITUDE,
                BrainiacContract.BDEventoLugar.COLUMN_NAME_LONGITUDE
        };

        String selection = BrainiacContract.BDEventoLugar.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(BrainiacContract.BDEventoLugar.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        EventoLugar eventoLugar = null;

        if(c.moveToFirst()) {
            eventoLugar = new EventoLugar();

            eventoLugar.setId(c.getLong(c.getColumnIndexOrThrow(BrainiacContract.BDEventoLugar.COLUMN_NAME_ID)));
            eventoLugar.setNomeLugar(c.getString(c.getColumnIndexOrThrow(BrainiacContract.BDEventoLugar.COLUMN_NAME_NOME_LUGAR)));
            eventoLugar.setLatitude(c.getFloat(c.getColumnIndexOrThrow(BrainiacContract.BDEventoLugar.COLUMN_NAME_LATITUDE)));
            eventoLugar.setLongitude(c.getFloat(c.getColumnIndexOrThrow(BrainiacContract.BDEventoLugar.COLUMN_NAME_LONGITUDE)));
        }

        c.close();
        db.close();

        return eventoLugar;
    }
}
