package entity;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import org.junit.jupiter.api.Test;

public class TestEspecialidad extends TestORMObject<Especialidad>{

    @Override
    protected Especialidad crearInstancia() {
        return new Especialidad();
    }

    @Test
    public void testTratar() {
        Especialidad especialidad = new Especialidad("Cirujano");
        especialidad.tratar();
    }
}
