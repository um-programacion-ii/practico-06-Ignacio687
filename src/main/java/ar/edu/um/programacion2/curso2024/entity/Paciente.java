package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoPaciente;
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
    private List<Receta> recetas;

    public void guardarReceta(Receta receta) {

    }

    public Receta sacarReceta() {
        return null;
    }

}
