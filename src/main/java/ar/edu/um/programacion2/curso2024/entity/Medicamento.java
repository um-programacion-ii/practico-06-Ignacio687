package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnoughStockException;
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
        this.cantidad += cantidad;
    }

    public void sacar(int cantidad) throws NotEnoughStockException {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
        } else {
            throw new NotEnoughStockException("No hay "+cantidad+" unidades de "+this.nombre);
        }
    }

    /**
     * Verifica si existe la cantidad de unidades solicitadas.
     * @return Devuelve 'true' si existen, 'false' si no.
     */
    public boolean verificarStock(int cantidad) {
        return this.cantidad >= cantidad;
    }
}
