package com.brainiac.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by matheus on 09/11/2015.
 */
public class Evento implements Parcelable {

    private long id;
    private EventoLugar eventoLugar;
    private EventoHorario eventoHorario;

    public Evento() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EventoLugar getEventoLugar() {
        return eventoLugar;
    }

    public void setEventoLugar(EventoLugar eventoLugar) {
        this.eventoLugar = eventoLugar;
    }

    public EventoHorario getEventoHorario() {
        return eventoHorario;
    }

    public void setEventoHorario(EventoHorario eventoHorario) {
        this.eventoHorario = eventoHorario;
    }

    public static transient final Parcelable.Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel source) {
            Evento evento = new Evento();
            evento.id = source.readLong();
            evento.eventoLugar = source.readParcelable(Evento.class.getClassLoader());
            evento.eventoHorario = source.readParcelable(Evento.class.getClassLoader());

            return evento;
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeParcelable(eventoLugar, 0);
        dest.writeParcelable(eventoHorario, 0);
    }
}
