package ar.edu.um.programacion2.curso2024.entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoTransaccion;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pedido extends Transaccion {

    public Pedido(Map<Integer, Medicamento> medicamentos, EstadoTransaccion estado) {
        super(medicamentos, estado);
    }
}
