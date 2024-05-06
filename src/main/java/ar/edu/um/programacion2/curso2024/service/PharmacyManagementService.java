package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.List;

public interface PharmacyManagementService {
    Compra iniciarCompra(Receta receta) throws NotEnabledToRunException;
    void finalizarCompra(Compra compra) throws NotEnabledToRunException;
    void cancelarCompra(Compra compra);
    List<Compra> verCompras();
    List<Compra> verCompras(Paciente paciente);
}
