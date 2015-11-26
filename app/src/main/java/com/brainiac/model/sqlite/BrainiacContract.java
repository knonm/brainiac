package com.brainiac.model.sqlite;

import android.provider.BaseColumns;

public final class BrainiacContract {

    public BrainiacContract() {

    }

    public static abstract class BDAlarme implements BaseColumns {
        public static final String TABLE_NAME = "ALARME";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ID_EVENTO = "id_evento";
        public static final String COLUMN_NAME_TITULO = "titulo";
    }

    public static abstract class BDEvento implements BaseColumns {
        public static final String TABLE_NAME = "EVENTO";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_ID_EVENTO_LUGAR = "id_evento_lugar";
        public static final String COLUMN_NAME_ID_EVENTO_HORARIO = "id_evento_horario";
    }

    public static abstract class BDEventoHorario implements BaseColumns {
        public static final String TABLE_NAME = "EVENTO_HORARIO";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DATA_EVENTO = "data_evento";
        public static final String COLUMN_NAME_HORARIO = "horario";
        public static final String COLUMN_NAME_REC_DOM = "rec_dom";
        public static final String COLUMN_NAME_REC_SEG = "rec_seg";
        public static final String COLUMN_NAME_REC_TER = "rec_ter";
        public static final String COLUMN_NAME_REC_QUA = "rec_qua";
        public static final String COLUMN_NAME_REC_QUI = "rec_qui";
        public static final String COLUMN_NAME_REC_SEX = "rec_sex";
        public static final String COLUMN_NAME_REC_SAB = "rec_sab";
    }

    public static abstract class BDEventoLugar implements BaseColumns {
        public static final String TABLE_NAME = "EVENTO_LUGAR";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NOME_LUGAR = "nome_lugar";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
    }
}
