package service;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.estado.Finalizado;
import ar.edu.um.programacion2.curso2024.entity.estado.Iniciado;
import ar.edu.um.programacion2.curso2024.entity.estado.Libre;
import ar.edu.um.programacion2.curso2024.entity.estado.Ocupado;
import ar.edu.um.programacion2.curso2024.service.*;
import ar.edu.um.programacion2.curso2024.service.dataManager.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestIoCConteinerService {
    private IoCConteinerService ioCConteinerService;

    @BeforeEach
    public void preparacion() {
        this.ioCConteinerService = IoCConteinerService.obtenerInstancia();
    }

    @Test
    public void testGetCompraDAO() {
        assertSame(this.ioCConteinerService.getCompraDAO(), CompraDAO.obtenerInstancia());
    }

    @Test
    public void testGetEspecialidadDAO() {
        assertSame(this.ioCConteinerService.getEspecialidadDAO(), EspecialidadDAO.obtenerInstancia());
    }

    @Test
    public void testGetMedicamentoDAO() {
        assertSame(this.ioCConteinerService.getMedicamentoDAO(), MedicamentoDAO.obtenerInstancia());
    }

    @Test
    public void testGetMedicoDAO() {
        assertSame(this.ioCConteinerService.getMedicoDAO(), MedicoDAO.obtenerInstancia());
    }

    @Test
    public void testGetObraSocialDAO() {
        assertSame(this.ioCConteinerService.getObraSocialDAO(), ObraSocialDAO.obtenerInstancia());
    }

    @Test
    public void testGetPacienteDAO() {
        assertSame(this.ioCConteinerService.getPacienteDAO(), PacienteDAO.obtenerInstancia());
    }

    @Test
    public void testGetPedidoDAO() {
        assertSame(this.ioCConteinerService.getPedidoDAO(), PedidoDAO.obtenerInstancia());
    }

    @Test
    public void testGetRecetaDAO() {
        assertSame(this.ioCConteinerService.getRecetaDAO(), RecetaDAO.obtenerInstancia());
    }

    @Test
    public void testGetTurnoDAO() {
        assertSame(this.ioCConteinerService.getTurnoDAO(), TurnoDAO.obtenerInstancia());
    }

    @Test
    public void testGetAtencionMedicoService() {
        assertSame(this.ioCConteinerService.getAtencionMedicoService(), AtencionMedicoService.obtenerInstancia());
    }

    @Test
    public void testGetGestionDrogueriaService() {
        assertSame(this.ioCConteinerService.getGestionDrogueriaService(), GestionDrogueriaService.obtenerInstancia());
    }

    @Test
    public void testGetGestionFarmaciaService() {
        assertSame(this.ioCConteinerService.getGestionFarmaciaService(), GestionFarmaciaService.obtenerInstancia());
    }

    @Test
    public void testGetGestionTurnoService() {
        assertSame(this.ioCConteinerService.getGestionTurnoService(), GestionTurnoService.obtenerInstancia());
    }

    @Test
    public void testGetAtencionParticular() {
        assertSame(this.ioCConteinerService.getAtencionParticular(), AtencionParticular.obtenerInstancia());
    }

    @Test
    public void testGetORMMapManager() {
        assertSame(this.ioCConteinerService.getORMMapManager(), ORMMapManager.obtenerInstancia());
    }

    @Test
    public void testGetEstadoFinalizado() {
        Finalizado objInstace1 = this.ioCConteinerService.getEstadoFinalizado();
        Finalizado objInstace2 = this.ioCConteinerService.getEstadoFinalizado();
        assertSame(objInstace1, objInstace2);
    }

    @Test
    public void testGetEstadoIniciado() {
        Iniciado objInstace1 = this.ioCConteinerService.getEstadoIniciado();
        Iniciado objInstace2 = this.ioCConteinerService.getEstadoIniciado();
        assertSame(objInstace1, objInstace2);
    }

    @Test
    public void testGetEstadoLibre() {
        Libre objInstace1 = this.ioCConteinerService.getEstadoLibre();
        Libre objInstace2 = this.ioCConteinerService.getEstadoLibre();
        assertSame(objInstace1, objInstace2);
    }

    @Test
    public void testGetEstadoOcupado() {
        Ocupado objInstace1 = this.ioCConteinerService.getEstadoOcupado();
        Ocupado objInstace2 = this.ioCConteinerService.getEstadoOcupado();
        assertSame(objInstace1, objInstace2);
    }
}
