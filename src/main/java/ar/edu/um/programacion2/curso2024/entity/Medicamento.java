package ar.edu.um.programacion2.curso2024.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medicamento extends ORMObject {
    private String nombre;
    private Integer cantidad;
    private Integer precio;

    public void agregar(int cantidad) {

    }

    public void sacar(int cantidad) {

    }
    
}
