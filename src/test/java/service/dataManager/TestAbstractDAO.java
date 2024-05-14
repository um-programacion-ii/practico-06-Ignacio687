package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.AbstractDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TestAbstractDAO<F extends AbstractDAO<T>, T extends ORMObject> {
    protected F objectDAOSpy;
    protected Map<Integer, T> objectMapSpy;
    protected T objectSpy;
    protected T objectSpy2;
    protected MockedStatic<IoCConteinerService> mockedStatic;
    protected ORMMapManager ormMapManagerMock;

    protected abstract void preparacionParticular(IoCConteinerService ioCConteinerServiceMock);

    @BeforeEach
    public void preparacion() {
        this.objectMapSpy = spy(new HashMap<>());
        when(objectMapSpy.get(1)).thenReturn(this.objectSpy);
        this.mockedStatic = mockStatic(IoCConteinerService.class);
        IoCConteinerService ioCConteinerServiceMock = mock(IoCConteinerService.class);
        this.mockedStatic.when(IoCConteinerService::obtenerInstancia).thenReturn(ioCConteinerServiceMock);
        this.preparacionParticular(ioCConteinerServiceMock);
    }

    @AfterEach
    public void cierre() {
        this.mockedStatic.close();
    }

    @Test
    public void testFindById() {
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        T object = this.objectDAOSpy.findById(1);
        assertNotSame(this.objectSpy, object);
        assertEquals(this.objectSpy.getObjectID(), object.getObjectID());
        T objectNull = this.objectDAOSpy.findById(5);
        assertNull(objectNull);
    }

    @Test
    public void testFindAll() {
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Collection<T> objectMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(objectMapSimulado);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Map<Integer, T> objectMap= this.objectDAOSpy.findAll();
        assertEquals(2, objectMap.size());
        assertSame(this.objectSpy, objectMap.get(1));
        assertSame(this.objectSpy2, objectMap.get(2));
    }

    @Test
    public void testSave() {
        try (MockedStatic<ORMMapManager> mockedStaticORM = mockStatic(ORMMapManager.class)) {
            Mockito.doReturn(1).when(this.objectSpy).getObjectID();
            ArgumentCaptor<ORMObject> capturadorDeArgumento = ArgumentCaptor.forClass(ORMObject.class);
            this.objectDAOSpy.save(this.objectSpy);
            mockedStaticORM.verify(() -> ORMMapManager.addObject(capturadorDeArgumento.capture(), eq(this.objectMapSpy)),
                    times(1));
            ORMObject captura = capturadorDeArgumento.getValue();
            assertNotSame(captura, this.objectSpy);
            assertEquals(captura.getObjectID(), this.objectSpy.getObjectID());
        }
    }

    @Test
    public void testUpdate_NoExisteElObjeto() {
        Mockito.doNothing().when(this.objectDAOSpy).save(any());
        this.objectDAOSpy.update(this.objectSpy);
        verify(this.objectDAOSpy, times(1)).save(this.objectSpy);
    }
}