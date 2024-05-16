package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class TestTurnoDAO extends TestAbstractDAO<TurnoDAO, Turno> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(TurnoDAO.class);
        this.objectDAOSpy.setTurnoMap(this.objectMapSpy);
        ObraSocial obraSocialMock = mock(ObraSocial.class);
        when(obraSocialMock.getObjectID()).thenReturn(1);
        ObraSocialDAO obraSocialDAOMock = mock(ObraSocialDAO.class);
        when(ioCConteinerServiceMock.getObraSocialDAO()).thenReturn(obraSocialDAOMock);
        when(obraSocialDAOMock.findById(1)).thenReturn(obraSocialMock);
        Paciente pacienteMock = mock(Paciente.class);
        when(pacienteMock.getObjectID()).thenReturn(1);
        PacienteDAO pacienteDAOMock = mock(PacienteDAO.class);
        when(ioCConteinerServiceMock.getPacienteDAO()).thenReturn(pacienteDAOMock);
        when(pacienteDAOMock.findById(1)).thenReturn(pacienteMock);
        Medico medicoMock = mock(Medico.class);
        when(medicoMock.getObjectID()).thenReturn(1);
        MedicoDAO medicoDAOMock = mock(MedicoDAO.class);
        when(ioCConteinerServiceMock.getMedicoDAO()).thenReturn(medicoDAOMock);
        when(medicoDAOMock.findById(1)).thenReturn(medicoMock);
        this.objectSpy = spy(new Turno(medicoMock, pacienteMock, obraSocialMock));
        this.objectSpy2 = spy(new Turno(medicoMock, pacienteMock, mock(AtencionParticular.class)));
    }

    @Test
    public void testfindAllByPaciente() {
        Paciente pacienteMock2 = mock(Paciente.class);
        when(pacienteMock2.getObjectID()).thenReturn(2);
        this.objectSpy2.setPaciente(pacienteMock2);
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Collection<Turno> turnoMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(turnoMapSimulado);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Map<Integer, Turno> turnoMap = this.objectDAOSpy.findAllByPaciente(pacienteMock2);
        assertEquals(1, turnoMap.size());
        assertSame(this.objectSpy2.getObjectID(), turnoMap.get(2).getObjectID());
        assertEquals(pacienteMock2.getObjectID(), turnoMap.get(2).getPaciente().getObjectID());
    }

    @Test
    public void testfindAllByMedico() {
        Medico medicoMock2 = mock(Medico.class);
        when(medicoMock2.getObjectID()).thenReturn(2);
        this.objectSpy2.setMedico(medicoMock2);
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Collection<Turno> turnoMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(turnoMapSimulado);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Map<Integer, Turno> turnoMap = this.objectDAOSpy.findAllByMedico(medicoMock2);
        assertEquals(1, turnoMap.size());
        assertSame(this.objectSpy2.getObjectID(), turnoMap.get(2).getObjectID());
        assertEquals(medicoMock2.getObjectID(), turnoMap.get(2).getMedico().getObjectID());
    }

    private void preparacionFindBy() {
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Collection<Turno> medicoMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(medicoMapSimulado);
    }

    @Test
    public void testFindAllByAtencion_AtencionParticular() {
        this.preparacionFindBy();
        when(this.objectSpy.getTipoDeAtencion()).thenReturn(mock(ObraSocial.class));
        when(this.objectSpy2.getTipoDeAtencion()).thenReturn(mock(AtencionParticular.class));
        Map<Integer, Turno> turnoMap = this.objectDAOSpy.findAllByAtencion(mock(AtencionParticular.class));
        assertEquals(1, turnoMap.size());
        assertSame(this.objectSpy2.getObjectID(), turnoMap.get(2).getObjectID());
    }


    @Test
    public void testFindAllByAtencion_ObraSocial() {
        this.preparacionFindBy();
        ObraSocial obraSocialMock1 = mock(ObraSocial.class);
        ObraSocial obraSocialMock2 = mock(ObraSocial.class);
        when(obraSocialMock1.getObjectID()).thenReturn(1);
        when(obraSocialMock2.getObjectID()).thenReturn(2);
        when(this.objectSpy.getTipoDeAtencion()).thenReturn(obraSocialMock1);
        when(this.objectSpy2.getTipoDeAtencion()).thenReturn(obraSocialMock2);
        Map<Integer, Turno> turnoMap = this.objectDAOSpy.findAllByAtencion(obraSocialMock1);
        assertEquals(1, turnoMap.size());
        assertSame(this.objectSpy.getObjectID(), turnoMap.get(1).getObjectID());
    }

    @Test
    public void testUpdate() {
        Turno turnoTestSpy = spy(new Turno(mock((Medico.class)), mock(Paciente.class), mock(AtencionParticular.class)));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(turnoTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(turnoTestSpy);
        assertSame(this.objectSpy.getPaciente(), turnoTestSpy.getPaciente());
        assertSame(this.objectSpy.getMedico(), turnoTestSpy.getMedico());
        assertSame(this.objectSpy.getTipoDeAtencion(), turnoTestSpy.getTipoDeAtencion());
    }
}
