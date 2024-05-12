package entity;


import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Persona;

import java.util.ArrayList;

public class TestMedico extends TestPersona {

    @Override
    protected Persona crearInstancia() {
        return new Medico("Franco", 45, this.recetasSpy, this.turnoMap, new ArrayList<>(),
                new Especialidad(), null);
    }
}
