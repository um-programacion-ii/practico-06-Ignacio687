package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Paciente;

import java.util.Map;

public class PacienteDAO implements DAO<Paciente> {
    Map<Integer, Paciente> pacientes;

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

    @Override
    public void delete(Paciente paciente) {

    }
}
