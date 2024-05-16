package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MedicoDAO extends AbstractDAO<Medico> {
    private static MedicoDAO instanciaDeClase;
    private Map<Integer, Medico> medicoMap;

    private MedicoDAO() {
        this.medicoMap = new LinkedHashMap<>();
        this.objectMap = this.medicoMap;
    }

    public static MedicoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new MedicoDAO();
        }
        return instanciaDeClase;
    }

    public void setMedicoMap(Map<Integer, Medico> medicoMap) {
        this.medicoMap = medicoMap;
        this.objectMap = medicoMap;
    }

    @Override
    protected Medico generarCopiaObjeto(Medico objeto) {
        return new Medico(objeto.getNombre(), objeto.getEdad(), objeto.getRecetas(), objeto.getTurnos(),
                objeto.getObraSocialMap(), objeto.getEspecialidad(), objeto.getAtencionParticular());
    }

    @Override
    public Medico findById(int id) {
        Medico medico = this.medicoMap.get(id);
        if (medico != null) {
            Especialidad especialidad = IoCConteinerService.obtenerInstancia().getEspecialidadDAO()
                    .findById(medico.getEspecialidad().getObjectID());
            if (especialidad != null) {
                medico.setEspecialidad(especialidad);
            }
            Map<Integer, Receta> recetaMap = IoCConteinerService.obtenerInstancia().getRecetaDAO()
                    .findAllByMedico(medico);
            Map<Integer, Turno> turnoMap = IoCConteinerService.obtenerInstancia().getTurnoDAO()
                    .findAllByMedico(medico);
            medico.setRecetas(new ArrayList<>(recetaMap.values()));
            medico.setTurnos(turnoMap);
            Medico medicoCopia = this.generarCopiaObjeto(medico);
            medicoCopia.setObjectID(medico.getObjectID());
            return medicoCopia;
        }
        return null;
    }

    public Map<Integer, Medico> findAllByAtencion(Atencion tipoDeAtencion) {
        if (tipoDeAtencion instanceof ObraSocial) {
            return this.findAllByObraSocial((ObraSocial) tipoDeAtencion);
        } else {
            Map<Integer, Medico> medicoMapCopia = new HashMap<>();
            for (Medico medico: this.medicoMap.values()) {
                if (medico.habilitadoAtencionParticular()) {
                    Medico medicoCopia = this.findById(medico.getObjectID());
                    medicoMapCopia.put(medicoCopia.getObjectID(), medicoCopia);
                }
            }
            return medicoMapCopia;
        }
    }

    private Map<Integer, Medico> findAllByObraSocial(ObraSocial obraSocial) {
        Map<Integer, Medico> medicoMapCopia = new HashMap<>();
        for (Medico medico: this.medicoMap.values()) {
            if (medico.getObraSocialMap().get(obraSocial.getObjectID()) != null) {
                Medico medicoCopia = this.findById(medico.getObjectID());
                medicoMapCopia.put(medicoCopia.getObjectID(), medicoCopia);
            }
        }
        return medicoMapCopia;
    }

    public Map<Integer, Medico> findAllByEspecialidad(Especialidad especialidad) {
        Map<Integer, Medico> medicoMapCopia = new HashMap<>();
        for (Medico medico: this.medicoMap.values()) {
            if (medico.getEspecialidad().getObjectID() == especialidad.getObjectID()) {
                Medico medicoCopia = this.findById(medico.getObjectID());
                medicoMapCopia.put(medicoCopia.getObjectID(), medicoCopia);
            }
        }
        return medicoMapCopia;
    }

    @Override
    public void update(Medico medico) {
        Medico medicoLocal = this.medicoMap.get(medico.getObjectID());
        if (medicoLocal != null) {
            medicoLocal.setNombre(medico.getNombre());
            medicoLocal.setEdad(medico.getEdad());
            medicoLocal.setRecetas(medico.getRecetas());
            medicoLocal.setTurnos(medico.getTurnos());
            medicoLocal.setObraSocialMap(medico.getObraSocialMap());
            medicoLocal.setEspecialidad(medico.getEspecialidad());
            medicoLocal.setAtencionParticular(medico.getAtencionParticular());
        } else {
            this.save(medico);
        }
    }
}
