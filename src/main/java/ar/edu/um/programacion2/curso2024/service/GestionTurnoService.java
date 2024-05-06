package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.List;

public class GestionTurnoService implements ShiftManagementService {
    private static GestionTurnoService instanciaDeClase;

    private GestionTurnoService() { }

    public static GestionTurnoService obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new GestionTurnoService();
        }
        return instanciaDeClase;
    }

    @Override
    public Turno solicitarTurno(Paciente paciente, Especialidad especialidad, Atencion tipoDeAtencion) {
        return null;
    }

    @Override
    public Turno solicitarTurno(Paciente paciente, Medico medico, Atencion tipoDeAtencion) {
        return null;
    }

    @Override
    public Receta iniciarTurno(Turno turno) throws NotEnabledToRunException {
        return null;
    }

    @Override
    public void cancelarTurno(Turno turno) {

    }

    @Override
    public List<Turno> verTurnos() {
        return null;
    }

    @Override
    public List<Turno> verTurnos(Persona persona) {
        return null;
    }

    @Override
    public List<Turno> verTurnos(Atencion atencion) {
        return null;
    }
}
