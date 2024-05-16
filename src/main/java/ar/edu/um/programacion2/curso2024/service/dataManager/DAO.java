package ar.edu.um.programacion2.curso2024.service.dataManager;

import java.util.Map;

public interface DAO <T> {
    T findById(int id);
    Map<Integer, T> findAll();
    void save(T t);
    void update(T t);
    void delete(T t);
}
