package com.brainiac.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brainiac.model.Evento;
import com.brainiac.model.EventoHorario;
import com.brainiac.model.EventoLugar;
import com.brainiac.model.sqlite.BrainiacContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheus on 09/11/2015.
 */
public class EventoDAO {

    private SQLiteOpenHelper dbHelper;

    private EventoHorarioDAO eventoHorarioDAO;
    private EventoLugarDAO eventoLugarDAO;

    public EventoDAO(SQLiteOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
        eventoHorarioDAO = new EventoHorarioDAO(this.dbHelper);
        eventoLugarDAO = new EventoLugarDAO(this.dbHelper);
    }

    public long inserir(Evento evento) {
        long idEvento, idEventoHorario, idEventoLugar;

        ContentValues values = new ContentValues();

        if(evento.getEventoHorario() != null) {
            idEventoHorario = eventoHorarioDAO.inserir(evento.getEventoHorario());
            values.put(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO, idEventoHorario);
        }

        if(evento.getEventoLugar() != null) {
            idEventoLugar = eventoLugarDAO.inserir(evento.getEventoLugar());
            values.put(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_LUGAR, idEventoLugar);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        idEvento = db.insert(BrainiacContract.BDEvento.TABLE_NAME, null, values);

        db.close();

        return idEvento;
    }

    public void excluir(long id) {
        Evento eventoOld = consultar(id);

        if(eventoOld.getEventoLugar() != null) {
            eventoLugarDAO.excluir(eventoOld.getEventoLugar());
        }

        if(eventoOld.getEventoHorario() != null) {
            eventoHorarioDAO.excluir(eventoOld.getEventoHorario());
        }

        String selection = BrainiacContract.BDEvento.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(BrainiacContract.BDEvento.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public void atualizar(Evento evento) {
        if (evento.getEventoHorario() != null && evento.getEventoLugar() != null) {
            Evento eventoOld = consultar(evento.getId());

            ContentValues values = new ContentValues();

            if (evento.getEventoHorario() != null) {
                if(eventoOld.getEventoHorario() == null) {
                    eventoHorarioDAO.inserir(evento.getEventoHorario());
                } else {
                    eventoHorarioDAO.atualizar(evento.getEventoHorario());
                }
                values.put(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO, evento.getEventoHorario().getId());
            } else if (eventoOld.getEventoHorario() != null) {
                eventoHorarioDAO.excluir(evento.getEventoHorario());
                values.putNull(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO);
            }

            if (evento.getEventoLugar() != null) {
                if(eventoOld.getEventoLugar() == null) {
                    eventoLugarDAO.inserir(evento.getEventoLugar());
                } else {
                    eventoLugarDAO.atualizar(evento.getEventoLugar());
                }
                values.put(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO, evento.getEventoHorario().getId());
            } else if (eventoOld.getEventoLugar() != null) {
                eventoLugarDAO.excluir(evento.getEventoLugar());
                values.putNull(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_LUGAR);
            }

            String selection = BrainiacContract.BDEvento.COLUMN_NAME_ID + " = ?";
            String[] selectionArgs = {String.valueOf(evento.getId())};

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(BrainiacContract.BDEvento.TABLE_NAME, values, selection, selectionArgs);
            db.close();
        }
    }

    public Evento consultar(long id) {
        String[] projection = {
                BrainiacContract.BDEvento.COLUMN_NAME_ID,
                BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO,
                BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_LUGAR
        };

        String selection = BrainiacContract.BDEvento.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(BrainiacContract.BDEvento.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Evento evento = null;
        EventoHorario eventoHorario;
        EventoLugar eventoLugar;
        int colInd;
        long idTpEvento;

        if(c.moveToFirst()) {
            evento = new Evento();

            evento.setId(c.getLong(c.getColumnIndexOrThrow(BrainiacContract.BDEvento.COLUMN_NAME_ID)));

            colInd = c.getColumnIndexOrThrow(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO);

            // Tratamento para casos em que o id eh null
            try {
                idTpEvento = c.getLong(colInd);
                eventoHorario = eventoHorarioDAO.consultar(idTpEvento);
            } catch (Exception e) {
                eventoHorario = null;
            }
            evento.setEventoHorario(eventoHorario);

            colInd = c.getColumnIndexOrThrow(BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_LUGAR);

            // Tratamento para casos em que o id eh null
            try {
                idTpEvento = c.getLong(colInd);
                eventoLugar = eventoLugarDAO.consultar(idTpEvento);
            } catch (Exception e) {
                eventoLugar = null;
            }
            evento.setEventoLugar(eventoLugar);
        }

        c.close();
        db.close();

        return evento;
    }
}
