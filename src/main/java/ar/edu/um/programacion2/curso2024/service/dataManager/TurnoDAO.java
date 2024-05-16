package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TurnoDAO extends AbstractDAO<Turno> {
    private static TurnoDAO instanciaDeClase;
    private Map<Integer, Turno> turnoMap;

    private TurnoDAO() {
        this.turnoMap = new LinkedHashMap<>();
        this.objectMap = this.turnoMap;
    }

    public static TurnoDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new TurnoDAO();
        }
        return instanciaDeClase;
    }

    public void setTurnoMap(Map<Integer, Turno> turnoMap) {
        this.turnoMap = turnoMap;
        this.objectMap = turnoMap;
    }

    @Override
    protected Turno generarCopiaObjeto(Turno objeto) {
        return new Turno(objeto.getMedico(), objeto.getPaciente(), objeto.getTipoDeAtencion());
    }

    @Override
    public Turno findById(int id) {
        Turno turno = this.turnoMap.get(id);
        if (turno != null) {
            Paciente paciente = IoCConteinerService.obtenerInstancia().getPacienteDAO()
                    .findById(turno.getPaciente().getObjectID());
            Medico medico = IoCConteinerService.obtenerInstancia().getMedicoDAO()
                    .findById(turno.getMedico().getObjectID());
            if (paciente != null) { turno.setPaciente(paciente); }
            if (medico != null) { turno.setMedico(medico); }
            if (turno.getTipoDeAtencion() instanceof ObraSocial) {
                ObraSocial obraSocial = IoCConteinerService.obtenerInstancia().getObraSocialDAO()
                        .findById(turno.getTipoDeAtencion().getObjectID());
                if (obraSocial != null) { turno.setTipoDeAtencion(obraSocial); }
            }
            Turno turnoCopia = this.generarCopiaObjeto(turno);
            turnoCopia.setObjectID(turno.getObjectID());
            return turnoCopia;
        }
        return null;
    }

    public Map<Integer, Turno> findAllByMedico(Medico medico) {
        Map<Integer, Turno> turnoMapCopia = new HashMap<>();
        for (Turno turno: this.turnoMap.values()) {
            if (turno.getMedico().getObjectID() == medico.getObjectID()) {
                Turno turnoCopia = this.findById(turno.getObjectID());
                turnoMapCopia.put(turnoCopia.getObjectID(), turnoCopia);
            }
        }
        return turnoMapCopia;
    }

    public Map<Integer, Turno> findAllByPaciente(Paciente paciente) {
        Map<Integer, Turno> turnoMapCopia = new HashMap<>();
        for (Turno turno: this.turnoMap.values()) {
            if (turno.getPaciente().getObjectID() == paciente.getObjectID()) {
                Turno turnoCopia = this.findById(turno.getObjectID());
                turnoMapCopia.put(turnoCopia.getObjectID(), turnoCopia);
            }
        }
        return turnoMapCopia;
    }

    public Map<Integer, Turno> findAllByAtencion(Atencion tipoDeAtencion) {
        if (tipoDeAtencion instanceof ObraSocial) {
            return this.findAllByAtencion(tipoDeAtencion);
        } else {
            Map<Integer, Turno> turnoMapCopia = new HashMap<>();
            for (Turno turno: this.turnoMap.values()) {
                if (turno.getTipoDeAtencion() instanceof AtencionParticular) {
                    Turno turnoCopia = this.findById(turno.getObjectID());
                    turnoMapCopia.put(turnoCopia.getObjectID(), turnoCopia);
                }
            }
            return turnoMapCopia;
        }
    }
    private Map<Integer, Turno> findAllByAtencion(ObraSocial obraSocial) {
        Map<Integer, Turno> turnoMapCopia = new HashMap<>();
        for (Turno turno: this.turnoMap.values()) {
            if (turno.getTipoDeAtencion().getObjectID() == obraSocial.getObjectID()) {
                Turno turnoCopia = this.findById(turno.getObjectID());
                turnoMapCopia.put(turnoCopia.getObjectID(), turnoCopia);
            }
        }
        return turnoMapCopia;
    }

    @Override
    public void update(Turno turno) {
        Turno turnoLocal = this.turnoMap.get(turno.getObjectID());
        if (turnoLocal != null) {
            turnoLocal.setPaciente(turno.getPaciente());
            turnoLocal.setMedico(turno.getMedico());
            turnoLocal.setTipoDeAtencion(turno.getTipoDeAtencion());
        } else {
            this.save(turno);
        }
    }
}
