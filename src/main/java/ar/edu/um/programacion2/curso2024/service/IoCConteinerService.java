package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.estado.*;
import ar.edu.um.programacion2.curso2024.service.dataManager.*;

public class IoCConteinerService implements IoCService {
    private static IoCConteinerService ioCConteinerInstancia;
    private static Cancelado canceladoInstancia;
    private static Finalizado finalizadoInstancia;
    private static Iniciado iniciadoInstancia;
    private static Libre libreInstancia;
    private static Ocupado ocupadoInstancia;

    private IoCConteinerService() { }

    public static IoCConteinerService obtenerInstancia() {
        if (ioCConteinerInstancia == null) {
            ioCConteinerInstancia = new IoCConteinerService();
        }
        return ioCConteinerInstancia;
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

    @Override
    public ORMMapManager getORMMapManager() {
        return null;
    }

    @Override
    public Cancelado getEstadoCancelado() {
        return null;
    }

    @Override
    public Finalizado getEstadoFinalizado() {
        return null;
    }

    @Override
    public Iniciado getEstadoIniciado() {
        return null;
    }

    @Override
    public Libre getEstadoLibre() {
        return null;
    }

    @Override
    public Ocupado getEstadoOcupado() {
        return null;
    }
}
