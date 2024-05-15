package service;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnoughStockException;
import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.estado.Iniciado;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.GestionDrogueriaService;
import ar.edu.um.programacion2.curso2024.service.GestionFarmaciaService;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.CompraDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicamentoDAO;
import ar.edu.um.programacion2.curso2024.service.dataManager.RecetaDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestGestionFarmaciaService {
    private MockedStatic<IoCConteinerService> ioCConteinerServiceMockedStatic;
    private IoCConteinerService ioCConteinerServiceMock;
    private GestionFarmaciaService gestionFarmaciaServiceSpy;
    private Medicamento medicamentoSpy1;
    private Medicamento medicamentoSpy2;
    private MedicamentoDAO medicamentoDAOMock;
    private Paciente pacienteMock;
    private Receta recetaMock;
    private Compra compraMock;

    
    @BeforeEach
    public void preparacion() {
        this.ioCConteinerServiceMock = mock(IoCConteinerService.class);
        this.ioCConteinerServiceMockedStatic = mockStatic(IoCConteinerService.class);
        this.ioCConteinerServiceMockedStatic.when(IoCConteinerService::obtenerInstancia)
                .thenReturn(this.ioCConteinerServiceMock);
        this.gestionFarmaciaServiceSpy = spy(GestionFarmaciaService.obtenerInstancia());
        this.medicamentoSpy1 = spy(new Medicamento("Med-x", 5, 100));
        when(this.medicamentoSpy1.getObjectID()).thenReturn(1);
        this.medicamentoSpy2 = spy(new Medicamento("Med-x", 10, 100));
        when(this.medicamentoSpy2.getObjectID()).thenReturn(2);
        this.medicamentoDAOMock = mock(MedicamentoDAO.class);
        when(this.ioCConteinerServiceMock.getMedicamentoDAO()).thenReturn(this.medicamentoDAOMock);
        when(this.medicamentoDAOMock.findById(1)).thenReturn(this.medicamentoSpy1);
        when(this.medicamentoDAOMock.findById(2)).thenReturn(this.medicamentoSpy2);
        this.recetaMock = mock(Receta.class);
        this.pacienteMock = mock(Paciente.class);
        this.compraMock = mock(Compra.class);
    }

    @AfterEach
    public void cierre() {
        this.ioCConteinerServiceMockedStatic.close();
    }

    @Test
    public void testVerificarStock() {
        when(this.medicamentoSpy1.verificarStock(anyInt())).thenReturn(false);
        when(this.medicamentoSpy2.verificarStock(anyInt())).thenReturn(true);
        List<Medicamento> medicamentoListSpy = spy(new ArrayList<>(List.of(this.medicamentoSpy1, 
                this.medicamentoSpy2)));
        List<Medicamento> medicamentoList = this.gestionFarmaciaServiceSpy.verificarStock(medicamentoListSpy);
        assertEquals(1, medicamentoList.size());
        assertTrue(medicamentoList.stream().anyMatch(e -> this.medicamentoSpy1.equals(e)));
    }

    @Test
    public void testVerificarStock_Null() {
        when(this.medicamentoSpy1.verificarStock(anyInt())).thenReturn(true);
        when(this.medicamentoSpy2.verificarStock(anyInt())).thenReturn(true);
        List<Medicamento> medicamentoListSpy = spy(new ArrayList<>(List.of(this.medicamentoSpy1,
                this.medicamentoSpy2)));
        assertNull(this.gestionFarmaciaServiceSpy.verificarStock(medicamentoListSpy));
    }

    @Test
    public void testRealizarPedido() {
        GestionDrogueriaService gestionDrogueriaServiceMock = mock(GestionDrogueriaService.class);
        Pedido pedidoMock = mock(Pedido.class);
        when(gestionDrogueriaServiceMock.iniciarPedido(anyList())).thenReturn(pedidoMock);
        when(ioCConteinerServiceMock.getGestionDrogueriaService()).thenReturn(gestionDrogueriaServiceMock);
        List<Medicamento> medicamentoListSpy = spy(new ArrayList<>(List.of(this.medicamentoSpy1,
                this.medicamentoSpy2)));
        ArgumentCaptor<List<Medicamento>> listArgumentCaptor = ArgumentCaptor.captor();
        this.gestionFarmaciaServiceSpy.realizarPedido(medicamentoListSpy);
        verify(gestionDrogueriaServiceMock, times(1)).iniciarPedido(listArgumentCaptor.capture());
        List<Medicamento> captureList = listArgumentCaptor.getValue();
        for (Medicamento medicamento: List.of(this.medicamentoSpy1, this.medicamentoSpy2)) {
            assertTrue(captureList.stream().noneMatch(medicamento::equals));
            assertTrue(captureList.stream().anyMatch(e -> medicamento.getObjectID() == e.getObjectID()));
            assertTrue(captureList.stream().anyMatch(e -> Objects.equals((medicamento.getCantidad()*2),
                    e.getCantidad())));
            assertTrue(captureList.stream().anyMatch(e -> Objects.equals(medicamento.getNombre(), e.getNombre())));
            assertTrue(captureList.stream().anyMatch(e -> Objects.equals(medicamento.getPrecio(), e.getPrecio())));
        }
        try {
            verify(gestionDrogueriaServiceMock, times(1)).finalizarPedido(pedidoMock);
        } catch (NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    private void preparacionCompra() {
        when(ioCConteinerServiceMock.getEstadoIniciado()).thenReturn(mock(Iniciado.class));
        when(ioCConteinerServiceMock.getEstadoFinalizado()).thenReturn(mock(Finalizado.class));
    }

    @Test
    public void testIniciarCompra() {
        this.preparacionCompra();
        when(this.recetaMock.getPaciente()).thenReturn(this.pacienteMock);
        when(this.recetaMock.getEstado()).thenReturn(mock(Iniciado.class));
        Map<Integer, Medicamento> medicamentoMap = new HashMap<>(Map.of(1, this.medicamentoSpy1,
                2, this.medicamentoSpy2));
        when(this.recetaMock.getMedicamentos()).thenReturn(medicamentoMap);
        when(this.pacienteMock.comprobarEstado()).thenReturn(false);
        RecetaDAO recetaDAOMock = mock(RecetaDAO.class);
        when(this.ioCConteinerServiceMock.getRecetaDAO()).thenReturn(recetaDAOMock);
        try {
            Compra compra = this.gestionFarmaciaServiceSpy.iniciarCompra(recetaMock);
            for (Medicamento medicamento: List.of(this.medicamentoSpy1, this.medicamentoSpy2)) {
                assertNotSame(compra.getMedicamentos().get(medicamento.getObjectID()), medicamento);
                assertEquals(compra.getMedicamentos().get(medicamento.getObjectID()).getObjectID(),
                        medicamento.getObjectID());
                assertEquals(compra.getMedicamentos().get(medicamento.getObjectID()).getCantidad(),
                        medicamento.getCantidad());
                assertEquals(compra.getMedicamentos().get(medicamento.getObjectID()).getPrecio(),
                        medicamento.getPrecio());
                assertEquals(compra.getMedicamentos().get(medicamento.getObjectID()).getNombre(),
                        medicamento.getNombre());
            }
            assertSame(compra.getPaciente(), this.recetaMock.getPaciente());
            assertInstanceOf(Iniciado.class, compra.getEstado());
            verify(this.recetaMock).setEstado(any(Finalizado.class));
            verify(recetaDAOMock).update(this.recetaMock);
        } catch (NotEnabledToRunException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIniciarCompra_NotEnabledToRunException() {
        when(this.recetaMock.getPaciente()).thenReturn(this.pacienteMock);
        when(this.recetaMock.getEstado()).thenReturn(mock(Finalizado.class));
        assertThrows(NotEnabledToRunException.class, () -> this.gestionFarmaciaServiceSpy
                .iniciarCompra(this.recetaMock));
        when(this.pacienteMock.comprobarEstado()).thenReturn(true);
        assertThrows(NotEnabledToRunException.class, () -> this.gestionFarmaciaServiceSpy
                .iniciarCompra(this.recetaMock));
    }

    @Test
    public void testFinalizarCompra() {
        this.preparacionCompra();
        CompraDAO compraDAOMock = mock(CompraDAO.class);
        when(this.ioCConteinerServiceMock.getCompraDAO()).thenReturn(compraDAOMock);
        when(this.compraMock.getPaciente()).thenReturn(this.pacienteMock);
        when(this.compraMock.getEstado()).thenReturn(mock(Iniciado.class));
        Map<Integer, Medicamento> medicamentoMap = new HashMap<>(Map.of(1, this.medicamentoSpy1,
                2, this.medicamentoSpy2));
        Mockito.doReturn(null).when(this.gestionFarmaciaServiceSpy).verificarStock(any());
        doNothing().when(this.gestionFarmaciaServiceSpy).realizarPedido(any());
        when(this.compraMock.getMedicamentos()).thenReturn(medicamentoMap);
        try {
            this.gestionFarmaciaServiceSpy.finalizarCompra(this.compraMock);
            verify(this.gestionFarmaciaServiceSpy, times(0)).realizarPedido(any());
            verify(this.medicamentoSpy1, times(1)).sacar(5);
            verify(this.medicamentoSpy2, times(1)).sacar(10);
            verify(this.medicamentoDAOMock).update(this.medicamentoSpy1);
            verify(this.medicamentoDAOMock).update(this.medicamentoSpy2);
            verify(this.compraMock).setEstado(any(Finalizado.class));
            verify(compraDAOMock).update(this.compraMock);
        } catch (NotEnabledToRunException | NotEnoughStockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFinalizarCompra_NotEnabledToRunException() {
        when(this.compraMock.getPaciente()).thenReturn(this.pacienteMock);
        when(this.compraMock.getEstado()).thenReturn(mock(Finalizado.class));
        assertThrows(NotEnabledToRunException.class, () -> this.gestionFarmaciaServiceSpy
                .finalizarCompra(this.compraMock));
        when(this.pacienteMock.comprobarEstado()).thenReturn(true);
        assertThrows(NotEnabledToRunException.class, () -> this.gestionFarmaciaServiceSpy
                .finalizarCompra(this.compraMock));
    }

    @Test
    public void testVerCompras() {
        CompraDAO compraDAOMock = mock(CompraDAO.class);
        when(this.ioCConteinerServiceMock.getCompraDAO()).thenReturn(compraDAOMock);
        Compra compraMockTest = mock(Compra.class);
        Map<Integer, Compra> compraMapSim = new HashMap<>(Map.of(9, this.compraMock,12, compraMockTest));
        Mockito.doReturn(compraMapSim).when(compraDAOMock).findAll();
        List<Compra> compraList = this.gestionFarmaciaServiceSpy.verCompras();
        assertSame(2, compraList.size());
        assertTrue(compraList.stream().anyMatch(e -> this.compraMock.equals(e)));
        assertTrue(compraList.stream().anyMatch(compraMockTest::equals));
    }

    @Test
    public void testVerCompras_Paciente() {
        CompraDAO compraDAOMock = mock(CompraDAO.class);
        when(this.ioCConteinerServiceMock.getCompraDAO()).thenReturn(compraDAOMock);
        Paciente pacienteMock = mock(Paciente.class);
        Compra compraMockTest = mock(Compra.class);
        Map<Integer, Compra> compraMapSim = new HashMap<>(Map.of(9, this.compraMock,12, compraMockTest));
        when(ioCConteinerServiceMock.getCompraDAO().findAllByPaciente(pacienteMock)).thenReturn(compraMapSim);
        List<Compra> compraList = this.gestionFarmaciaServiceSpy.verCompras(pacienteMock);
        assertSame(2, compraList.size());
        assertTrue(compraList.stream().anyMatch(e -> this.compraMock.equals(e)));
        assertTrue(compraList.stream().anyMatch(compraMockTest::equals));
    }
}
