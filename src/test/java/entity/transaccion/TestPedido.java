package entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Pedido;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Transaccion;

import java.util.HashMap;

public class TestPedido extends TestTransaccion {

    @Override
    protected Transaccion crearInstancia() {
        return new Pedido(this.medicamentoMap, new IniciadoImp());
    }
}
