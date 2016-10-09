package com.jakarinc.jakar.LocalIO;

import com.jakarinc.jakar.Domain.Horario;


public interface HorariosI {
    Horario[] getAll();

    void addNew(Horario h);

    void remove(Horario h);
}
