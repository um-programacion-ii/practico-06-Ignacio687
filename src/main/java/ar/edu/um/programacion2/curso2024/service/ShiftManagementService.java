package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.List;

public interface ShiftManagementService {
    Turno solicitarTurno(Paciente paciente, Especialidad especialidad, Atencion tipoDeAtencion)
            throws ObjectNotFoundException;
    Turno solicitarTurno(Paciente paciente, Medico medico, Atencion tipoDeAtencion);
    Receta iniciarTurno(Turno turno) throws NotEnabledToRunException;
    List<Turno> verTurnos();
    List<Turno> verTurnos(Persona persona);
    List<Turno> verTurnos(Atencion atencion);
}
