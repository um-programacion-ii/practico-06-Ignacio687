package entity;

import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class TestORMObject<T extends ORMObject> {

    protected abstract T crearInstancia();

    @Test
    public void verificarObjectIDUnico() {
        T ormObject = this.crearInstancia();
        for (int counter = 0; counter < 10; counter++) {
            T ormObject1 = this.crearInstancia();
            T ormObject2 = this.crearInstancia();
            assertNotEquals(ormObject.getObjectID(), ormObject1.getObjectID());
            assertNotEquals(ormObject.getObjectID(), ormObject2.getObjectID());
        }
    }
}
