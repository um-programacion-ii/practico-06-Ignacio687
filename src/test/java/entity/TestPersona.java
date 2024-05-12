package entity;

import ar.edu.um.programacion2.curso2024.entity.*;
import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TestPersona extends TestORMObject<Persona> {
    protected Receta receta1;
    protected Receta receta2;
    protected Turno turno1;
    protected Turno turno2;
    protected Persona persona;
    protected List<Receta> recetasSpy;
    protected Map<Integer, Turno> turnoMap;

    @Override
    protected abstract Persona crearInstancia();

    @BeforeEach
    public void preparacion() {
        this.turnoMap = new HashMap<>();
        this.recetasSpy = Mockito.spy(new ArrayList<>());
        this.persona = this.crearInstancia();
        this.receta1 = new Receta(new HashMap<>(), new IniciadoImp(), new Medico(), new Paciente());
        this.receta2 = new Receta(new HashMap<>(), new IniciadoImp(), new Medico(), new Paciente());
        this.turno1 = new Turno(new Medico(), new Paciente(), AtencionParticular.obtenerInstancia());
        this.turno2 = new Turno(new Medico(), new Paciente(), AtencionParticular.obtenerInstancia());
    }

    @Test
    public void testGuardarReceta() {
        assertTrue(this.persona.getRecetas().isEmpty());
        this.persona.guardarReceta(this.receta1);
        this.persona.guardarReceta(this.receta2);
        verify(this.recetasSpy).add(this.receta1);
        verify(this.recetasSpy).add(this.receta2);
        verify(this.recetasSpy, times(2)).add(any(Receta.class));
        assertEquals(2, this.persona.getRecetas().size());
        assertSame(this.receta1, this.persona.getRecetas().getFirst());
    }

    @Test
    public void testSacarReceta() {
        this.recetasSpy.add(this.receta1);
        this.recetasSpy.add(this.receta2);
        this.recetasSpy.add(this.receta2);
        assertSame(this.receta1, this.persona.sacarReceta());
        assertEquals(2, this.persona.getRecetas().size());
    }

    @Test
    public void testSacarReceta_SinMasElementos() {
        assertNull(this.persona.sacarReceta());
    }

    @Test
    public void testAgendarTurno() {
        try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
            this.persona.agendarTurno(this.turno1);
            this.persona.agendarTurno(this.turno2);
            mockedStatic.verify(() -> ORMMapManager.addObject(this.turno1, this.turnoMap));
            mockedStatic.verify(() -> ORMMapManager.addObject(this.turno2, this.turnoMap));
            mockedStatic.verify(() -> ORMMapManager.addObject(any(Turno.class),
                    any(Map.class)), times(2));
        }
    }

    @Test
    public void testEliminarTurno() {
        try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
            this.persona.eliminarTurno(this.turno1);
            this.persona.eliminarTurno(this.turno2);
            mockedStatic.verify(() -> ORMMapManager.delObject(this.turno1, this.turnoMap));
            mockedStatic.verify(() -> ORMMapManager.delObject(this.turno2, this.turnoMap));
            mockedStatic.verify(() -> ORMMapManager.delObject(any(Turno.class),
                    any(Map.class)), times(2));
        } catch (ObjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEliminarTurno_ObjectNotFoundException() {
        assertThrows(ObjectNotFoundException.class, () -> {
            try (MockedStatic<ORMMapManager> mockedStatic = mockStatic(ORMMapManager.class)) {
                mockedStatic.when(() -> ORMMapManager.delObject(this.turno1, this.turnoMap))
                        .thenThrow(new ObjectNotFoundException(""));
                this.persona.eliminarTurno(this.turno1);
                mockedStatic.verify(() -> ORMMapManager.delObject(this.turno1, this.turnoMap));
            }
        });
    }
}
