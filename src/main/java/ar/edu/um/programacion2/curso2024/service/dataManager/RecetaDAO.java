package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecetaDAO extends AbstractDAO<Receta> {
    private static RecetaDAO instanciaDeClase;
    private Map<Integer, Receta> recetaMap;

    private RecetaDAO() {
        this.recetaMap = new LinkedHashMap<>();
    }

    public static RecetaDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new RecetaDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public Receta findById(int id) {
        return null;
    }

    @Override
    public Map<Integer, Receta> findAll() {
        return null;
    }

    public Map<Integer, Receta> findAllByMedico(Medico medico) {
        return null;
    }

    public Map<Integer, Receta> findAllByPaciente(Paciente paciente) {
        return null;
    }

    @Override
    public void save(Receta receta) {

    }

    @Override
    public void update(Receta receta) {

    }
}
