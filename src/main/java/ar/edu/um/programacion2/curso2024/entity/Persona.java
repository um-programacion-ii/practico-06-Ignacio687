package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Persona extends ORMObject {
    protected String nombre;
    protected Integer edad;
    private Map<Integer, Turno> turnos;

    public void agendarTurno(Turno turno) {

    }

    public void eliminarTurno(Turno turno) throws ObjectNotFoundException {

    }
}
