package ar.edu.um.programacion2.curso2024.service.dataManager;

import java.util.Map;

public abstract class AbstractDAO<T> implements DAO<T> {

    @Override
    public T findById(int id) {
        return null;
    }

    @Override
    public Map<Integer, T> findAll() {
        return null;
    }

    @Override
    public abstract void save(T t);

    @Override
    public abstract void update(T t);

    @Override
    public void delete(T t) {
        // Desactivo el 'delete' para evitar problemas con las relaciones, directamente trabajo con estados.
    }
}
