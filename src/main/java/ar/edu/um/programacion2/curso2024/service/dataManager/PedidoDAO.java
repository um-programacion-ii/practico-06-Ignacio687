package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;

import java.util.LinkedHashMap;
import java.util.Map;

public class PedidoDAO extends AbstractDAO<Pedido> {
    private static PedidoDAO instanciaDeClase;
    private Map<Integer, Pedido> pedidoMap;

    private PedidoDAO() {
        this.pedidoMap = new LinkedHashMap<>();
        this.objectMap = this.pedidoMap;
    }

    public static PedidoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new PedidoDAO();
        }
        return instanciaDeClase;
    }

    public void setPedidoMap(Map<Integer, Pedido> pedidoMap) {
        this.pedidoMap = pedidoMap;
        this.objectMap = pedidoMap;
    }

    @Override
    protected Pedido generarCopiaObjeto(Pedido objeto) {
        return new Pedido(objeto.getMedicamentos(), objeto.getEstado());
    }

    @Override
    public void update(Pedido pedido) {
        Pedido pedidoLocal = this.pedidoMap.get(pedido.getObjectID());
        if (pedidoLocal != null) {
            pedidoLocal.setMedicamentos(pedido.getMedicamentos());
            pedidoLocal.setEstado(pedido.getEstado());
        } else {
            this.save(pedido);
        }
    }
}
