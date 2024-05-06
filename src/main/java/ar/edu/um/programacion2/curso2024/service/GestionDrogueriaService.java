package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;

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
        return null;
    }

    @Override
    public void finalizarPedido(Pedido pedido) {

    }

    @Override
    public void cancelarPedido(Pedido pedido) {

    }

    @Override
    public List<Pedido> verPedidos() {
        return null;
    }
}
