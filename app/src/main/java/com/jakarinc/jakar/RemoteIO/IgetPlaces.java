package com.jakarinc.jakar.RemoteIO;

import android.content.Context;

import com.jakarinc.jakar.Domain.Lugar;

import java.util.ArrayList;


/**
 * Created by Conta Única on 04/11/2016.
 */

public interface IgetPlaces {
    public void fetchInRadius(int radius, double lat, double lon, Context c, final ArrayList<Lugar> listaObservavel, final getPlaces.callback a);
}
