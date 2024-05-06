package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Persona;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

import java.util.List;

public interface MedicalAttentionService {
    Receta atenderTurno(Turno turno);
    List<Medico> verMedicos();
    List<Medico> verMedicos(Atencion atencion);
    List<Medico> verMedicos(Especialidad especialidad);
    List<Receta> verRecetas();
    List<Receta> verRecetas(Persona persona);
}
