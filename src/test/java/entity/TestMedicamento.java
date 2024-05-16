package entity;

import ar.edu.um.programacion2.curso2024.entity.Medicamento;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnoughStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMedicamento extends TestORMObject<Medicamento> {
    private Medicamento medicamento1;
    private Medicamento medicamento2;

    @Override
    protected Medicamento crearInstancia() {
        return new Medicamento();
    }

    @BeforeEach
    public void preparacion() {
        this.medicamento1 = new Medicamento("Med-x", 5, 500);
        this.medicamento2 = new Medicamento("RadAway", 12, 1000);
    }

    @Test
    public void testAgregar() {
        this.medicamento1.agregar(5);
        assertEquals(10, this.medicamento1.getCantidad());
        this.medicamento2.agregar(0);
        assertEquals(12, this.medicamento2.getCantidad());
    }

    @Test
    public void testSacar() {
        try {
            this.medicamento1.sacar(5);
            assertEquals(0, this.medicamento1.getCantidad());
            this.medicamento2.sacar(7);
            assertEquals(5, this.medicamento2.getCantidad());
            this.medicamento2.sacar(0);
            assertEquals(5, this.medicamento2.getCantidad());
        } catch (NotEnoughStockException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSacar_NotEnoughStockException() {
        assertThrows(NotEnoughStockException.class, () -> this.medicamento1.sacar(6));
        assertThrows(NotEnoughStockException.class, () -> this.medicamento2.sacar(20));
    }

    @Test
    public void testVerificarStock() {
        assertTrue(this.medicamento1.verificarStock(5));
        assertEquals(5, this.medicamento1.getCantidad());
        assertTrue(this.medicamento1.verificarStock(4));
        assertEquals(5, this.medicamento1.getCantidad());
        assertTrue(this.medicamento2.verificarStock(9));
        assertFalse(this.medicamento1.verificarStock(6));
        assertEquals(5, this.medicamento1.getCantidad());
        assertFalse(this.medicamento2.verificarStock(20));
        assertEquals(12, this.medicamento2.getCantidad());
    }
}
