package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestORMMapManager {
    private Compra compra1;
    private Compra compra2;
    private Map<Integer, Compra> ormObjectMap1;
    private Map<Integer, Compra> ormObjectMap2;

    @BeforeEach
    public void preparacion() {
        this.compra1 = mock(Compra.class);
        this.compra2 = mock(Compra.class);
        when(compra1.getObjectID()).thenReturn(0);
        when(compra2.getObjectID()).thenReturn(1);
        this.ormObjectMap1 = spy(new HashMap<>());
        this.ormObjectMap2 = spy(new LinkedHashMap<>());
    }

    @Test
    public void testAddObject() {
        ORMMapManager.addObject(this.compra1, this.ormObjectMap1);
        ORMMapManager.addObject(this.compra2, this.ormObjectMap1);
        assertEquals(2, this.ormObjectMap1.size());
        assertSame(this.ormObjectMap1.get(0), this.compra1);
        assertSame(this.ormObjectMap1.get(1), this.compra2);
        ORMMapManager.addObject(this.compra2, this.ormObjectMap2);
        ORMMapManager.addObject(this.compra1, this.ormObjectMap2);
        ORMMapManager.addObject(this.compra2, this.ormObjectMap2);
        assertEquals(2, this.ormObjectMap2.size());
        assertSame(this.ormObjectMap1.get(0), this.compra1);
        assertSame(this.ormObjectMap1.get(1), this.compra2);
        Compra compraTest = mock(Compra.class);
        when(compraTest.getObjectID()).thenReturn(0);
        ORMMapManager.addObject(compraTest, this.ormObjectMap1);
        assertEquals(2, this.ormObjectMap2.size());
        assertSame(this.ormObjectMap1.get(0), compraTest);
    }

    @Test
    public void delObject() {
        try {
            ORMMapManager.addObject(this.compra1, this.ormObjectMap1);
            ORMMapManager.addObject(this.compra2, this.ormObjectMap1);
            ORMMapManager.delObject(this.compra1, this.ormObjectMap1);
            assertEquals(1, this.ormObjectMap1.size());
            assertSame(this.ormObjectMap1.get(1), this.compra2);
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void delObject_ObjectNotFoundException() {
        ORMMapManager.addObject(this.compra1, this.ormObjectMap1);
        assertThrows(ObjectNotFoundException.class, () -> ORMMapManager.delObject(this.compra2, this.ormObjectMap1));
    }
}
