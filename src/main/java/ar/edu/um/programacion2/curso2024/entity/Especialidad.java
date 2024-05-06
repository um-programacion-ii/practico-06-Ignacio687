package ar.edu.um.programacion2.curso2024.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Especialidad extends ORMObject {
    private String nombre;

    public Especialidad(String nombre) {
        super();
        this.nombre = nombre;
    }

    public void tratar() {

    }
}
