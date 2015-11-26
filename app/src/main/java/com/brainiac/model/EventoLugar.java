package com.brainiac.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EventoLugar implements Parcelable {

    private long id;
    private String nomeLugar;
    private float latitude;
    private float longitude;

    public EventoLugar() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeLugar() {
        return nomeLugar;
    }

    public void setNomeLugar(String nomeLugar) {
        this.nomeLugar = nomeLugar;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public static transient final Parcelable.Creator<EventoLugar> CREATOR = new Creator<EventoLugar>() {
        @Override
        public EventoLugar createFromParcel(Parcel source) {
            EventoLugar eventoLugar = new EventoLugar();
            eventoLugar.id = source.readLong();
            eventoLugar.nomeLugar = source.readString();
            eventoLugar.latitude = source.readFloat();
            eventoLugar.longitude = source.readFloat();

            return eventoLugar;
        }

        @Override
        public EventoLugar[] newArray(int size) {
            return new EventoLugar[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nomeLugar);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }
}
