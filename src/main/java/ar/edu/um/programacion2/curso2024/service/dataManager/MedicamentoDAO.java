package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;

import java.util.LinkedHashMap;
import java.util.Map;

public class MedicamentoDAO extends AbstractDAO<Medicamento> {
    private static MedicamentoDAO instanciaDeClase;
    private Map<Integer, Medicamento> medicamentoMap;

    private MedicamentoDAO() {
        this.medicamentoMap = new LinkedHashMap<>();
    }

    public static MedicamentoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new MedicamentoDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public void save(Medicamento medicamento) {

    }

    @Override
    public void update(Medicamento medicamento) {

    }
}
