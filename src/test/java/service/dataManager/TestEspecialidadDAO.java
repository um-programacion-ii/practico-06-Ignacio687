package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.EspecialidadDAO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestEspecialidadDAO extends TestAbstractDAO<EspecialidadDAO, Especialidad> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(EspecialidadDAO.class);
        this.objectDAOSpy.setEspecialidadMap(this.objectMapSpy);
        this.objectSpy = spy(new Especialidad(null));
        this.objectSpy2 = spy(new Especialidad("Pediatra"));
    }

    @Test
    public void testUpdate() {
        Especialidad especialidadTestSpy = spy(new Especialidad("Pediatra"));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(especialidadTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(especialidadTestSpy);
        assertSame(this.objectSpy.getNombre(), especialidadTestSpy.getNombre());
    }
}
