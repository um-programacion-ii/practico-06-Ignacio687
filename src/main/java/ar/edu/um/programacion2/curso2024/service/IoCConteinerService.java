package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.service.dataManager.*;

public class IoCConteinerService implements IoCService {
    public static IoCConteinerService ioCConteiner;

    private IoCConteinerService() { }

    public static IoCConteinerService getInstance() {
        if (ioCConteiner == null) {
            ioCConteiner = new IoCConteinerService();
        }
        return ioCConteiner;
    }

    @Override
    public CompraDAO getCompraDAO() {
        return null;
    }

    @Override
    public EspecialidadDAO getEspecialidadDAO() {
        return null;
    }

    @Override
    public MedicamentoDAO getMedicamentoDAO() {
        return null;
    }

    @Override
    public MedicoDAO getMedicoDAO() {
        return null;
    }

    @Override
    public ObraSocialDAO getObraSocialDAO() {
        return null;
    }

    @Override
    public PacienteDAO getPacienteDAO() {
        return null;
    }

    @Override
    public PedidoDAO getPedidoDAO() {
        return null;
    }

    @Override
    public RecetaDAO getRecetaDAO() {
        return null;
    }

    @Override
    public TurnoDAO getTurnoDAO() {
        return null;
    }

    @Override
    public AtencionMedicoService getAtencionMedicoService() {
        return null;
    }

    @Override
    public GestionDrogueriaService getGestionDrogueriaService() {
        return null;
    }

    @Override
    public GestionFarmaciaService getGestionFarmaciaService() {
        return null;
    }

    @Override
    public GestionTurnoService getGestionTurnoService() {
        return null;
    }

    @Override
    public AtencionParticular getAtencionParticular() {
        return null;
    }
}
