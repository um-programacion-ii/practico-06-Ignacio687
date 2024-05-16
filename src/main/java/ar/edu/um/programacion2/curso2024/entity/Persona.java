package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Persona extends ORMObject {
    protected String nombre;
    protected Integer edad;
    protected List<Receta> recetas;
    protected Map<Integer, Turno> turnos;

    protected Persona(String nombre, Integer edad, List<Receta> recetas, Map<Integer, Turno> turnos) {
        super();
        this.nombre = nombre;
        this.edad = edad;
        this.recetas = recetas;
        this.turnos = turnos;
    }

    public void guardarReceta(Receta receta) {
        this.recetas.add(receta);
    }

    public Receta sacarReceta() {
        try {
            Receta receta = this.recetas.getFirst();
            this.recetas.removeFirst();
            return receta;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void agendarTurno(Turno turno) {
        ORMMapManager.addObject(turno, this.turnos);
    }

    public void eliminarTurno(Turno turno) throws ObjectNotFoundException {
        ORMMapManager.delObject(turno, this.turnos);
    }
}
