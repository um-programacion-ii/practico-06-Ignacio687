package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.MedicamentoDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestMedicamentoDAO extends TestAbstractDAO<MedicamentoDAO, Medicamento> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(MedicamentoDAO.class);
        this.objectDAOSpy.setMedicamentoMap(this.objectMapSpy);
        this.objectSpy = spy(new Medicamento("Med-x", 5, 100));
        this.objectSpy2 = spy(new Medicamento("Ibuprofeno", 20, 50));
    }

    @Test
    public void testUpdate() {
        Medicamento medicamentoTestSpy = spy(new Medicamento("RadAway", 2, 70));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(medicamentoTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(medicamentoTestSpy);
        assertSame(this.objectSpy.getNombre(), medicamentoTestSpy.getNombre());
        assertSame(this.objectSpy.getCantidad(), medicamentoTestSpy.getCantidad());
        assertSame(this.objectSpy.getPrecio(), medicamentoTestSpy.getPrecio());
    }
}
