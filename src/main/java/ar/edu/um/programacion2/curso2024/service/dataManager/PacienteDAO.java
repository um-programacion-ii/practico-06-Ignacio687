package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Paciente;

import java.util.LinkedHashMap;
import java.util.Map;

public class PacienteDAO extends AbstractDAO<Paciente> {
    private static PacienteDAO instanciaDeClase;
    private Map<Integer, Paciente> pacienteMap;

    private PacienteDAO() {
        this.pacienteMap = new LinkedHashMap<>();
    }

    public static PacienteDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new PacienteDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public Paciente findById(int id) {
        return null;
    }

    @Override
    public Map<Integer, Paciente> findAll() {
        return null;
    }

    @Override
    public void save(Paciente paciente) {

    }

    @Override
    public void update(Paciente paciente) {

    }
}
