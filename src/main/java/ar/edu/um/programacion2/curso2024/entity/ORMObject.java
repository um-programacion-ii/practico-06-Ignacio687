package ar.edu.um.programacion2.curso2024.entity;

import lombok.Data;

@Data
public abstract class ORMObject {
    protected int objectID;

    protected ORMObject() {
        this.objectID = java.util.UUID.randomUUID().hashCode();
    }
}
