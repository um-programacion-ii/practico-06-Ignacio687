package ar.edu.um.programacion2.curso2024.entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoTransaccion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Transaccion extends ORMObject {
    private Map<Integer, Medicamento> medicamentos;
    private EstadoTransaccion estado;

    protected Transaccion(Map<Integer, Medicamento> medicamentos, EstadoTransaccion estado) {
        super();
        this.medicamentos = medicamentos;
        this.estado = estado;
    }

    public void agregarMedicamento(Medicamento medicamento) {
        // Recordatorio: Usar ORMMapManager
    }

    public void eliminarMedicamento(Medicamento medicamento) throws ObjectNotFoundException {
        // Recordatorio: Usar ORMMapManager
    }
}
