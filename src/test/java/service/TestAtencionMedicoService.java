package service;

import ar.edu.um.programacion2.curso2024.entity.Especialidad;
import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.Turno;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.AtencionMedicoService;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicoDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.RecetaDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.TurnoDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestAtencionMedicoService {
    private MockedStatic<IoCConteinerService> ioCConteinerServiceMockedStatic;
    private IoCConteinerService ioCConteinerServiceMock;
    private AtencionMedicoService atencionMedicoService;
    private Medico medicoMock;
    private MedicoDAO medicoDAOMock;
    private Receta recetaMock;
    private Turno turnoSpy;

    @BeforeEach
    public void preparacion() {
        this.ioCConteinerServiceMock = mock(IoCConteinerService.class);
        this.medicoMock = mock(Medico.class);
        this.medicoDAOMock = mock(MedicoDAO.class);
        this.recetaMock = mock(Receta.class);
        this.ioCConteinerServiceMockedStatic = mockStatic(IoCConteinerService.class);
        this.ioCConteinerServiceMockedStatic.when(IoCConteinerService::obtenerInstancia)
                .thenReturn(this.ioCConteinerServiceMock);
        when(this.ioCConteinerServiceMock.getMedicoDAO()).thenReturn(this.medicoDAOMock);
        this.turnoSpy = spy(new Turno(this.medicoMock, mock(Paciente.class), mock(AtencionParticular.class)));
        this.atencionMedicoService = AtencionMedicoService.obtenerInstancia();
    }

    private void preparacionAtenderTurno() {
        when(medicoMock.getObjectID()).thenReturn(1);
        when(this.medicoDAOMock.findById(1)).thenReturn(this.medicoMock);
        when(this.ioCConteinerServiceMock.getTurnoDAO()).thenReturn(mock(TurnoDAO.class));
        when(this.ioCConteinerServiceMock.getRecetaDAO()).thenReturn(mock(RecetaDAO.class));
        try {
            Mockito.doReturn(this.recetaMock).when(this.medicoMock).atender(this.turnoSpy);
        } catch (ObjectNotFoundException | NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void cierre() {
        this.ioCConteinerServiceMockedStatic.close();
    }

    @Test
    public void testAtenderTurno() {
        this.preparacionAtenderTurno();
        try {
            Receta recetaTest = this.atencionMedicoService.atenderTurno(this.turnoSpy);
            assertEquals(recetaTest, this.recetaMock);
            verify(this.medicoMock, times(1)).atender(this.turnoSpy);
            verify(this.turnoSpy).setTipoDeAtencion(null);
            verify(ioCConteinerServiceMock.getTurnoDAO()).update(this.turnoSpy);
            verify(ioCConteinerServiceMock.getRecetaDAO()).save(this.recetaMock);
        } catch (NotEnabledToRunException | ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAtenderTurno_NotEnabledToRunException() {
        when(this.turnoSpy.getTipoDeAtencion()).thenReturn(null);
        assertThrows(NotEnabledToRunException.class,() -> this.atencionMedicoService.atenderTurno(this.turnoSpy));
        this.preparacionAtenderTurno();
        try {
            when(this.turnoSpy.getTipoDeAtencion()).thenReturn(mock(ObraSocial.class));
            Mockito.doThrow(ObjectNotFoundException.class).when(this.medicoMock).atender(this.turnoSpy);
            assertThrows(NotEnabledToRunException.class, () -> this.atencionMedicoService.atenderTurno(this.turnoSpy));
            when(this.turnoSpy.getTipoDeAtencion()).thenReturn(mock(AtencionParticular.class));
            Mockito.doThrow(NotEnabledToRunException.class).when(this.medicoMock).atender(this.turnoSpy);
            assertThrows(NotEnabledToRunException.class, () -> this.atencionMedicoService.atenderTurno(this.turnoSpy));
            verify(this.turnoSpy, times(2)).setTipoDeAtencion(null);
            verify(ioCConteinerServiceMock.getTurnoDAO(), times(2)).update(this.turnoSpy);
        } catch (ObjectNotFoundException | NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testVerMedicos() {
        Medico medicoMockTest = mock(Medico.class);
        Map<Integer, Medico> medicoMapSim = new HashMap<>(Map.of(9, this.medicoMock,12, medicoMockTest));
        when(ioCConteinerServiceMock.getMedicoDAO().findAll()).thenReturn(medicoMapSim);
        List<Medico> medicoList = this.atencionMedicoService.verMedicos();
        assertSame(2, medicoList.size());
        assertTrue(medicoList.stream().anyMatch(e -> this.medicoMock.equals(e)));
        assertTrue(medicoList.stream().anyMatch(medicoMockTest::equals));
    }

    @Test
    public void testVerMedicos_Atencion() {
        Atencion atencionMock = mock(AtencionParticular.class);
        Medico medicoMockTest = mock(Medico.class);
        Map<Integer, Medico> medicoMapSim = new HashMap<>(Map.of(9, this.medicoMock,12, medicoMockTest));
        when(ioCConteinerServiceMock.getMedicoDAO().findAllByAtencion(atencionMock)).thenReturn(medicoMapSim);
        List<Medico> medicoList = this.atencionMedicoService.verMedicos(atencionMock);
        assertSame(2, medicoList.size());
        assertTrue(medicoList.stream().anyMatch(e -> this.medicoMock.equals(e)));
        assertTrue(medicoList.stream().anyMatch(medicoMockTest::equals));
    }

    @Test
    public void testVerMedicos_Especialidad() {
        Especialidad especialidadMock = mock(Especialidad.class);
        Medico medicoMockTest = mock(Medico.class);
        Map<Integer, Medico> medicoMapSim = new HashMap<>(Map.of(9, this.medicoMock,12, medicoMockTest));
        when(ioCConteinerServiceMock.getMedicoDAO().findAllByEspecialidad(especialidadMock)).thenReturn(medicoMapSim);
        List<Medico> medicoList = this.atencionMedicoService.verMedicos(especialidadMock);
        assertSame(2, medicoList.size());
        assertTrue(medicoList.stream().anyMatch(e -> this.medicoMock.equals(e)));
        assertTrue(medicoList.stream().anyMatch(medicoMockTest::equals));
    }

    @Test
    public void testVerRecetas() {
        RecetaDAO recetaDAOMock = mock(RecetaDAO.class);
        when(this.ioCConteinerServiceMock.getRecetaDAO()).thenReturn(recetaDAOMock);
        Receta recetaMockTest = mock(Receta.class);
        Map<Integer, Receta> recetaMapSim = new HashMap<>(Map.of(9, this.recetaMock,12, recetaMockTest));
        Mockito.doReturn(recetaMapSim).when(recetaDAOMock).findAll();
        List<Receta> recetaList = this.atencionMedicoService.verRecetas();
        assertSame(2, recetaList.size());
        assertTrue(recetaList.stream().anyMatch(e -> this.recetaMock.equals(e)));
        assertTrue(recetaList.stream().anyMatch(recetaMockTest::equals));
    }

    @Test
    public void testVerRecetas_Persona() {
        RecetaDAO recetaDAOMock = mock(RecetaDAO.class);
        when(this.ioCConteinerServiceMock.getRecetaDAO()).thenReturn(recetaDAOMock);
        Paciente pacienteMock = mock(Paciente.class);
        Receta recetaMockTest = mock(Receta.class);
        Map<Integer, Receta> recetaMapSimPaciente = new HashMap<>(Map.of(9, this.recetaMock,12, recetaMockTest));
        Map<Integer, Receta> recetaMapSimMedico = new HashMap<>(Map.of(9, this.recetaMock));
        Mockito.doReturn(recetaMapSimPaciente).when(recetaDAOMock).findAllByPaciente(pacienteMock);
        Mockito.doReturn(recetaMapSimMedico).when(recetaDAOMock).findAllByMedico(this.medicoMock);
        List<Receta> recetaListPaciente = this.atencionMedicoService.verRecetas(pacienteMock);
        assertSame(2, recetaListPaciente.size());
        assertTrue(recetaListPaciente.stream().anyMatch(e -> this.recetaMock.equals(e)));
        assertTrue(recetaListPaciente.stream().anyMatch(recetaMockTest::equals));
        List<Receta> recetaListMedico = this.atencionMedicoService.verRecetas(this.medicoMock);
        assertSame(1, recetaListMedico.size());
        assertTrue(recetaListMedico.stream().anyMatch(e -> this.recetaMock.equals(e)));
        assertFalse(recetaListMedico.stream().anyMatch(recetaMockTest::equals));
    }
}
