package entity.atencion;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestAtencionParticular {

    @Test
    public void testVerificarInstanciaUnica() {
        AtencionParticular atencionParticular1 = AtencionParticular.obtenerInstancia();
        AtencionParticular atencionParticular2 = AtencionParticular.obtenerInstancia();
        assertSame(atencionParticular1, atencionParticular2);
    }

    @Test
    public void testRegistrarAtencion() {
        AtencionParticular.obtenerInstancia().registrarAtencion();
    }
}
