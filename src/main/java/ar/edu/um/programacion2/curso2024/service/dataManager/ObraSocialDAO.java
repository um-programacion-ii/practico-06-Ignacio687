package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;

import java.util.LinkedHashMap;
import java.util.Map;

public class ObraSocialDAO extends AbstractDAO<ObraSocial> {
    private static ObraSocialDAO instanciaDeClase;
    private Map<Integer, ObraSocial> obraSocialMap;

    private ObraSocialDAO() {
        this.obraSocialMap = new LinkedHashMap<>();
    }

    public static ObraSocialDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new ObraSocialDAO();
        }
        return instanciaDeClase;
    }

    @Override
    public void save(ObraSocial obraSocial) {

    }

    @Override
    public void update(ObraSocial obraSocial) {

    }
}
