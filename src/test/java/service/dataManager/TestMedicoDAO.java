package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.EspecialidadDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicoDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.RecetaDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.TurnoDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

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

    private void preparacionFindBy() {
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Collection<Medico> medicoMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(medicoMapSimulado);
    }

    @Test
    public void testFindAllByAtencion_AtencionParticular() {
        this.preparacionFindBy();
        when(this.objectSpy.habilitadoAtencionParticular()).thenReturn(false);
        when(this.objectSpy2.habilitadoAtencionParticular()).thenReturn(true);
        Map<Integer, Medico> medicoMap = this.objectDAOSpy.findAllByAtencion(mock(AtencionParticular.class));
        assertEquals(1, medicoMap.size());
        assertSame(this.objectSpy2.getObjectID(), medicoMap.get(2).getObjectID());
    }


    @Test
    public void testFindAllByAtencion_ObraSocial() {
        this.preparacionFindBy();
        ObraSocial obraSocialMock1 = mock(ObraSocial.class);
        ObraSocial obraSocialMock2 = mock(ObraSocial.class);
        when(obraSocialMock1.getObjectID()).thenReturn(1);
        when(obraSocialMock2.getObjectID()).thenReturn(2);
        Map<Integer, ObraSocial> obraSocialMap1 = new HashMap<>(Map.of(1, obraSocialMock1, 2, obraSocialMock2));
        Map<Integer, ObraSocial> obraSocialMap2 = new HashMap<>(Map.of(1, obraSocialMock1));
        when(this.objectSpy.getObraSocialMap()).thenReturn(obraSocialMap1);
        when(this.objectSpy2.getObraSocialMap()).thenReturn(obraSocialMap2);
        Map<Integer, Medico> medicoMap = this.objectDAOSpy.findAllByAtencion(obraSocialMock2);
        assertEquals(1, medicoMap.size());
        assertSame(this.objectSpy.getObjectID(), medicoMap.get(1).getObjectID());
    }

    @Test
    public void testFindAllByAtencion_Especialidad() {
        this.preparacionFindBy();
        Especialidad especialidadMock1 = mock(Especialidad.class);
        Especialidad especialidadMock2 = mock(Especialidad.class);
        when(especialidadMock1.getObjectID()).thenReturn(1);
        when(especialidadMock2.getObjectID()).thenReturn(2);
        when(this.objectSpy.getEspecialidad()).thenReturn(especialidadMock1);
        when(this.objectSpy2.getEspecialidad()).thenReturn(especialidadMock2);
        Map<Integer, Medico> medicoMap = this.objectDAOSpy.findAllByEspecialidad(especialidadMock2);
        assertEquals(1, medicoMap.size());
        assertSame(this.objectSpy2.getObjectID(), medicoMap.get(2).getObjectID());
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
