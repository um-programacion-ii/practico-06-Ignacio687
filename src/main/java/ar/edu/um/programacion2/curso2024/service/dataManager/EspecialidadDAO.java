package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;

import java.util.LinkedHashMap;
import java.util.Map;

public class EspecialidadDAO extends AbstractDAO<Especialidad> {
    private static EspecialidadDAO instanciaDeClase;
    private Map<Integer, Especialidad> especialidadMap;

    private EspecialidadDAO() {
        this.especialidadMap = new LinkedHashMap<>();
    }

    public static EspecialidadDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new EspecialidadDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public void save(Especialidad especialidad) {

    }

    @Override
    public void update(Especialidad especialidad) {

    }
}
