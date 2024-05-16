package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.estado.Libre;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TestPacienteDAO extends TestAbstractDAO<PacienteDAO, Paciente> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(PacienteDAO.class);
        this.objectDAOSpy.setPacienteMap(this.objectMapSpy);
        RecetaDAO recetaDAOMock = mock(RecetaDAO.class);
        when(ioCConteinerServiceMock.getRecetaDAO()).thenReturn(recetaDAOMock);
        TurnoDAO turnoDAOMock = mock(TurnoDAO.class);
        when(ioCConteinerServiceMock.getTurnoDAO()).thenReturn(turnoDAOMock);
        CompraDAO compraDAOMock = mock(CompraDAO.class);
        when(ioCConteinerServiceMock.getCompraDAO()).thenReturn(compraDAOMock);
        ObraSocial obraSocialMock = mock(ObraSocial.class);
        when(obraSocialMock.getObjectID()).thenReturn(1);
        ObraSocialDAO obraSocialDAOMock = mock(ObraSocialDAO.class);
        when(ioCConteinerServiceMock.getObraSocialDAO()).thenReturn(obraSocialDAOMock);
        when(obraSocialDAOMock.findById(1)).thenReturn(obraSocialMock);
        this.objectSpy = spy(new Paciente("Pepe", 22, new ArrayList<>(), new HashMap<>(), obraSocialMock,
                mock(Libre.class), new ArrayList<>()));
        this.objectSpy2 = spy(new Paciente("Juan", 45, new ArrayList<>(), new HashMap<>(), obraSocialMock,
                mock(Libre.class), new ArrayList<>()));
        when(turnoDAOMock.findAllByPaciente(this.objectSpy)).thenReturn(new HashMap<>());
        when(recetaDAOMock.findAllByPaciente(this.objectSpy)).thenReturn(new HashMap<>());
        when(compraDAOMock.findAllByPaciente(this.objectSpy)).thenReturn(new HashMap<>());
    }

    @Test
    public void testUpdate() {
        Paciente pacienteTestSpy = spy(new Paciente("Franco", 15, new ArrayList<>(), new HashMap<>(),
                mock(ObraSocial.class), mock(Libre.class), new ArrayList<>()));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(pacienteTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(pacienteTestSpy);
        assertSame(this.objectSpy.getNombre(), pacienteTestSpy.getNombre());
        assertEquals(this.objectSpy.getEdad(), pacienteTestSpy.getEdad());
        assertSame(this.objectSpy.getRecetas(), pacienteTestSpy.getRecetas());
        assertSame(this.objectSpy.getTurnos(), pacienteTestSpy.getTurnos());
        assertSame(this.objectSpy.getObraSocial(), pacienteTestSpy.getObraSocial());
        assertSame(this.objectSpy.getEstado(), pacienteTestSpy.getEstado());
        assertSame(this.objectSpy.getCompras(), pacienteTestSpy.getCompras());
    }
}
