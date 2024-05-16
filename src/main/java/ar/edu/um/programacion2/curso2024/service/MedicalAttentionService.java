package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Persona;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.List;

public interface MedicalAttentionService {
    Receta atenderTurno(Turno turno) throws NotEnabledToRunException, ObjectNotFoundException;
    List<Medico> verMedicos();
    List<Medico> verMedicos(Atencion atencion);
    List<Medico> verMedicos(Especialidad especialidad);
    List<Receta> verRecetas();
    public <T extends Persona> List<Receta> verRecetas(T persona);
}
