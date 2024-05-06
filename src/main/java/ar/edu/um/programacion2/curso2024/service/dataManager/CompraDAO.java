package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;

import java.util.LinkedHashMap;
import java.util.Map;

public class CompraDAO extends AbstractDAO<Compra> {
    private static CompraDAO instanciaDeClase;
    private Map<Integer, Compra> compraMap;

    private CompraDAO() {
        this.compraMap = new LinkedHashMap<>();
    }

    public static CompraDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new CompraDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public Compra findById(int id) {
        return null;
    }

    @Override
    public Map<Integer, Compra> findAll() {
        return null;
    }

    public Map<Integer, Compra> findAllByPaciente(Paciente paciente) {
        return null;
    }

    @Override
    public void save(Compra compra) {

    }

    @Override
    public void update(Compra compra) {

    }
}
