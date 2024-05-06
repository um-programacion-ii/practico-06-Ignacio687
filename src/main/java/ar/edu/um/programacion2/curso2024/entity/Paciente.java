package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoPaciente;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Paciente extends Persona {
    private ObraSocial obraSocial;
    private EstadoPaciente estado;
    private List<Compra> compras;

    public void guardarCompra(Compra compra) {

    }

    public Compra sacarCompra() {
        //Recordatorio: Tiene que funcionar como una FIFO
        return null;
    }

    /**
     * Verifica si el paciente está o no ocupado para poder realizar otras tareas.
     * @return Devuelve 'true' si esta ocupado, 'false' si esta desocupado.
     */
    public boolean comprobarEstado() {
        return false;
    }
}
