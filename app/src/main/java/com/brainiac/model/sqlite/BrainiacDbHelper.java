package com.brainiac.model.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matheus on 09/11/2015.
 */
public class BrainiacDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "brainiac.db";
    private static final int DATABASE_VERSION = 22;

    private static final String TB_EVENTO_LUGAR =
            "CREATE TABLE " + BrainiacContract.BDEventoLugar.TABLE_NAME + " (" +
                    BrainiacContract.BDEventoLugar.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BrainiacContract.BDEventoLugar.COLUMN_NAME_NOME_LUGAR + " TEXT NOT NULL," +
                    BrainiacContract.BDEventoLugar.COLUMN_NAME_LATITUDE + " REAL NOT NULL, " +
                    BrainiacContract.BDEventoLugar.COLUMN_NAME_LONGITUDE + " REAL NOT NULL" +
                    ")";

    private static final String TB_EVENTO_HORARIO =
            "CREATE TABLE " + BrainiacContract.BDEventoHorario.TABLE_NAME + " (" +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_DATA_EVENTO + " TEXT, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_HORARIO + " TEXT NOT NULL, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_DOM + " INTEGER, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEG + " INTEGER, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_TER + " INTEGER, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUA + " INTEGER, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_QUI + " INTEGER, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SEX + " INTEGER, " +
                    BrainiacContract.BDEventoHorario.COLUMN_NAME_REC_SAB + " INTEGER" +
                    ")";

    private static final String TB_EVENTO =
            "CREATE TABLE " + BrainiacContract.BDEvento.TABLE_NAME + " (" +
                    BrainiacContract.BDEvento.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_LUGAR + " INTEGER, " +
                    BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO + " INTEGER, " +
                    "FOREIGN KEY(" + BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_LUGAR + ") " +
                    "REFERENCES " + BrainiacContract.BDEventoLugar.TABLE_NAME + "(" + BrainiacContract.BDEventoLugar.COLUMN_NAME_ID + "), " +
                    "FOREIGN KEY(" + BrainiacContract.BDEvento.COLUMN_NAME_ID_EVENTO_HORARIO + ") " +
                    "REFERENCES " + BrainiacContract.BDEventoHorario.TABLE_NAME + "(" + BrainiacContract.BDEventoHorario.COLUMN_NAME_ID + ")" +
                    ")";

    private static final String TB_ALARME =
            "CREATE TABLE " + BrainiacContract.BDAlarme.TABLE_NAME + " (" +
                    BrainiacContract.BDAlarme.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BrainiacContract.BDAlarme.COLUMN_NAME_ID_EVENTO + " INTEGER NOT NULL, " +
                    BrainiacContract.BDAlarme.COLUMN_NAME_TITULO + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + BrainiacContract.BDAlarme.COLUMN_NAME_ID_EVENTO + ") " +
                    "REFERENCES " + BrainiacContract.BDEvento.TABLE_NAME + "(" + BrainiacContract.BDEvento.COLUMN_NAME_ID + ")" +
                    ")";

    public BrainiacDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TB_EVENTO_LUGAR);
        db.execSQL(TB_EVENTO_HORARIO);
        db.execSQL(TB_EVENTO);
        db.execSQL(TB_ALARME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BrainiacContract.BDEventoHorario.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BrainiacContract.BDEventoLugar.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BrainiacContract.BDEvento.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BrainiacContract.BDAlarme.TABLE_NAME);
        onCreate(db);
    }
}
