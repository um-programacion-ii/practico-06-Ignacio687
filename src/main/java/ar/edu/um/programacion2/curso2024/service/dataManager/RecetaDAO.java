package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RecetaDAO extends AbstractDAO<Receta> {
    private static RecetaDAO instanciaDeClase;
    private Map<Integer, Receta> recetaMap;

    private RecetaDAO() {
        this.recetaMap = new LinkedHashMap<>();
        this.objectMap = this.recetaMap;
    }

    public static RecetaDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new RecetaDAO();
        }
        return instanciaDeClase;
    }

    public void setRecetaMap(Map<Integer, Receta> recetaMap) {
        this.recetaMap = recetaMap;
        this.objectMap = recetaMap;
    }

    @Override
    protected Receta generarCopiaObjeto(Receta objeto) {
        return new Receta(objeto.getMedicamentos(), objeto.getEstado(), objeto.getMedico(), objeto.getPaciente());
    }

    @Override
    public Receta findById(int id) {
        Receta receta = this.recetaMap.get(id);
        if (receta != null) {
            Paciente paciente = IoCConteinerService.obtenerInstancia().getPacienteDAO()
                    .findById(receta.getPaciente().getObjectID());
            Medico medico = IoCConteinerService.obtenerInstancia().getMedicoDAO()
                    .findById(receta.getMedico().getObjectID());
            if (paciente != null) {
                receta.setPaciente(paciente);
            }
            if (medico != null) {
                receta.setMedico(medico);
            }
            Receta recetaCopia = this.generarCopiaObjeto(receta);
            recetaCopia.setObjectID(receta.getObjectID());
            return recetaCopia;
        }
        return null;
    }

    public Map<Integer, Receta> findAllByMedico(Medico medico) {
        Map<Integer, Receta> recetaMapCopia = new HashMap<>();
        for (Receta receta: this.recetaMap.values()) {
            if (receta.getMedico().getObjectID() == medico.getObjectID()) {
                Receta recetaCopia = this.findById(receta.getObjectID());
                recetaMapCopia.put(recetaCopia.getObjectID(), recetaCopia);
            }
        }
        return recetaMapCopia;
    }

    public Map<Integer, Receta> findAllByPaciente(Paciente paciente) {
        Map<Integer, Receta> recetaMapCopia = new HashMap<>();
        for (Receta receta: this.recetaMap.values()) {
            if (receta.getPaciente().getObjectID() == paciente.getObjectID()) {
                Receta recetaCopia = this.findById(receta.getObjectID());
                recetaMapCopia.put(recetaCopia.getObjectID(), recetaCopia);
            }
        }
        return recetaMapCopia;
    }

    @Override
    public void update(Receta receta) {
        Receta recetaLocal = this.recetaMap.get(receta.getObjectID());
        if (recetaLocal != null) {
            recetaLocal.setEstado(receta.getEstado());
            recetaLocal.setMedicamentos(receta.getMedicamentos());
            recetaLocal.setPaciente(receta.getPaciente());
            recetaLocal.setMedico(receta.getMedico());
        } else {
            this.save(receta);
        }
    }
}
