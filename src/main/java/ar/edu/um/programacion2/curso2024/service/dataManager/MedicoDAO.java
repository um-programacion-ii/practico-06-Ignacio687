package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;

import java.util.LinkedHashMap;
import java.util.Map;

public class MedicoDAO extends AbstractDAO<Medico> {
    private static MedicoDAO instanciaDeClase;
    private Map<Integer, Medico> medicoMap;

    private MedicoDAO() {
        this.medicoMap = new LinkedHashMap<>();
    }

    public static MedicoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new MedicoDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public Medico findById(int id) {
        return null;
    }

    @Override
    public Map<Integer, Medico> findAll() {
        return null;
    }

    public Map<Integer, Medico> findAllByAtencion(Atencion tipoDeAtencion) {
        return null;
    }

    public Map<Integer, Medico> findAllByEspecialidad(Especialidad especialidad) {
        return null;
    }

    @Override
    public void save(Medico medico) {

    }

    @Override
    public void update(Medico medico) {

    }
}
