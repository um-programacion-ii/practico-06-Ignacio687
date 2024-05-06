package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Persona extends ORMObject {
    protected String nombre;
    protected Integer edad;
    private List<Receta> recetas;
    private Map<Integer, Turno> turnos;

    protected Persona(String nombre, Integer edad, List<Receta> recetas, Map<Integer, Turno> turnos) {
        super();
        this.nombre = nombre;
        this.edad = edad;
        this.recetas = recetas;
        this.turnos = turnos;
    }

    public void guardarReceta(Receta receta) {

    }

    public Receta sacarReceta() {
        //Recordatorio: Tiene que funcionar como una FIFO
        return null;
    }

    public void agendarTurno(Turno turno) {

    }

    public void eliminarTurno(Turno turno) throws ObjectNotFoundException {

    }
}
