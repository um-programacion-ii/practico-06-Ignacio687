package service.dataManager;

import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.ObraSocialDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TestObraSocialDAO extends TestAbstractDAO<ObraSocialDAO, ObraSocial> {

    @Override
    protected void preparacionParticular(IoCConteinerService ioCConteinerServiceMock) {
        this.objectDAOSpy = spy(ObraSocialDAO.class);
        this.objectDAOSpy.setObraSocialMap(this.objectMapSpy);
        this.objectSpy = spy(new ObraSocial("Medica-farm"));
        this.objectSpy2 = spy(new ObraSocial("UnixMed"));
    }

    @Test
    public void testUpdate() {
        ObraSocial obraSocialTestSpy = spy(new ObraSocial("Sancor"));
        when(this.objectMapSpy.get(1)).thenReturn(this.objectSpy);
        when(obraSocialTestSpy.getObjectID()).thenReturn(1);
        this.objectDAOSpy.update(obraSocialTestSpy);
        assertSame(this.objectSpy.getNombre(), obraSocialTestSpy.getNombre());
    }
}
