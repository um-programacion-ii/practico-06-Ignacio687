package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.ORMObject;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDAO<T extends ORMObject> implements DAO<T> {
    protected Map<Integer, T> objectMap;

    protected abstract T generarCopiaObjeto(T objeto);

    @Override
    public T findById(int id) {
        T objeto = this.objectMap.get(id);
        if (objeto != null) {
            T objetoCopia = this.generarCopiaObjeto(objeto);
            objetoCopia.setObjectID(objeto.getObjectID());
            return objetoCopia;
        }
        return null;
    }

    @Override
    public Map<Integer, T> findAll() {
        Map<Integer, T> objetoMapCopia = new HashMap<>();
        for (T objeto: this.objectMap.values()) {
            T objetoCopia = this.findById(objeto.getObjectID());
            objetoMapCopia.put(objetoCopia.getObjectID(), objetoCopia);
        }
        return objetoMapCopia;
    }

    @Override
    public void save(T t) {
        T objetoCopia = this.generarCopiaObjeto(t);
        objetoCopia.setObjectID(t.getObjectID());
        ORMMapManager.addObject(objetoCopia, this.objectMap);
    }

    @Override
    public abstract void update(T t);

    @Override
    public void delete(T t) {
        // Desactivo el 'delete' para evitar problemas con las relaciones, directamente trabajo con estados.
    }
}
