package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObraSocialDAO extends AbstractDAO<ObraSocial> {
    private static ObraSocialDAO instanciaDeClase;
    private Map<Integer, ObraSocial> obraSocialMap;

    private ObraSocialDAO() {
        this.obraSocialMap = new LinkedHashMap<>();
        this.objectMap = this.obraSocialMap;
    }

    public static ObraSocialDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new ObraSocialDAO();
        }
        return instanciaDeClase;
    }

    public void setObraSocialMap(Map<Integer, ObraSocial> obraSocialMap) {
        this.obraSocialMap = obraSocialMap;
        this.objectMap = obraSocialMap;
    }

    @Override
    protected ObraSocial generarCopiaObjeto(ObraSocial objeto) {
        return new ObraSocial(objeto.getNombre());
    }

    @Override
    public void update(ObraSocial obraSocial) {
        ObraSocial obraSocialLocal = this.obraSocialMap.get(obraSocial.getObjectID());
        if (obraSocialLocal != null) {
            obraSocialLocal.setNombre(obraSocial.getNombre());
        } else {
            this.save(obraSocial);
        }
    }
}
