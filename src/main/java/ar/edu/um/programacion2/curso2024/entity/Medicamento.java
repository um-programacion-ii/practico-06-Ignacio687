package ar.edu.um.programacion2.curso2024.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medicamento extends ORMObject {
    private String nombre;
    private Integer cantidad;
    private Integer precio;

    public Medicamento(String nombre, Integer cantidad, Integer precio) {
        super();
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public void agregar(int cantidad) {

    }

    public void sacar(int cantidad) {

    }
    
}
