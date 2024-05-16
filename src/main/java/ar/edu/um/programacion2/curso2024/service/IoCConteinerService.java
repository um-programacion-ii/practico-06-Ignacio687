package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.estado.*;
import ar.edu.um.programacion2.curso2024.service.dataManager.*;

public class IoCConteinerService implements IoCService {
    private static IoCConteinerService ioCConteinerInstancia;
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
        return CompraDAO.obtenerInstancia();
    }

    @Override
    public EspecialidadDAO getEspecialidadDAO() {
        return EspecialidadDAO.obtenerInstancia();
    }

    @Override
    public MedicamentoDAO getMedicamentoDAO() {
        return MedicamentoDAO.obtenerInstancia();
    }

    @Override
    public MedicoDAO getMedicoDAO() {
        return MedicoDAO.obtenerInstancia();
    }

    @Override
    public ObraSocialDAO getObraSocialDAO() {
        return ObraSocialDAO.obtenerInstancia();
    }

    @Override
    public PacienteDAO getPacienteDAO() {
        return PacienteDAO.obtenerInstancia();
    }

    @Override
    public PedidoDAO getPedidoDAO() {
        return PedidoDAO.obtenerInstancia();
    }

    @Override
    public RecetaDAO getRecetaDAO() {
        return RecetaDAO.obtenerInstancia();
    }

    @Override
    public TurnoDAO getTurnoDAO() {
        return TurnoDAO.obtenerInstancia();
    }

    @Override
    public AtencionMedicoService getAtencionMedicoService() {
        return AtencionMedicoService.obtenerInstancia();
    }

    @Override
    public GestionDrogueriaService getGestionDrogueriaService() {
        return GestionDrogueriaService.obtenerInstancia();
    }

    @Override
    public GestionFarmaciaService getGestionFarmaciaService() {
        return GestionFarmaciaService.obtenerInstancia();
    }

    @Override
    public GestionTurnoService getGestionTurnoService() {
        return GestionTurnoService.obtenerInstancia();
    }

    @Override
    public AtencionParticular getAtencionParticular() {
        return AtencionParticular.obtenerInstancia();
    }

    @Override
    public ORMMapManager getORMMapManager() {
        return ORMMapManager.obtenerInstancia();
    }

    @Override
    public Finalizado getEstadoFinalizado() {
        if (finalizadoInstancia == null) {
            finalizadoInstancia = new FinalizadoImp();
        }
        return finalizadoInstancia;
    }

    @Override
    public Iniciado getEstadoIniciado() {
        if (iniciadoInstancia == null) {
            iniciadoInstancia = new IniciadoImp();
        }
        return iniciadoInstancia;
    }

    @Override
    public Libre getEstadoLibre() {
        if (libreInstancia == null) {
            libreInstancia = new LibreImp();
        }
        return libreInstancia;
    }

    @Override
    public Ocupado getEstadoOcupado() {
        if (ocupadoInstancia == null) {
            ocupadoInstancia = new OcupadoImp();
        }
        return ocupadoInstancia;
    }
}
