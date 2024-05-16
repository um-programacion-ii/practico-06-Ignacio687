package service;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.estado.Iniciado;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;
import ar.edu.um.programacion2.curso2024.service.GestionDrogueriaService;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicamentoDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.PedidoDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestGestionDrogueriaService {
    private MockedStatic<IoCConteinerService> ioCConteinerServiceMockedStatic;
    private IoCConteinerService ioCConteinerServiceMock;
    private GestionDrogueriaService gestionDrogueriaService;
    private Medicamento medicamentoMock1;
    private Medicamento medicamentoMock2;
    private Pedido pedidoSpy;
    private Map<Integer, Medicamento> medicamentoMapSpy;
    private PedidoDAO pedidoDAOMock;

    @BeforeEach
    public void preparacion() {
        this.ioCConteinerServiceMock = mock(IoCConteinerService.class);
        this.ioCConteinerServiceMockedStatic = mockStatic(IoCConteinerService.class);
        this.ioCConteinerServiceMockedStatic.when(IoCConteinerService::obtenerInstancia)
                .thenReturn(this.ioCConteinerServiceMock);
        this.gestionDrogueriaService = GestionDrogueriaService.obtenerInstancia();
        this.medicamentoMock1 = mock(Medicamento.class);
        this.medicamentoMock2 = mock(Medicamento.class);
        this.medicamentoMapSpy = spy(new HashMap<>());
        this.pedidoSpy = spy(new Pedido(this.medicamentoMapSpy, mock(Iniciado.class)));
        when(this.pedidoSpy.getObjectID()).thenReturn(1);
        when(this.medicamentoMock1.getObjectID()).thenReturn(1);
        when(this.medicamentoMock2.getObjectID()).thenReturn(2);
        this.pedidoDAOMock = mock(PedidoDAO.class);
        when(this.ioCConteinerServiceMock.getPedidoDAO()).thenReturn(this.pedidoDAOMock);
        when(this.pedidoDAOMock.findById(1)).thenReturn(pedidoSpy);
    }

    @AfterEach
    public void cierre() {
        this.ioCConteinerServiceMockedStatic.close();
    }

    @Test
    public void testIniciarPedido() {
        when(ioCConteinerServiceMock.getEstadoIniciado()).thenReturn(mock(Iniciado.class));
        Pedido pedido = this.gestionDrogueriaService.iniciarPedido(List.of(this.medicamentoMock1,
                this.medicamentoMock2));
        assertInstanceOf(Iniciado.class, pedido.getEstado());
        assertNotSame(pedido.getMedicamentos().get(1), this.medicamentoMock1);
        assertNotSame(pedido.getMedicamentos().get(2), this.medicamentoMock2);
        for (Medicamento medicamento: List.of(this.medicamentoMock1, this.medicamentoMock2)) {
            assertEquals(medicamento.getNombre(), pedido.getMedicamentos().get(medicamento.getObjectID()).getNombre());
            assertEquals(medicamento.getPrecio(), pedido.getMedicamentos().get(medicamento.getObjectID()).getPrecio());
            assertEquals(medicamento.getCantidad(), pedido.getMedicamentos()
                    .get(medicamento.getObjectID()).getCantidad());
        }
    }

    @Test
    public void testFinalizarPedido() {
        when(ioCConteinerServiceMock.getEstadoFinalizado()).thenReturn(mock(Finalizado.class));
        when(this.medicamentoMapSpy.values()).thenReturn(List.of(this.medicamentoMock1, this.medicamentoMock2));
        MedicamentoDAO medicamentoDAOMock = mock(MedicamentoDAO.class);
        when(this.ioCConteinerServiceMock.getMedicamentoDAO()).thenReturn(medicamentoDAOMock);
        when(medicamentoDAOMock.findById(1)).thenReturn(this.medicamentoMock1);
        when(medicamentoDAOMock.findById(2)).thenReturn(this.medicamentoMock2);
        when(this.medicamentoMock1.getCantidad()).thenReturn(5);
        when(this.medicamentoMock2.getCantidad()).thenReturn(10);
        try {
            this.gestionDrogueriaService.finalizarPedido(this.pedidoSpy);
            verify(this.medicamentoMock1, times(1)).agregar(5);
            verify(this.medicamentoMock2, times(1)).agregar(10);
            verify(medicamentoDAOMock).update(this.medicamentoMock1);
            verify(medicamentoDAOMock).update(this.medicamentoMock2);
            verify(this.pedidoSpy).setEstado(any(Finalizado.class));
            verify(this.pedidoDAOMock).update(this.pedidoSpy);
        } catch (NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFinalizarPedido_NotEnabledToRunException() {
        when(this.pedidoSpy.getEstado()).thenReturn(mock(Finalizado.class));
        assertThrows(NotEnabledToRunException.class, () -> this.gestionDrogueriaService
                .finalizarPedido(this.pedidoSpy));
    }

    @Test
    public void testVerPedidos() {
        Pedido pedidoMockTest = mock(Pedido.class);
        Map<Integer, Pedido> pedidoMapSim = new HashMap<>(Map.of(9, this.pedidoSpy,12, pedidoMockTest));
        when(ioCConteinerServiceMock.getPedidoDAO().findAll()).thenReturn(pedidoMapSim);
        List<Pedido> pedidoList = this.gestionDrogueriaService.verPedidos();
        assertSame(2, pedidoList.size());
        assertTrue(pedidoList.stream().anyMatch(e -> this.pedidoSpy.equals(e)));
        assertTrue(pedidoList.stream().anyMatch(pedidoMockTest::equals));
    }
}
