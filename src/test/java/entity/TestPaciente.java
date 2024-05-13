package entity;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.Persona;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.estado.LibreImp;
import ar.edu.um.programacion2.curso2024.entity.estado.OcupadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestPaciente extends TestPersona {
    private Paciente paciente;
    private List<Compra> comprasSpy;
    private Compra compra1;
    private Compra compra2;

    @Override
    protected Persona crearInstancia() {
        return new Paciente("Pepe", 20, this.recetasSpy, this.turnoMap, new ObraSocial(), new LibreImp(),
                this.comprasSpy);
    }

    @BeforeEach
    public void preparacionPaciente() {
        this.comprasSpy = Mockito.spy(new ArrayList<>());
        this.paciente = (Paciente) this.crearInstancia();
        this.compra1 = new Compra(new HashMap<>(), new IniciadoImp(), this.paciente);
        this.compra2 = new Compra(new HashMap<>(), new IniciadoImp(), this.paciente);
    }

    @Test
    public void testGuardarCompra() {
        assertTrue(this.paciente.getCompras().isEmpty());
        this.paciente.guardarCompra(this.compra1);
        this.paciente.guardarCompra(this.compra2);
        verify(this.comprasSpy).add(this.compra1);
        verify(this.comprasSpy).add(this.compra2);
        verify(this.comprasSpy, times(2)).add(any(Compra.class));
        assertEquals(2, this.paciente.getCompras().size());
        assertSame(this.compra1, this.paciente.getCompras().getFirst());
    }

    @Test
    public void testSacarCompra() {
        this.comprasSpy.add(this.compra1);
        this.comprasSpy.add(this.compra2);
        this.comprasSpy.add(this.compra2);
        assertSame(this.compra1, this.paciente.sacarCompra());
        assertEquals(2, this.paciente.getCompras().size());
    }

    @Test
    public void testSacarCompra_SinMasElementos() {
        assertNull(this.paciente.sacarCompra());
    }

    @Test
    public void testComprobarEstado() {
        assertFalse(this.paciente.comprobarEstado());
        this.paciente.setEstado(new OcupadoImp());
        assertTrue(this.paciente.comprobarEstado());
    }
}
