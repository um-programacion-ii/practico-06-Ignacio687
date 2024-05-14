package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;

import java.util.LinkedHashMap;
import java.util.Map;

public class MedicamentoDAO extends AbstractDAO<Medicamento> {
    private static MedicamentoDAO instanciaDeClase;
    private Map<Integer, Medicamento> medicamentoMap;

    private MedicamentoDAO() {
        this.medicamentoMap = new LinkedHashMap<>();
        this.objectMap = this.medicamentoMap;
    }

    public static MedicamentoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new MedicamentoDAO();
        }
        return instanciaDeClase;
    }

    public void setMedicamentoMap(Map<Integer, Medicamento> medicamentoMap) {
        this.medicamentoMap = medicamentoMap;
        this.objectMap = medicamentoMap;
    }

    @Override
    protected Medicamento generarCopiaObjeto(Medicamento objeto) {
        return new Medicamento(objeto.getNombre(), objeto.getCantidad(), objeto.getPrecio());
    }

    @Override
    public void update(Medicamento medicamento) {
        Medicamento medicamentoLocal = this.medicamentoMap.get(medicamento.getObjectID());
        if (medicamentoLocal != null) {
            medicamentoLocal.setNombre(medicamento.getNombre());
            medicamentoLocal.setCantidad(medicamento.getCantidad());
            medicamentoLocal.setPrecio(medicamento.getPrecio());
        } else {
            this.save(medicamento);
        }
    }
}
