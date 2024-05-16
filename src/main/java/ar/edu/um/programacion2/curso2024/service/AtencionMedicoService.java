package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.ArrayList;
import java.util.List;

public class AtencionMedicoService implements MedicalAttentionService {
    private static AtencionMedicoService instanciaDeClase;

    private AtencionMedicoService() { }

    public static AtencionMedicoService obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new AtencionMedicoService();
        }
        return instanciaDeClase;
    }

    @Override
    public Receta atenderTurno(Turno turno) throws NotEnabledToRunException {
        if (turno.getTipoDeAtencion() == null) {
            throw new NotEnabledToRunException("El turno ya fue utilizado, solicite uno nuevo.");
        }
        IoCConteinerService ioCConteinerService = IoCConteinerService.obtenerInstancia();
        Medico medico = ioCConteinerService.getMedicoDAO().findById(turno.getMedico().getObjectID());
        try {
            Receta receta = medico.atender(turno);
            turno.setTipoDeAtencion(null);
            ioCConteinerService.getTurnoDAO().update(turno);
            ioCConteinerService.getRecetaDAO().save(receta);
            return receta;
        } catch (ObjectNotFoundException | NotEnabledToRunException e) {
            turno.setTipoDeAtencion(null);
            ioCConteinerService.getTurnoDAO().update(turno);
            throw new NotEnabledToRunException("El turno es erroneo solicite uno nuevo, ERROR: "+e);
        }
    }

    @Override
    public List<Medico> verMedicos() {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getMedicoDAO().findAll().values());
    }

    @Override
    public List<Medico> verMedicos(Atencion atencion) {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getMedicoDAO().findAllByAtencion(atencion)
                .values());
    }

    @Override
    public List<Medico> verMedicos(Especialidad especialidad) {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getMedicoDAO().findAllByEspecialidad(especialidad)
                .values());
    }

    @Override
    public List<Receta> verRecetas() {
        return new ArrayList<>(IoCConteinerService.obtenerInstancia().getRecetaDAO().findAll().values());
    }

    @Override
    public <T extends Persona> List<Receta> verRecetas(T persona) {
        if (persona instanceof Paciente) {
            return new ArrayList<>(IoCConteinerService.obtenerInstancia().getRecetaDAO()
                    .findAllByPaciente((Paciente) persona).values());
        } else {
            return new ArrayList<>(IoCConteinerService.obtenerInstancia().getRecetaDAO()
                    .findAllByMedico((Medico) persona).values());
        }
    }
}
