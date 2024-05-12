package entity.atencion;

import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import entity.TestORMObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestObraSocial extends TestORMObject<ObraSocial> {
    ObraSocial obraSocial;

    @Override
    protected ObraSocial crearInstancia() {
        return new ObraSocial();
    }

    @BeforeEach
    public void preparacion() {
        this.obraSocial = this.crearInstancia();
    }

    @Test
    public void testRegistrarAtencion() {
        this.obraSocial.registrarAtencion();
    }
}
