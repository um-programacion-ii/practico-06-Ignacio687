package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicoDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.PacienteDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.RecetaDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class TestRecetaDAO extends TestAbstractDAO<RecetaDAO, Receta> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(RecetaDAO.class);
        this.objectDAOSpy.setRecetaMap(this.objectMapSpy);
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
        this.objectSpy = spy(new Receta(new HashMap<>(), mock(IniciadoImp.class), medicoMock, pacienteMock));
        this.objectSpy2 = spy(new Receta(new HashMap<>(), mock(Finalizado.class), medicoMock, pacienteMock));
    }

    @Test
    public void testfindAllByPaciente() {
        Paciente pacienteMock2 = mock(Paciente.class);
        when(pacienteMock2.getObjectID()).thenReturn(2);
        this.objectSpy2.setPaciente(pacienteMock2);
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Collection<Receta> recetaMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(recetaMapSimulado);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Map<Integer, Receta> recetaMap = this.objectDAOSpy.findAllByPaciente(pacienteMock2);
        assertEquals(1, recetaMap.size());
        assertSame(this.objectSpy2.getObjectID(), recetaMap.get(2).getObjectID());
        assertEquals(pacienteMock2.getObjectID(), recetaMap.get(2).getPaciente().getObjectID());
    }

    @Test
    public void testfindAllByMedico() {
        Medico medicoMock2 = mock(Medico.class);
        when(medicoMock2.getObjectID()).thenReturn(2);
        this.objectSpy2.setMedico(medicoMock2);
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Collection<Receta> recetaMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(recetaMapSimulado);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Map<Integer, Receta> recetaMap = this.objectDAOSpy.findAllByMedico(medicoMock2);
        assertEquals(1, recetaMap.size());
        assertSame(this.objectSpy2.getObjectID(), recetaMap.get(2).getObjectID());
        assertEquals(medicoMock2.getObjectID(), recetaMap.get(2).getMedico().getObjectID());
    }

    @Test
    public void testUpdate() {
        Receta recetaTestSpy = spy(new Receta(new HashMap<>(Map.of(1, mock(Medicamento.class))),
                mock(Finalizado.class), mock(Medico.class), mock(Paciente.class)));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(recetaTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(recetaTestSpy);
        assertSame(this.objectSpy.getPaciente(), recetaTestSpy.getPaciente());
        assertSame(this.objectSpy.getMedico(), recetaTestSpy.getMedico());
        assertSame(this.objectSpy.getMedicamentos(), recetaTestSpy.getMedicamentos());
        assertSame(this.objectSpy.getEstado(), recetaTestSpy.getEstado());
    }
}
