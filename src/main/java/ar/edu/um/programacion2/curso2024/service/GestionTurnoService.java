package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public Turno solicitarTurno(Paciente paciente, Especialidad especialidad, Atencion tipoDeAtencion)
            throws ObjectNotFoundException {
        Turno turno = new Turno(null, paciente, tipoDeAtencion);
        Random random = new Random();
        List<Medico> medicoEspecialidadList = new ArrayList<>(IoCConteinerService.obtenerInstancia().getMedicoDAO()
                .findAllByEspecialidad(especialidad).values());
        if (medicoEspecialidadList.isEmpty()) {
            throw new ObjectNotFoundException("No hay medicos de la especialidad "+especialidad.getNombre()+".");
        }
        List<Medico> medicoTipoDeAtencionList = new ArrayList<>(IoCConteinerService.obtenerInstancia().getMedicoDAO()
                .findAllByAtencion(tipoDeAtencion).values());
        List<Medico> medicoInterseccionList = medicoEspecialidadList.stream()
                .filter(obj -> medicoTipoDeAtencionList.stream()
                        .anyMatch(obj2 -> obj2.getObjectID() == obj.getObjectID())).toList();
        if (medicoInterseccionList.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            if (tipoDeAtencion instanceof AtencionParticular) {
                errorMessage.append("No hay medicos que atiendan de forma particular.");
            } else {
                errorMessage.append("No hay medicos que atiendan por ")
                        .append(((ObraSocial) tipoDeAtencion).getNombre());
            }
            throw new ObjectNotFoundException(errorMessage.toString());
        }
        turno.setMedico(medicoInterseccionList.get(random.nextInt(medicoInterseccionList.size())));
        IoCConteinerService.obtenerInstancia().getTurnoDAO().save(turno);
        return turno;
    }

    @Override
    public Turno solicitarTurno(Paciente paciente, Medico medico, Atencion tipoDeAtencion) {
        Turno turno = new Turno(medico, paciente, tipoDeAtencion);
        IoCConteinerService.obtenerInstancia().getTurnoDAO().save(turno);
        return turno;
    }

    @Override
    public Receta iniciarTurno(Turno turno) throws NotEnabledToRunException {
        IoCConteinerService ioCConteinerService = IoCConteinerService.obtenerInstancia();
        Paciente paciente = turno.getPaciente();
        if (paciente.comprobarEstado()) {
            throw new NotEnabledToRunException("El paciente ID: "+paciente.getObjectID()+" esta ocupado" +
                    " en este momento, intente mas tarde.");
        }
        paciente.setEstado(ioCConteinerService.getEstadoOcupado());
        ioCConteinerService.getPacienteDAO().update(paciente);
        Receta receta = ioCConteinerService.getAtencionMedicoService().atenderTurno(turno);
        paciente.setEstado(ioCConteinerService.getEstadoLibre());
        ioCConteinerService.getPacienteDAO().update(paciente);
        return receta;
    }

    @Override
    public List<Turno> verTurnos() {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getTurnoDAO().findAll().values());
    }

    @Override
    public List<Turno> verTurnos(Persona persona) {
        if (persona instanceof Paciente) {
            return new ArrayList<>(IoCConteinerService.obtenerInstancia().getTurnoDAO()
                    .findAllByPaciente((Paciente) persona).values());
        } else {
            return new ArrayList<>(IoCConteinerService.obtenerInstancia().getTurnoDAO()
                    .findAllByMedico((Medico) persona).values());
        }
    }

    @Override
    public List<Turno> verTurnos(Atencion atencion) {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getTurnoDAO().findAllByAtencion(atencion)
                .values());
    }
}
