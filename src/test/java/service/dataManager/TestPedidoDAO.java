package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.estado.Iniciado;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.PedidoDAO;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TestPedidoDAO extends TestAbstractDAO<PedidoDAO, Pedido> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(PedidoDAO.class);
        this.objectDAOSpy.setPedidoMap(this.objectMapSpy);
        this.objectSpy = spy(new Pedido(new HashMap<>(), mock(Iniciado.class)));
        this.objectSpy2 = spy(new Pedido(new HashMap<>(), mock(Finalizado.class)));
    }

    @Test
    public void testUpdate() {
        Pedido pedidoTestSpy = spy(new Pedido(new HashMap<>(), mock(Finalizado.class)));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(pedidoTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(pedidoTestSpy);
        assertSame(this.objectSpy.getMedicamentos(), pedidoTestSpy.getMedicamentos());
        assertSame(this.objectSpy.getEstado(), pedidoTestSpy.getEstado());
    }
}
