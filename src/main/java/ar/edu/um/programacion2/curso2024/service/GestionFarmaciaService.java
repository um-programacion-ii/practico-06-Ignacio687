package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

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
    public Compra iniciarCompra(Receta receta) throws NotEnabledToRunException {
        return null;
    }

    @Override
    public void finalizarCompra(Compra compra) throws NotEnabledToRunException {

    }

    @Override
    public List<Compra> verCompras() {
        return null;
    }

    @Override
    public List<Compra> verCompras(Paciente paciente) {
        return null;
    }
}
