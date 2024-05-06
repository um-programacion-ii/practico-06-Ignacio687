package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Persona;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;

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
    public Receta atenderTurno(Turno turno) {
        return null;
    }

    @Override
    public List<Medico> verMedicos() {
        return null;
    }

    @Override
    public List<Medico> verMedicos(Atencion atencion) {
        return null;
    }

    @Override
    public List<Medico> verMedicos(Especialidad especialidad) {
        return null;
    }

    @Override
    public List<Receta> verRecetas() {
        return null;
    }

    @Override
    public List<Receta> verRecetas(Persona persona) {
        return null;
    }
}
