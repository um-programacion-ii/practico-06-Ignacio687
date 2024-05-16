package service;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.atencion.Atencion;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.estado.Libre;
import ar.edu.um.programacion2.curso2024.entity.estado.Ocupado;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.AtencionMedicoService;
import ar.edu.um.programacion2.curso2024.service.GestionTurnoService;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicoDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.PacienteDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.TurnoDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestGestionTurnoService {
    private MockedStatic<IoCConteinerService> ioCConteinerServiceMockedStatic;
    private IoCConteinerService ioCConteinerServiceMock;
    private GestionTurnoService gestionTurnoService;
    private Paciente pacienteMock;
    private Medico medicoMock;
    private Turno turnoMock;
    private TurnoDAO turnoDAOMock;


    @BeforeEach
    public void preparacion() {
        this.ioCConteinerServiceMock = mock(IoCConteinerService.class);
        this.ioCConteinerServiceMockedStatic = mockStatic(IoCConteinerService.class);
        this.ioCConteinerServiceMockedStatic.when(IoCConteinerService::obtenerInstancia)
                .thenReturn(this.ioCConteinerServiceMock);
        this.gestionTurnoService = GestionTurnoService.obtenerInstancia();
        this.pacienteMock = mock(Paciente.class);
        this.medicoMock = mock(Medico.class);
        when(this.medicoMock.getObjectID()).thenReturn(1);
        this.turnoMock = mock(Turno.class);
        this.turnoDAOMock = mock(TurnoDAO.class);
        when(ioCConteinerServiceMock.getTurnoDAO()).thenReturn(this.turnoDAOMock);
    }
    @AfterEach
    public void cierre() {
        this.ioCConteinerServiceMockedStatic.close();
    }

    @Test
    public void testSolicitarTurno() {
        Medico medicoMock2 = mock(Medico.class);
        when(medicoMock2.getObjectID()).thenReturn(2);
        Medico medicoMock3 = mock(Medico.class);
        when(medicoMock3.getObjectID()).thenReturn(3);
        AtencionParticular atencionParticularMock = mock(AtencionParticular.class);
        ObraSocial obraSocialMock = mock(ObraSocial.class);
        Especialidad especialidadMock = mock(Especialidad.class);
        Map<Integer, Medico> medicoEspecialidadMap = new HashMap<>(Map.of(1, this.medicoMock, 2, medicoMock2));
        Map<Integer, Medico> medicoTipoDeAtencionMap = new HashMap<>(Map.of(2, medicoMock2, 3, medicoMock3));
        MedicoDAO medicoDAOMock = mock(MedicoDAO.class);
        when(ioCConteinerServiceMock.getMedicoDAO()).thenReturn(medicoDAOMock);
        when(medicoDAOMock.findAllByEspecialidad(especialidadMock)).thenReturn(medicoEspecialidadMap);
        when(medicoDAOMock.findAllByAtencion(any(Atencion.class))).thenReturn(medicoTipoDeAtencionMap);
        doNothing().when(this.turnoDAOMock).save(any(Turno.class));
        try {
            Turno turno = this.gestionTurnoService.solicitarTurno(this.pacienteMock, especialidadMock,
                    atencionParticularMock);
            assertSame(turno.getPaciente(), this.pacienteMock);
            assertSame(turno.getTipoDeAtencion(), atencionParticularMock);
            assertSame(turno.getMedico(), medicoMock2);
            verify(turnoDAOMock, times(1)).save(turno);
            medicoEspecialidadMap.put(3, medicoMock3);
            Turno turno2 = this.gestionTurnoService.solicitarTurno(this.pacienteMock, especialidadMock, obraSocialMock);
            assertSame(turno2.getPaciente(), this.pacienteMock);
            assertSame(turno2.getTipoDeAtencion(), obraSocialMock);
            assertTrue(Stream.of(medicoMock2, medicoMock3).anyMatch(obj -> obj.equals(turno2.getMedico())));
            verify(turnoDAOMock, times(1)).save(turno2);
            verify(turnoDAOMock, times(2)).save(any(Turno.class));
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSolicitarTurno_ObjectNotFoundException() {
        Medico medicoMock2 = mock(Medico.class);
        when(medicoMock2.getObjectID()).thenReturn(2);
        Medico medicoMock3 = mock(Medico.class);
        when(medicoMock3.getObjectID()).thenReturn(3);
        AtencionParticular atencionParticularMock = mock(AtencionParticular.class);
        ObraSocial obraSocialMock = mock(ObraSocial.class);
        Especialidad especialidadMock = mock(Especialidad.class);
        Map<Integer, Medico> medicoEspecialidadMap = new HashMap<>(Map.of(1, this.medicoMock, 2, medicoMock2));
        Map<Integer, Medico> medicoTipoDeAtencionMap = new HashMap<>(Map.of(3, medicoMock3));
        MedicoDAO medicoDAOMock = mock(MedicoDAO.class);
        when(ioCConteinerServiceMock.getMedicoDAO()).thenReturn(medicoDAOMock);
        when(medicoDAOMock.findAllByEspecialidad(especialidadMock)).thenReturn(medicoEspecialidadMap);
        when(medicoDAOMock.findAllByAtencion(any(Atencion.class))).thenReturn(medicoTipoDeAtencionMap);
        assertThrows(ObjectNotFoundException.class, () -> this.gestionTurnoService.solicitarTurno(this.pacienteMock,
                especialidadMock, obraSocialMock));
        assertThrows(ObjectNotFoundException.class, () -> this.gestionTurnoService.solicitarTurno(this.pacienteMock,
                especialidadMock, atencionParticularMock));
        medicoEspecialidadMap.clear();
        assertThrows(ObjectNotFoundException.class, () -> this.gestionTurnoService.solicitarTurno(this.pacienteMock,
                especialidadMock, atencionParticularMock));
    }

    @Test
    public void testSolicitarTurno_EleccionDeMedico() {
        ObraSocial obraSocialMock = mock(ObraSocial.class);
        doNothing().when(this.turnoDAOMock).save(any(Turno.class));
        MedicoDAO medicoDAOMock = mock(MedicoDAO.class);
        when(ioCConteinerServiceMock.getMedicoDAO()).thenReturn(medicoDAOMock);
        Turno turno = this.gestionTurnoService.solicitarTurno(this.pacienteMock, this.medicoMock, obraSocialMock);
        assertSame(turno.getPaciente(), this.pacienteMock);
        assertSame(turno.getTipoDeAtencion(), obraSocialMock);
        assertSame(turno.getMedico(), this.medicoMock);
        verify(turnoDAOMock, times(1)).save(turno);
    }

    @Test
    public void testIniciarTurno() {
        when(this.turnoMock.getPaciente()).thenReturn(this.pacienteMock);
        when(this.pacienteMock.comprobarEstado()).thenReturn(false);
        AtencionMedicoService atencionMedicoServiceMock = mock(AtencionMedicoService.class);
        when(this.ioCConteinerServiceMock.getAtencionMedicoService()).thenReturn(atencionMedicoServiceMock);
        Receta recetaMock = mock(Receta.class);
        PacienteDAO pacienteDAOMock = mock(PacienteDAO.class);
        when(this.ioCConteinerServiceMock.getPacienteDAO()).thenReturn(pacienteDAOMock);
        when(this.ioCConteinerServiceMock.getEstadoOcupado()).thenReturn(mock(Ocupado.class));
        when(this.ioCConteinerServiceMock.getEstadoLibre()).thenReturn(mock(Libre.class));
        try {
            when(atencionMedicoServiceMock.atenderTurno(this.turnoMock)).thenReturn(recetaMock);
            Receta receta = this.gestionTurnoService.iniciarTurno(this.turnoMock);
            assertSame(receta, recetaMock);
            verify(this.pacienteMock, times(1)).setEstado(any(Ocupado.class));
            verify(this.pacienteMock, times(1)).setEstado(any(Libre.class));
            verify(pacienteDAOMock, times(2)).update(this.pacienteMock);
        } catch (NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIniciarTurno_NotEnabledToRunException() {
        when(this.turnoMock.getPaciente()).thenReturn(this.pacienteMock);
        when(this.pacienteMock.comprobarEstado()).thenReturn(true);
        assertThrows(NotEnabledToRunException.class, () -> this.gestionTurnoService.iniciarTurno(this.turnoMock));
    }

    @Test
    public void testVerTurnos() {
        TurnoDAO turnoDAOMock = mock(TurnoDAO.class);
        when(this.ioCConteinerServiceMock.getTurnoDAO()).thenReturn(turnoDAOMock);
        Turno turnoMockTest = mock(Turno.class);
        Map<Integer, Turno> turnoMapSim = new HashMap<>(Map.of(9, this.turnoMock,12, turnoMockTest));
        Mockito.doReturn(turnoMapSim).when(turnoDAOMock).findAll();
        List<Turno> turnoList = this.gestionTurnoService.verTurnos();
        assertSame(2, turnoList.size());
        assertTrue(turnoList.stream().anyMatch(e -> this.turnoMock.equals(e)));
        assertTrue(turnoList.stream().anyMatch(turnoMockTest::equals));
    }

    @Test
    public void testVerTurnos_Persona() {
        TurnoDAO turnoDAOMock = mock(TurnoDAO.class);
        when(this.ioCConteinerServiceMock.getTurnoDAO()).thenReturn(turnoDAOMock);
        Paciente pacienteMock = mock(Paciente.class);
        Turno turnoMockTest = mock(Turno.class);
        Map<Integer, Turno> turnoMapSimPaciente = new HashMap<>(Map.of(9, this.turnoMock,12, turnoMockTest));
        Map<Integer, Turno> turnoMapSimMedico = new HashMap<>(Map.of(9, this.turnoMock));
        Mockito.doReturn(turnoMapSimPaciente).when(turnoDAOMock).findAllByPaciente(pacienteMock);
        Mockito.doReturn(turnoMapSimMedico).when(turnoDAOMock).findAllByMedico(this.medicoMock);
        List<Turno> turnoListPaciente = this.gestionTurnoService.verTurnos(pacienteMock);
        assertSame(2, turnoListPaciente.size());
        assertTrue(turnoListPaciente.stream().anyMatch(e -> this.turnoMock.equals(e)));
        assertTrue(turnoListPaciente.stream().anyMatch(turnoMockTest::equals));
        List<Turno> turnoListMedico = this.gestionTurnoService.verTurnos(this.medicoMock);
        assertSame(1, turnoListMedico.size());
        assertTrue(turnoListMedico.stream().anyMatch(e -> this.turnoMock.equals(e)));
        assertFalse(turnoListMedico.stream().anyMatch(turnoMockTest::equals));
    }

    @Test
    public void testVerTurnos_Atencion() {
        Atencion atencionMock = mock(AtencionParticular.class);
        Turno turnoMockTest = mock(Turno.class);
        Map<Integer, Turno> turnoMapSim = new HashMap<>(Map.of(9, this.turnoMock,12, turnoMockTest));
        when(ioCConteinerServiceMock.getTurnoDAO().findAllByAtencion(atencionMock)).thenReturn(turnoMapSim);
        List<Turno> turnoList = this.gestionTurnoService.verTurnos(atencionMock);
        assertSame(2, turnoList.size());
        assertTrue(turnoList.stream().anyMatch(e -> this.turnoMock.equals(e)));
        assertTrue(turnoList.stream().anyMatch(turnoMockTest::equals));
    }
}
