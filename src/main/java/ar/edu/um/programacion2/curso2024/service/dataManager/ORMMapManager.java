package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;

import java.util.Map;

public class ORMMapManager {
    private static ORMMapManager instanciaDeClase;

    private ORMMapManager() {}

    public static ORMMapManager obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new ORMMapManager();
        }
        return instanciaDeClase;
    }

    public static void addObject(ORMObject ormObject, Map<Integer, ? extends ORMObject> ormObjectMap) {

    }

    public static void delObject(ORMObject ormObject, Map<Integer, ? extends ORMObject> ormObjectMap) throws ObjectNotFoundException {

    }
}
