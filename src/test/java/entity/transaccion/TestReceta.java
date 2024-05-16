package entity.transaccion;

import ar.edu.um.programacion2.curso2024.entity.Medico;
import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Transaccion;

public class TestReceta extends TestTransaccion {

    @Override
    protected Transaccion crearInstancia() {
        return new Receta(this.medicamentoMap, new IniciadoImp(), new Medico(), new Paciente());
    }
}
