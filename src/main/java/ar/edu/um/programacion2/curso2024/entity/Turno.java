package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Turno extends ORMObject {
    private Medico medico;
    private Paciente paciente;
    private Atencion tipoDeAtencion;

    public Turno(Medico medico, Paciente paciente, Atencion tipoDeAtencion) {
        super();
        this.medico = medico;
        this.paciente = paciente;
        this.tipoDeAtencion = tipoDeAtencion;
    }
}
