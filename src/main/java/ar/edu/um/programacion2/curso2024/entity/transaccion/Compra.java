package ar.edu.um.programacion2.curso2024.entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Compra extends Transaccion {
    private Paciente paciente;

    public Compra(Map<Integer, Medicamento> medicamentos, EstadoTransaccion estado, Paciente paciente) {
        super(medicamentos, estado);
        this.paciente = paciente;
    }
}
