package entity.atencion;

import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestObraSocial {
    ObraSocial obraSocial;

    @BeforeEach
    public void preparacion() {
        this.obraSocial = new ObraSocial("Medex");
    }

    @Test
    public void verificarObjectIDUnico() {
        ObraSocial obraSocial1 = new ObraSocial();
        ObraSocial obraSocial2 = new ObraSocial("Medex");
        assertNotEquals(this.obraSocial.getObjectID(), obraSocial1.getObjectID());
        assertNotEquals(this.obraSocial.getObjectID(), obraSocial2.getObjectID());
    }

    @Test
    public void testRegistrarAtencion() {
        this.obraSocial.registrarAtencion();
    }
}
