package com.brainiac.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by matheus on 09/11/2015.
 */
public class Alarme implements Parcelable {

    private long id;
    private Evento evento;
    private String titulo;

    public Alarme() {
        evento = new Evento();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEventos(Evento evento) {
        this.evento = evento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }

    public static transient final Parcelable.Creator<Alarme> CREATOR = new Creator<Alarme>() {
        @Override
        public Alarme createFromParcel(Parcel source) {
            Alarme alarme = new Alarme();
            alarme.id = source.readLong();
            alarme.evento = source.readParcelable(Evento.class.getClassLoader());
            alarme.titulo = source.readString();

            return alarme;
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
        dest.writeLong(id);
        dest.writeParcelable(evento, 0);
        dest.writeString(titulo);
    }
}
