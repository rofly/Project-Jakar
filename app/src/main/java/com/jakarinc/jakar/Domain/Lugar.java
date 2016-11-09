package com.jakarinc.jakar.Domain;

/**
 * Created by Conta Única on 04/11/2016.
 */

public class Lugar {
    private double latitude;
    private double longitudde;
    private String id;

    public Lugar() {
    }

    public Lugar(double latitude, double longitudde, String id) {
        this.latitude = latitude;
        this.longitudde = longitudde;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lugar lugar = (Lugar) o;

        if (Double.compare(lugar.latitude, latitude) != 0) return false;
        if (Double.compare(lugar.longitudde, longitudde) != 0) return false;
        return id == lugar.id;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitudde);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLongitudde() {
        return longitudde;
    }

    public void setLongitudde(double longitudde) {
        this.longitudde = longitudde;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "latitude=" + latitude +
                ", longitudde=" + longitudde +
                ", id='" + id + '\'' +
                '}';
    }
}
