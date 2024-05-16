package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnoughStockException;
import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestionFarmaciaService implements PharmacyManagementService{
    private static GestionFarmaciaService instanciaDeClase;

    private GestionFarmaciaService() { }

    public static GestionFarmaciaService obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new GestionFarmaciaService();
        }
        return instanciaDeClase;
    }


    @Override
    public List<Medicamento> verificarStock(List<Medicamento> medicamentos) {
        List<Medicamento> medicamentosFaltantes = new ArrayList<>();
        for (Medicamento medicamento: medicamentos) {
            Medicamento medicamentoLocal = IoCConteinerService.obtenerInstancia().getMedicamentoDAO()
                    .findById(medicamento.getObjectID());
            if (!medicamentoLocal.verificarStock(medicamento.getCantidad())) {
                medicamentosFaltantes.add(medicamento);
            }
        }
        if (medicamentosFaltantes.isEmpty()) {
            return null;
        } else {
            return medicamentosFaltantes;
        }
    }

    @Override
    public void realizarPedido(List<Medicamento> medicamentos) {
        List<Medicamento> pedidoList = new ArrayList<>();
        for (Medicamento medicamento: medicamentos) {
            Medicamento medicamentoPedido = new Medicamento(medicamento.getNombre(), (medicamento.getCantidad()*2),
                    medicamento.getPrecio());
            medicamentoPedido.setObjectID(medicamento.getObjectID());
            pedidoList.add(medicamentoPedido);
        }
        Pedido pedido = IoCConteinerService.obtenerInstancia().getGestionDrogueriaService().iniciarPedido(pedidoList);
        try {
            IoCConteinerService.obtenerInstancia().getGestionDrogueriaService().finalizarPedido(pedido);
        } catch (NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Compra iniciarCompra(Receta receta) throws NotEnabledToRunException {
        IoCConteinerService ioCConteinerService = IoCConteinerService.obtenerInstancia();
        Paciente paciente = receta.getPaciente();
        if (paciente.comprobarEstado()) {
            throw new NotEnabledToRunException("El paciente ID: "+paciente.getObjectID()+" esta ocupado en este " +
                    "momento, intente mas tarde.");
        } else if (receta.getEstado() instanceof Finalizado) {
            throw new NotEnabledToRunException("La receta ID: "+receta+" ya fue utilizada, solicite una nueva.");
        }
        Compra compra = new Compra(new HashMap<>(), ioCConteinerService.getEstadoIniciado(), paciente);
        for (Medicamento medicamento: receta.getMedicamentos().values()) {
            Medicamento medicamentoCopia = new Medicamento(medicamento.getNombre(), medicamento.getCantidad(),
                    medicamento.getPrecio());
            medicamentoCopia.setObjectID(medicamento.getObjectID());
            compra.agregarMedicamento(medicamentoCopia);
        }
        receta.setEstado(ioCConteinerService.getEstadoFinalizado());
        ioCConteinerService.getRecetaDAO().update(receta);
        return compra;
    }

    @Override
    public void finalizarCompra(Compra compra) throws NotEnabledToRunException {
        IoCConteinerService ioCConteinerService = IoCConteinerService.obtenerInstancia();
        Paciente paciente = compra.getPaciente();
        if (paciente.comprobarEstado()) {
            throw new NotEnabledToRunException("El paciente ID: "+paciente.getObjectID()+" esta ocupado en este " +
                    "momento, intente mas tarde.");
        } else if (compra.getEstado() instanceof Finalizado) {
            throw new NotEnabledToRunException("La compra ID: "+compra+" ya fue concretada.");
        }
        List<Medicamento> medicamentosFaltantes = this.verificarStock(new ArrayList<>(compra.getMedicamentos()
                .values()));
        if (medicamentosFaltantes != null) { this.realizarPedido(medicamentosFaltantes); }
        for (Medicamento medicamento: compra.getMedicamentos().values()) {
            Medicamento medicamentoLocal = ioCConteinerService.getMedicamentoDAO().findById(medicamento.getObjectID());
            try {
                medicamentoLocal.sacar(medicamento.getCantidad());
                ioCConteinerService.getMedicamentoDAO().update(medicamentoLocal);
            } catch (NotEnoughStockException e) {
                throw new RuntimeException(e);
            }
        }
        compra.setEstado(ioCConteinerService.getEstadoFinalizado());
        ioCConteinerService.getCompraDAO().update(compra);
    }

    @Override
    public List<Compra> verCompras() {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getCompraDAO().findAll().values());
    }

    @Override
    public List<Compra> verCompras(Paciente paciente) {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getCompraDAO().findAllByPaciente(paciente)
                .values());
    }
}
