package entity;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.estado.Iniciado;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestMedico extends TestPersona {
    private Medico medicoSpy;
    private ObraSocial obraSocialMock;
    private Map<Integer, ObraSocial>  obraSocialMapSpy;
    private Especialidad especialidadMock;
    private AtencionParticular atencionParticularMock;
    private Turno turnoParticularMock;
    private Turno turnoObraSocialMock;

    @Override
    protected Persona crearInstancia() {
        return new Medico("Franco", 45, this.recetasSpy, this.turnoMap, null,
                null, null);
    }

    @BeforeEach
    public void preparacionMedico() {
        this.obraSocialMock = mock(ObraSocial.class);
        when(this.obraSocialMock.getObjectID()).thenReturn(1);
        this.obraSocialMapSpy = spy(new HashMap<>());
        this.especialidadMock = mock(Especialidad.class);
        this.atencionParticularMock = mock(AtencionParticular.class);
        this.turnoParticularMock = mock(Turno.class);
        when(this.turnoParticularMock.getTipoDeAtencion()).thenReturn(this.atencionParticularMock);
        when(this.turnoParticularMock.getObjectID()).thenReturn(1);
        this.turnoObraSocialMock = mock(Turno.class);
        when(this.turnoObraSocialMock.getTipoDeAtencion()).thenReturn(this.obraSocialMock);
        when(this.turnoObraSocialMock.getObjectID()).thenReturn(2);
        this.medicoSpy = spy(new Medico(null, null, null, new HashMap<>(), this.obraSocialMapSpy,
                this.especialidadMock, null));
    }

    @Test
    public void testAtender() {
        Receta recetaMock = mock(Receta.class);
        when(obraSocialMock.getObjectID()).thenReturn(1);
        when(this.medicoSpy.generarReceta(this.turnoParticularMock)).thenReturn(recetaMock);
        when(this.medicoSpy.generarReceta(this.turnoObraSocialMock)).thenReturn(recetaMock);
        this.medicoSpy.getTurnos().put(1, this.turnoParticularMock);
        this.medicoSpy.getTurnos().put(2, this.turnoObraSocialMock);
        this.medicoSpy.setAtencionParticular(this.atencionParticularMock);
        when(this.obraSocialMapSpy.get(1)).thenReturn(this.obraSocialMock);
        try {
            assertSame(recetaMock, this.medicoSpy.atender(this.turnoObraSocialMock));
            verify(this.obraSocialMock, times(1)).registrarAtencion();
            verify(this.medicoSpy).eliminarTurno(this.turnoObraSocialMock);
            assertSame(recetaMock, this.medicoSpy.atender(this.turnoParticularMock));
            verify(this.atencionParticularMock, times(1)).registrarAtencion();
            verify(this.medicoSpy).eliminarTurno(this.turnoParticularMock);
            verify(this.especialidadMock, times(2)).tratar();
        } catch (ObjectNotFoundException | NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAtender_ObjectNotFoundException() {
        assertThrows(ObjectNotFoundException.class, () -> this.medicoSpy.atender(this.turnoParticularMock));
        assertThrows(ObjectNotFoundException.class, () -> this.medicoSpy.atender(this.turnoObraSocialMock));
    }

    @Test
    public void testAtender_NotEnabledToRunException() {
        this.medicoSpy.getTurnos().put(1, this.turnoParticularMock);
        this.medicoSpy.getTurnos().put(2, this.turnoObraSocialMock);
        ObraSocial obraSocialMock1 = mock(ObraSocial.class);
        when(obraSocialMock1.getObjectID()).thenReturn(2);
        when(this.obraSocialMapSpy.get(2)).thenReturn(obraSocialMock1);
        assertThrows(NotEnabledToRunException.class, () -> this.medicoSpy.atender(this.turnoObraSocialMock));
        when(this.medicoSpy.getEspecialidad()).thenReturn(this.especialidadMock);
        assertThrows(NotEnabledToRunException.class, () -> this.medicoSpy.atender(this.turnoObraSocialMock));
        when(this.medicoSpy.getEspecialidad()).thenReturn(null);
        assertThrows(NotEnabledToRunException.class, () -> this.medicoSpy.atender(this.turnoParticularMock));
    }

    @Test
    public void testHabilitadoAtencionParticular() {
        assertFalse(this.medicoSpy.habilitadoAtencionParticular());
        this.medicoSpy.setAtencionParticular(this.atencionParticularMock);
        assertTrue(this.medicoSpy.habilitadoAtencionParticular());
    }

    @Test
    public void testGenerarReceta() {
        Paciente pacienteMock = mock(Paciente.class);
        Turno turno = new Turno(this.medicoSpy, pacienteMock, null);
        Receta receta;
        boolean NullAssertionFlag = false;
        boolean NotNullAssertionFlag = false;
        for (int counter=0; counter<21; counter++) {
            receta = this.medicoSpy.generarReceta(turno);
            if (receta == null) {
                NullAssertionFlag = true;
            } else {
                NotNullAssertionFlag = true;
                assertSame(this.medicoSpy, receta.getMedico());
                assertSame(pacienteMock, receta.getPaciente());
                assertInstanceOf(Iniciado.class, receta.getEstado());
            }
        }
        assertTrue(NullAssertionFlag);
        assertTrue(NotNullAssertionFlag);
    }

    @Test
    public void agregarObraSocial() {
        try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
            this.medicoSpy.agregarObraSocial(this.obraSocialMock);
            mockedStatic.verify(() -> ORMMapManager.addObject(this.obraSocialMock, this.obraSocialMapSpy));
            mockedStatic.verify(() -> ORMMapManager.addObject(any(ObraSocial.class),
                    any(Map.class)), times(1));
        }
    }

    @Test
    public void eliminarObraSocial() {
        try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
            this.medicoSpy.eliminarObraSocial(this.obraSocialMock);
            mockedStatic.verify(() -> ORMMapManager.delObject(this.obraSocialMock, this.obraSocialMapSpy));
            mockedStatic.verify(() -> ORMMapManager.delObject(any(ObraSocial.class),
                    any(Map.class)), times(1));
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
