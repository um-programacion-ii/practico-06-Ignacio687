package ar.edu.um.programacion2.curso2024.entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoTransaccion;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Transaccion extends ORMObject {
    private Map<Integer, Medicamento> medicamentos;
    private EstadoTransaccion estado;

    public void agregarMedicamento(Medicamento medicamento) {
        // Recordatorio: Usar ORMMapManager
    }

    public void eliminarMedicamento(Medicamento medicamento) throws ObjectNotFoundException {
        // Recordatorio: Usar ORMMapManager
    }
}
