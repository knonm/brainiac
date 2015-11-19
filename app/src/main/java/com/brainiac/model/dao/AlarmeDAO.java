package com.brainiac.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brainiac.model.Alarme;
import com.brainiac.model.Evento;
import com.brainiac.model.sqlite.BrainiacContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheus on 09/11/2015.
 */
public class AlarmeDAO {

    private SQLiteOpenHelper dbHelper;

    private EventoDAO eventoDAO;

    public AlarmeDAO(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
        eventoDAO = new EventoDAO(this.dbHelper);
    }

    public long inserir(Alarme alarme) {
        long idEvento = eventoDAO.inserir(alarme.getEvento());
        long idAlarme;

        ContentValues values = new ContentValues();
        values.put(BrainiacContract.BDAlarme.COLUMN_NAME_ID_EVENTO, idEvento);
        values.put(BrainiacContract.BDAlarme.COLUMN_NAME_TITULO, alarme.getTitulo());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        idAlarme = db.insert(BrainiacContract.BDAlarme.TABLE_NAME, null, values);

        db.close();

        return idAlarme;
    }

    public void excluir(Alarme alarme) {
        eventoDAO.excluir(alarme.getEvento());

        String selection = BrainiacContract.BDAlarme.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(alarme.getId()) };

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(BrainiacContract.BDAlarme.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public void atualizar(Alarme alarme) {
        eventoDAO.atualizar(alarme.getEvento());

        ContentValues values = new ContentValues();
        values.put(BrainiacContract.BDAlarme.COLUMN_NAME_TITULO, alarme.getTitulo());

        String selection = BrainiacContract.BDAlarme.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(alarme.getId()) };

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update(BrainiacContract.BDAlarme.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    public Alarme[] consultarTodos() {
        String[] projection = {
                BrainiacContract.BDAlarme.COLUMN_NAME_ID,
                BrainiacContract.BDAlarme.COLUMN_NAME_ID_EVENTO,
                BrainiacContract.BDAlarme.COLUMN_NAME_TITULO
        };

        String sortOrder = BrainiacContract.BDAlarme.COLUMN_NAME_ID + " DESC";

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(BrainiacContract.BDAlarme.TABLE_NAME, projection, null, null, null, null, sortOrder);

        List<Alarme> listAlarme = new ArrayList<>();
        Alarme alarme;
        Evento evento;

        if(c.moveToFirst()) {
            do {
                alarme = new Alarme();
                alarme.setId(c.getLong(c.getColumnIndexOrThrow(BrainiacContract.BDAlarme.COLUMN_NAME_ID)));

                evento = eventoDAO.consultar(c.getLong(c.getColumnIndexOrThrow(BrainiacContract.BDAlarme.COLUMN_NAME_ID_EVENTO)));
                alarme.setEventos(evento);

                alarme.setTitulo(c.getString(c.getColumnIndexOrThrow(BrainiacContract.BDAlarme.COLUMN_NAME_TITULO)));

                listAlarme.add(alarme);
            } while(c.moveToNext());
        }

        c.close();
        db.close();

        return listAlarme.toArray(new Alarme[0]);
    }
}
