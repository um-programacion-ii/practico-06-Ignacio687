package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;

import java.util.LinkedHashMap;
import java.util.Map;

public class PedidoDAO extends AbstractDAO<Pedido> {
    private static PedidoDAO instanciaDeClase;
    private Map<Integer, Pedido> pedidoMap;

    private PedidoDAO() {
        this.pedidoMap = new LinkedHashMap<>();
    }

    public static PedidoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new PedidoDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public Pedido findById(int id) {
        return null;
    }

    @Override
    public Map<Integer, Pedido> findAll() {
        return null;
    }

    @Override
    public void save(Pedido pedido) {

    }

    @Override
    public void update(Pedido pedido) {

    }
}
