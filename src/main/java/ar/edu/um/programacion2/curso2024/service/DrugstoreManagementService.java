package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;

import java.util.List;

public interface DrugstoreManagementService {
    Pedido iniciarPedido(List<Medicamento> medicamentos);
    void finalizarPedido(Pedido pedido) throws NotEnabledToRunException;
    List<Pedido> verPedidos();
}