package entity;

import ar.edu.um.programacion2.curso2024.entity.Turno;

public class TestTurno extends TestORMObject<Turno> {
    @Override
    protected Turno crearInstancia() {
        return new Turno();
    }
}
