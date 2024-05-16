package entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Transaccion;

import java.util.HashMap;

public class TestCompra extends TestTransaccion {

    @Override
    protected Transaccion crearInstancia() {
        return new Compra(this.medicamentoMap, new IniciadoImp(), new Paciente());
    }
}
