package com.brainiac.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by matheus on 09/11/2015.
 */
public class Alarme implements Parcelable {

    private int id;
    private Evento[] eventos;
    private String titulo;

    public Alarme() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Evento[] getEventos() {
        return eventos;
    }

    public void setEventos(Evento[] eventos) {
        this.eventos = eventos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public static transient final Parcelable.Creator<Alarme> CREATOR = new Creator<Alarme>() {
        @Override
        public Alarme createFromParcel(Parcel source) {
            Alarme a = new Alarme();
            a.id = source.readInt();
            //a.eventos = source.createTypedArray(Evento.class);

            return a;
        }

        @Override
        public Alarme[] newArray(int size) {
            return new Alarme[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
