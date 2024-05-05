package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;

import java.util.Map;

public interface ORMMapManager {
    static void addObject(ORMObject ormObject, Map<Integer, ORMObject> ormObjectMap) {

    }

    static void delObject(ORMObject ormObject, Map<Integer, ORMObject> ormObjectMap) throws ObjectNotFoundException {

    }
}
