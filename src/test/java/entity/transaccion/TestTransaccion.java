package entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Transaccion;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import entity.TestORMObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TestTransaccion extends TestORMObject<Transaccion> {
    protected Medicamento medicamento;
    protected Medicamento medicamento1;
    Map<Integer, Medicamento> medicamentoMap;
    protected Transaccion transaccion;

    @Override
    protected abstract Transaccion crearInstancia();

    @BeforeEach
    public void preparacion() {
        this.medicamentoMap = new HashMap<>();
        this.transaccion = crearInstancia();
        this.medicamento = new Medicamento();
        this.medicamento1 = new Medicamento("Med-x", 2, 400);
    }

    @Test
    public void testAgregarMedicamento() {
        try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
            this.transaccion.agregarMedicamento(this.medicamento);
            this.transaccion.agregarMedicamento(this.medicamento1);
            mockedStatic.verify(() -> ORMMapManager.addObject(this.medicamento, this.medicamentoMap));
            mockedStatic.verify(() -> ORMMapManager.addObject(this.medicamento1, this.medicamentoMap));
            mockedStatic.verify(() -> ORMMapManager.addObject(any(Medicamento.class),
                            any(Map.class)), times(2));
        }
    }

    @Test
    public void testEliminarMedicamento() {
        try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
            this.transaccion.eliminarMedicamento(this.medicamento);
            this.transaccion.eliminarMedicamento(this.medicamento1);
            mockedStatic.verify(() -> ORMMapManager.delObject(this.medicamento, this.medicamentoMap));
            mockedStatic.verify(() -> ORMMapManager.delObject(this.medicamento1, this.medicamentoMap));
            mockedStatic.verify(() -> ORMMapManager.delObject(any(Medicamento.class),
                    any(Map.class)), times(2));
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEliminarMedicamento_ObjectNotFoundException() {
        assertThrows(ObjectNotFoundException.class, () -> {
        try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
            mockedStatic.when(() -> ORMMapManager.delObject(this.medicamento1, this.medicamentoMap))
                    .thenThrow(new ObjectNotFoundException(""));
            this.transaccion.eliminarMedicamento(this.medicamento1);
            mockedStatic.verify(() -> ORMMapManager.delObject(this.medicamento1, this.medicamentoMap));
        }
        });
    }
}
