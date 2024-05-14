package ar.edu.um.programacion2.curso2024.service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.Paciente;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Compra;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompraDAO extends AbstractDAO<Compra> {
    private static CompraDAO instanciaDeClase;
    private Map<Integer, Compra> compraMap;

    private CompraDAO() {
        this.compraMap = new LinkedHashMap<>();
        this.objectMap = this.compraMap;
    }

    public static CompraDAO obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new CompraDAO();
        }
        return instanciaDeClase;
    }

    public void setCompraMap(Map<Integer, Compra> compraMap) {
        this.compraMap = compraMap;
        this.objectMap = compraMap;
    }

    @Override
    protected Compra generarCopiaObjeto(Compra objeto) {
        return new Compra(objeto.getMedicamentos(), objeto.getEstado(), objeto.getPaciente());
    }

    @Override
    public Compra findById(int id) {
        Compra compra = this.compraMap.get(id);
        if (compra != null) {
            Paciente paciente = IoCConteinerService.obtenerInstancia().getPacienteDAO()
                    .findById(compra.getPaciente().getObjectID());
            if (paciente != null) {
                compra.setPaciente(paciente);
            }
            Compra compraCopia = this.generarCopiaObjeto(compra);
            compraCopia.setObjectID(compra.getObjectID());
            return compraCopia;
        }
        return null;
    }

    public Map<Integer, Compra> findAllByPaciente(Paciente paciente) {
        Map<Integer, Compra> compraMapCopia = new HashMap<>();
        for (Compra compra: this.compraMap.values()) {
            if (compra.getPaciente().getObjectID() == paciente.getObjectID()) {
                Compra compraCopia = this.findById(compra.getObjectID());
                compraMapCopia.put(compraCopia.getObjectID(), compraCopia);
            }
        }
        return compraMapCopia;
    }

    @Override
    public void update(Compra compra) {
        Compra compraLocal = this.compraMap.get(compra.getObjectID());
        if (compraLocal != null) {
            compraLocal.setPaciente(compra.getPaciente());
            compraLocal.setEstado(compra.getEstado());
            compraLocal.setMedicamentos(compra.getMedicamentos());
        } else {
            this.save(compra);
        }
    }
}
