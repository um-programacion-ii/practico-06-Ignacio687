package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.CompraDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.PacienteDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestCompraDAO extends TestAbstractDAO<CompraDAO, Compra> {

    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceSpy) {
        this.objectDAOSpy = spy(CompraDAO.class);
        this.objectDAOSpy.setCompraMap(this.objectMapSpy);
        Paciente pacienteMock = mock(Paciente.class);
        when(pacienteMock.getObjectID()).thenReturn(1);
        PacienteDAO pacienteDAOMock = mock(PacienteDAO.class);
        when(ioCConteinerServiceSpy.getPacienteDAO()).thenReturn(pacienteDAOMock);
        when(pacienteDAOMock.findById(1)).thenReturn(pacienteMock);
        this.objectSpy = spy(new Compra(new HashMap<>(), mock(IniciadoImp.class), pacienteMock));
        this.objectSpy2 = spy(new Compra(new HashMap<>(), mock(Finalizado.class), pacienteMock));
    }

    @Test
    public void testfindAllByPaciente() {
        Paciente pacienteMock2 = mock(Paciente.class);
        when(pacienteMock2.getObjectID()).thenReturn(2);
        this.objectSpy2.setPaciente(pacienteMock2);
        when(this.objectSpy.getObjectID()).thenReturn(1);
        when(this.objectSpy2.getObjectID()).thenReturn(2);
        Collection<Compra> compraMapSimulado = new ArrayList<>(List.of(this.objectSpy, this.objectSpy2));
        when(this.objectMapSpy.values()).thenReturn(compraMapSimulado);
        Mockito.doReturn(this.objectSpy).when(this.objectDAOSpy).findById(1);
        Mockito.doReturn(this.objectSpy2).when(this.objectDAOSpy).findById(2);
        Map<Integer, Compra> compraMap = this.objectDAOSpy.findAllByPaciente(pacienteMock2);
        assertEquals(1, compraMap.size());
        assertSame(this.objectSpy2.getObjectID(), compraMap.get(2).getObjectID());
        assertEquals(pacienteMock2.getObjectID(), compraMap.get(2).getPaciente().getObjectID());
    }

    @Test
    public void testUpdate() {
        Compra compraTestSpy = spy(new Compra(new HashMap<>(Map.of(1, mock(Medicamento.class))),
                mock(Finalizado.class), mock(Paciente.class)));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(compraTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(compraTestSpy);
        assertSame(this.objectSpy.getPaciente(), compraTestSpy.getPaciente());
        assertSame(this.objectSpy.getMedicamentos(), compraTestSpy.getMedicamentos());
        assertSame(this.objectSpy.getEstado(), compraTestSpy.getEstado());
    }
}
