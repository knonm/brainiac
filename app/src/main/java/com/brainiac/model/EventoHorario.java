package com.brainiac.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventoHorario implements Parcelable {

    public static final String STRING_DATE_FORMAT = "dd/MM/yyyy";
    private static final String STRING_HOUR_FORMAT = "HH:mm";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(EventoHorario.STRING_DATE_FORMAT);
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat HOUR_FORMAT = new SimpleDateFormat(EventoHorario.STRING_HOUR_FORMAT);

    private long id;
    private Date data_evento;
    private Date horario;
    private boolean recDom;
    private boolean recSeg;
    private boolean recTer;
    private boolean recQua;
    private boolean recQui;
    private boolean recSex;
    private boolean recSab;

    public EventoHorario() {
        data_evento = null;
        recDom = false;
        recSeg = false;
        recTer = false;
        recQua = false;
        recQui = false;
        recSex = false;
        recSab = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData_evento() {
        return data_evento;
    }

    public void setData_evento(Date data_evento) {
        this.data_evento = data_evento;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public boolean isRecDom() {
        return recDom;
    }

    public void setRecDom(boolean recDom) {
        this.recDom = recDom;
    }

    public boolean isRecSeg() {
        return recSeg;
    }

    public void setRecSeg(boolean recSeg) {
        this.recSeg = recSeg;
    }

    public boolean isRecTer() {
        return recTer;
    }

    public void setRecTer(boolean recTer) {
        this.recTer = recTer;
    }

    public boolean isRecQua() {
        return recQua;
    }

    public void setRecQua(boolean recQua) {
        this.recQua = recQua;
    }

    public boolean isRecQui() {
        return recQui;
    }

    public void setRecQui(boolean recQui) {
        this.recQui = recQui;
    }

    public boolean isRecSex() {
        return recSex;
    }

    public void setRecSex(boolean recSex) {
        this.recSex = recSex;
    }

    public boolean isRecSab() {
        return recSab;
    }

    public void setRecSab(boolean recSab) {
        this.recSab = recSab;
    }

    public static String horaToString(Date horario) {
        return EventoHorario.HOUR_FORMAT.format(horario);
    }

    public static Date stringToHora(String horario) throws ParseException {
        return EventoHorario.HOUR_FORMAT.parse(horario);
    }

    public static String dataEventoToString(Date data_evento) {
        return EventoHorario.DATE_FORMAT.format(data_evento);
    }

    public static Date stringToDataEvento(String data_evento) throws ParseException {
        return EventoHorario.DATE_FORMAT.parse(data_evento);
    }

    public static transient final Parcelable.Creator<EventoHorario> CREATOR = new Creator<EventoHorario>() {
        @Override
        public EventoHorario createFromParcel(Parcel source) {
            EventoHorario eventoHorario = new EventoHorario();
            eventoHorario.id = source.readLong();
            eventoHorario.data_evento = (Date) source.readValue(Date.class.getClassLoader());
            eventoHorario.horario = (Date) source.readValue(Date.class.getClassLoader());
            eventoHorario.recDom = source.readByte() != 0;
            eventoHorario.recSeg = source.readByte() != 0;
            eventoHorario.recTer = source.readByte() != 0;
            eventoHorario.recQua = source.readByte() != 0;
            eventoHorario.recQui = source.readByte() != 0;
            eventoHorario.recSex = source.readByte() != 0;
            eventoHorario.recSab = source.readByte() != 0;

            return eventoHorario;
        }

        @Override
        public EventoHorario[] newArray(int size) {
            return new EventoHorario[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeValue(data_evento);
        dest.writeValue(horario);
        dest.writeByte((byte) (recDom ? 1 : 0));
        dest.writeByte((byte) (recSeg ? 1 : 0));
        dest.writeByte((byte) (recTer ? 1 : 0));
        dest.writeByte((byte) (recQua ? 1 : 0));
        dest.writeByte((byte) (recQui ? 1 : 0));
        dest.writeByte((byte) (recSex ? 1 : 0));
        dest.writeByte((byte) (recSab ? 1 : 0));
    }
}
