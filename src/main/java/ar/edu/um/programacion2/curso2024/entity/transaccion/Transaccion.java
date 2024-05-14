package ar.edu.um.programacion2.curso2024.entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.estado.EstadoTransaccion;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Transaccion extends ORMObject {
    protected Map<Integer, Medicamento> medicamentos;
    protected EstadoTransaccion estado;

    protected Transaccion(Map<Integer, Medicamento> medicamentos, EstadoTransaccion estado) {
        super();
        this.medicamentos = medicamentos;
        this.estado = estado;
    }

    public void agregarMedicamento(Medicamento medicamento) {
        ORMMapManager.addObject(medicamento, this.medicamentos);
    }

    public void eliminarMedicamento(Medicamento medicamento) throws ObjectNotFoundException {
        ORMMapManager.delObject(medicamento, this.medicamentos);
    }
}
