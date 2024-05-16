package ar.edu.um.programacion2.curso2024.entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoTransaccion;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Receta extends Transaccion {
    private Medico medico;
    private Paciente paciente;

    public Receta(Map<Integer, Medicamento> medicamentos, EstadoTransaccion estado, Medico medico, Paciente paciente) {
        super(medicamentos, estado);
        this.medico = medico;
        this.paciente = paciente;
    }
}
