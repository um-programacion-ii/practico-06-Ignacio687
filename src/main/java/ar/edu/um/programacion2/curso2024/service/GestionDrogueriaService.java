package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestionDrogueriaService implements DrugstoreManagementService {
    private static GestionDrogueriaService instanciaDeClase;

    private GestionDrogueriaService() { }

    public static GestionDrogueriaService obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new GestionDrogueriaService();
        }
        return instanciaDeClase;
    }

    @Override
    public Pedido iniciarPedido(List<Medicamento> medicamentos) {
        Pedido pedido = new Pedido(new HashMap<>(), IoCConteinerService.obtenerInstancia().getEstadoIniciado());
        for (Medicamento medicamento: medicamentos) {
            Medicamento medicamentoCopia = new Medicamento(medicamento.getNombre(), medicamento.getCantidad(),
                    medicamento.getPrecio());
            medicamentoCopia.setObjectID(medicamento.getObjectID());
            pedido.agregarMedicamento(medicamentoCopia);
        }
        return pedido;
    }

    @Override
    public void finalizarPedido(Pedido pedido) throws NotEnabledToRunException {
        IoCConteinerService ioCConteinerService = IoCConteinerService.obtenerInstancia();
        Pedido pedidoLocal = ioCConteinerService.getPedidoDAO().findById(pedido.getObjectID());
        if (pedidoLocal.getEstado() instanceof Finalizado) {
            throw new NotEnabledToRunException("El pedido ID: " + pedido.getObjectID() + " ya fue concretado");
        }
        for (Medicamento medicamento: pedidoLocal.getMedicamentos().values()) {
            Medicamento medicamentoLocal = ioCConteinerService.getMedicamentoDAO().findById(medicamento.getObjectID());
            medicamentoLocal.agregar(medicamento.getCantidad());
            ioCConteinerService.getMedicamentoDAO().update(medicamentoLocal);
        }
        pedidoLocal.setEstado(ioCConteinerService.getEstadoFinalizado());
        ioCConteinerService.getPedidoDAO().update(pedidoLocal);
    }

    @Override
    public List<Pedido> verPedidos() {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getPedidoDAO().findAll().values());
    }
}
