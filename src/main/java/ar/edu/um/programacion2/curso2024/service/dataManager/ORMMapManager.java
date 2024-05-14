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

    public static <T extends ORMObject> void addObject(T ormObject, Map<Integer, T> ormObjectMap) {
        ormObjectMap.put(ormObject.getObjectID(), ormObject);
    }

    public static <T extends ORMObject> void delObject(T ormObject, Map<Integer, T> ormObjectMap)
            throws ObjectNotFoundException {
        if (ormObjectMap.remove(ormObject.getObjectID()) == null) {
            throw new ObjectNotFoundException("El objeto "+ormObject.getObjectID()+" no se encuentra en el mapa");
        }
    }
}
