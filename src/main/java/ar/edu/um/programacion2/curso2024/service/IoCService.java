package ar.edu.um.programacion2.curso2024.service;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.estado.*;
import ar.edu.um.programacion2.curso2024.service.dataManager.*;

public interface IoCService {
    CompraDAO getCompraDAO();
    EspecialidadDAO getEspecialidadDAO();
    MedicamentoDAO getMedicamentoDAO();
    MedicoDAO getMedicoDAO();
    ObraSocialDAO getObraSocialDAO();
    PacienteDAO getPacienteDAO();
    PedidoDAO getPedidoDAO();
    RecetaDAO getRecetaDAO();
    TurnoDAO getTurnoDAO();
    AtencionMedicoService getAtencionMedicoService();
    GestionDrogueriaService getGestionDrogueriaService();
    GestionFarmaciaService getGestionFarmaciaService();
    GestionTurnoService getGestionTurnoService();
    AtencionParticular getAtencionParticular();
    ORMMapManager getORMMapManager();
    Cancelado getEstadoCancelado();
    Finalizado getEstadoFinalizado();
    Iniciado getEstadoIniciado();
    Libre getEstadoLibre();
    Ocupado getEstadoOcupado();
}
