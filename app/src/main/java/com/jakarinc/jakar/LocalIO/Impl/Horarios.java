package com.jakarinc.jakar.LocalIO.Impl;


import android.content.Context;
import android.content.SharedPreferences;

import com.jakarinc.jakar.Domain.Horario;
import com.jakarinc.jakar.secure.preferences.SecureFactory;

import java.util.HashSet;
import java.util.Set;

public class Horarios implements com.jakarinc.jakar.LocalIO.HorariosI {
    private SharedPreferences preferences;

    public Horarios(Context c) {

        preferences = SecureFactory.getPreferences(c, "jakarPref", "jakJunbotronKey");

    }

    @Override
    public Horario[] getAll() {
        Horario[] vetorHorarios;
        Set<String> HorariosSet = preferences.getStringSet("horarios", new HashSet<String>());
        vetorHorarios = new Horario[HorariosSet.size()];
        for (int i = 0; i < HorariosSet.size(); i++) {
            vetorHorarios[i] = new Horario((String) HorariosSet.toArray()[i]);
        }

        return vetorHorarios;
    }

    @Override
    public void addNew(Horario h) {
        Set<String> HorariosSet = preferences.getStringSet("horarios", new HashSet<String>());
        HorariosSet.add(h.ToString());
        preferences.edit().putStringSet("horarios", HorariosSet).commit();
    }

    @Override
    public void remove(Horario h) {
        Set<String> HorariosSet = preferences.getStringSet("horarios", new HashSet<String>());
        HorariosSet.remove(h.toString());
        preferences.edit().putStringSet("horarios", HorariosSet).commit();
    }
}
