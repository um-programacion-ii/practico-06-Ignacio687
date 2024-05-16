package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.EspecialidadDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicoDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.RecetaDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.TurnoDAO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestMedicoDAO extends TestAbstractDAO<MedicoDAO, Medico> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(MedicoDAO.class);
        this.objectDAOSpy.setMedicoMap(this.objectMapSpy);
        RecetaDAO recetaDAOMock = mock(RecetaDAO.class);
        when(ioCConteinerServiceMock.getRecetaDAO()).thenReturn(recetaDAOMock);
        TurnoDAO turnoDAOMock = mock(TurnoDAO.class);
        when(ioCConteinerServiceMock.getTurnoDAO()).thenReturn(turnoDAOMock);
        Especialidad especialidad = mock(Especialidad.class);
        when(especialidad.getObjectID()).thenReturn(1);
        EspecialidadDAO especialidadDAOMock = mock(EspecialidadDAO.class);
        when(ioCConteinerServiceMock.getEspecialidadDAO()).thenReturn(especialidadDAOMock);
        when(especialidadDAOMock.findById(1)).thenReturn(especialidad);
        this.objectSpy = spy(new Medico("Juan", 20, new ArrayList<>(), new HashMap<>(), new HashMap<>(),
                especialidad, mock(AtencionParticular.class)));
        this.objectSpy2 = spy(new Medico("Pepe", 45, new ArrayList<>(), new HashMap<>(), new HashMap<>(),
                especialidad, mock(AtencionParticular.class)));
        when(turnoDAOMock.findAllByMedico(this.objectSpy)).thenReturn(new HashMap<>());
        when(recetaDAOMock.findAllByMedico(this.objectSpy)).thenReturn(new HashMap<>());
    }

    @Test
    public void testUpdate() {
        Medico medicoTestSpy = spy(new Medico("Jose", 60, new ArrayList<>(), new HashMap<>(), new HashMap<>(),
                mock(Especialidad.class), mock(AtencionParticular.class)));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(medicoTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(medicoTestSpy);
        assertSame(this.objectSpy.getNombre(), medicoTestSpy.getNombre());
        assertEquals(this.objectSpy.getEdad(), medicoTestSpy.getEdad());
        assertSame(this.objectSpy.getRecetas(), medicoTestSpy.getRecetas());
        assertSame(this.objectSpy.getTurnos(), medicoTestSpy.getTurnos());
        assertSame(this.objectSpy.getObraSocialMap(), medicoTestSpy.getObraSocialMap());
        assertSame(this.objectSpy.getEspecialidad(), medicoTestSpy.getEspecialidad());
        assertSame(this.objectSpy.getAtencionParticular(), medicoTestSpy.getAtencionParticular());
    }
}
