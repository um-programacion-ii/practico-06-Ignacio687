package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PacienteDAO extends AbstractDAO<Paciente> {
    private static PacienteDAO instanciaDeClase;
    private Map<Integer, Paciente> pacienteMap;

    private PacienteDAO() {
        this.pacienteMap = new LinkedHashMap<>();
        this.objectMap = this.pacienteMap;
    }

    public static PacienteDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new PacienteDAO();
        }
        return instanciaDeClase;
    }

    public void setPacienteMap(Map<Integer, Paciente> pacienteMap) {
        this.pacienteMap = pacienteMap;
        this.objectMap = pacienteMap;
    }

    @Override
    protected Paciente generarCopiaObjeto(Paciente objeto) {
        return new Paciente(objeto.getNombre(), objeto.getEdad(), objeto.getRecetas(), objeto.getTurnos(),
                objeto.getObraSocial(), objeto.getEstado(), objeto.getCompras());
    }

    @Override
    public Paciente findById(int id) {
        Paciente paciente = this.pacienteMap.get(id);
        if (paciente != null) {
            ObraSocial obraSocial = IoCConteinerService.obtenerInstancia().getObraSocialDAO()
                    .findById(paciente.getObraSocial().getObjectID());
            if (obraSocial != null) {
                paciente.setObraSocial(obraSocial);
            }
            Map<Integer, Receta> recetaMap = IoCConteinerService.obtenerInstancia().getRecetaDAO()
                    .findAllByPaciente(paciente);
            Map<Integer, Turno> turnoMap = IoCConteinerService.obtenerInstancia().getTurnoDAO()
                    .findAllByPaciente(paciente);
            Map<Integer, Compra> compraMap = IoCConteinerService.obtenerInstancia().getCompraDAO()
                    .findAllByPaciente(paciente);
            paciente.setRecetas(new ArrayList<>(recetaMap.values()));
            paciente.setTurnos(turnoMap);
            paciente.setCompras(new ArrayList<>(compraMap.values()));
            Paciente pacienteCopia = this.generarCopiaObjeto(paciente);
            pacienteCopia.setObjectID(paciente.getObjectID());
            return pacienteCopia;
        }
        return null;
    }

    @Override
    public void update(Paciente paciente) {
        Paciente pacienteLocal = this.pacienteMap.get(paciente.getObjectID());
        if (pacienteLocal != null) {
            pacienteLocal.setNombre(paciente.getNombre());
            pacienteLocal.setEdad(paciente.getEdad());
            pacienteLocal.setRecetas(paciente.getRecetas());
            pacienteLocal.setTurnos(paciente.getTurnos());
            pacienteLocal.setObraSocial(paciente.getObraSocial());
            pacienteLocal.setEstado(paciente.getEstado());
            pacienteLocal.setCompras(paciente.getCompras());
        } else {
            this.save(paciente);
        }
    }
}
