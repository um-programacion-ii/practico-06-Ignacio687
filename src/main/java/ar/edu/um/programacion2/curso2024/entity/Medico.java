package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medico extends Persona {
    private Set<ObraSocial> obraSocialSet;
    private Especialidad especialidad;
    private AtencionParticular atencionParticular;

    public Receta atender(Turno turno) {
        return null;
    }

    public Receta atenderParticular(Turno turno) throws NotEnabledToRunException {
        return null;
    }

    public boolean habilitadoAtencionParticular() {
        return false;
    }

    private Receta generarReceta() {
        return null;
    }
}
