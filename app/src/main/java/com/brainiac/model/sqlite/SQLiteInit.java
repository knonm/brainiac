package com.brainiac.model.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by matheus on 09/11/2015.
 */
public class SQLiteInit extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "brainiac.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TB_EVENTO =
            "CREATE TABLE EVENTO (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_evento_lugar INTEGER NOT NULL, " +
                    "id_evento_horario INTEGER NOT NULL, " +
                    "FOREIGN KEY(id_evento_lugar) REFERENCES TIPOS_EVENTO(id), " +
                    "FOREIGN KEY(id_evento_horario) REFERENCES TIPOS_EVENTO(id)" +
                    ")";

    private static final String TB_EVENTO_LUGAR =
            "CREATE TABLE EVENTO_LUGAR (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_evento INTEGER NOT NULL, " +
                    "lat REAL NOT NULL, " +
                    "long REAL NOT NULL, " +
                    "FOREIGN KEY(id_evento) REFERENCES EVENTO(id)" +
                    ")";

    private static final String TB_EVENTO_HORARIO =
            "CREATE TABLE EVENTO_HORARIO (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_evento INTEGER NOT NULL, " +
                    "horario_ini REAL NOT NULL, " +
                    "horario_fim REAL NOT NULL, " +
                    "FOREIGN KEY(id_evento) REFERENCES EVENTO(id)" +
                    ")";

    private static final String TB_ALARME =
            "CREATE TABLE ALARME (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_evento INTEGER NOT NULL, " +
                    "titulo TEXT NOT NULL, " +
                    "FOREIGN KEY(id_evento) REFERENCES EVENTO(id)" +
                    ")";

    public SQLiteInit(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TB_EVENTO);
        db.execSQL(TB_EVENTO_LUGAR);
        db.execSQL(TB_EVENTO_HORARIO);
        db.execSQL(TB_ALARME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS EVENTO_HORARIO"); // Colocar CASCADE
        db.execSQL("DROP TABLE IF EXISTS EVENTO_LUGAR"); // Colocar CASCADE
        db.execSQL("DROP TABLE IF EXISTS EVENTO"); // Colocar CASCADE
        db.execSQL("DROP TABLE IF EXISTS ALARME"); // Colocar CASCADE
        onCreate(db);
    }
}
