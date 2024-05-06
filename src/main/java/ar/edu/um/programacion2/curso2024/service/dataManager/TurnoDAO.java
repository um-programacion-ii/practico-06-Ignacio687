package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.LinkedHashMap;
import java.util.Map;

public class TurnoDAO extends AbstractDAO<Turno> {
    private static TurnoDAO instanciaDeClase;
    private Map<Integer, Turno> turnoMap;

    private TurnoDAO() {
        this.turnoMap = new LinkedHashMap<>();
    }

    public static TurnoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new TurnoDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public Turno findById(int id) {
        return null;
    }

    @Override
    public Map<Integer, Turno> findAll() {
        return null;
    }

    public Map<Integer, Turno> findAllByMedico(Medico medico) {
        return null;
    }

    public Map<Integer, Turno> findAllByPaciente(Paciente paciente) {
        return null;
    }

    public Map<Integer, Turno> findAllByAtencion(Atencion tipoDeAtencion) {
        return null;
    }

    @Override
    public void save(Turno turno) {

    }

    @Override
    public void update(Turno turno) {

    }

    @Override
    public void delete(Turno turno) {

    }
}
