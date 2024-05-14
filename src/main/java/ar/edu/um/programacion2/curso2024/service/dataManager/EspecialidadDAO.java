package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class EspecialidadDAO extends AbstractDAO<Especialidad> {
    private static EspecialidadDAO instanciaDeClase;
    private Map<Integer, Especialidad> especialidadMap;

    private EspecialidadDAO() {
        this.especialidadMap = new LinkedHashMap<>();
        this.objectMap = this.especialidadMap;
    }

    public static EspecialidadDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new EspecialidadDAO();
        }
        return instanciaDeClase;
    }

    public void setEspecialidadMap(Map<Integer, Especialidad> especialidadMap) {
        this.especialidadMap = especialidadMap;
        this.objectMap = especialidadMap;
    }

    @Override
    protected Especialidad generarCopiaObjeto(Especialidad objeto) {
        return new Especialidad(objeto.getNombre());
    }

    @Override
    public void update(Especialidad especialidad) {
        Especialidad especialidadLocal = this.especialidadMap.get(especialidad.getObjectID());
        if (especialidadLocal != null) {
            especialidadLocal.setNombre(especialidad.getNombre());
        } else {
            this.save(especialidad);
        }
    }
}
