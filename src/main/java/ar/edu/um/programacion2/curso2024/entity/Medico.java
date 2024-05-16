package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.entity.atencion.AtencionParticular;
import ar.edu.um.programacion2.curso2024.entity.atencion.ObraSocial;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.NotEnabledToRunException;
import ar.edu.um.programacion2.curso2024.entity.customExceptions.ObjectNotFoundException;
import ar.edu.um.programacion2.curso2024.entity.estado.IniciadoImp;
import ar.edu.um.programacion2.curso2024.entity.transaccion.Receta;
import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import ar.edu.um.programacion2.curso2024.service.dataManager.ORMMapManager;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Medico extends Persona {
    private Map<Integer, ObraSocial> obraSocialMap;
    private Especialidad especialidad;
    private AtencionParticular atencionParticular;
    private static List<String> nombresMedicamentos = new ArrayList<>(Arrays.asList(
            "Paracetamol",
            "Ibuprofeno",
            "Amoxicilina",
            "Omeprazol",
            "Loratadina",
            "Diazepam",
            "Atorvastatina",
            "Tramadol",
            "Metformina",
            "Sertralina",
            "Fluoxetina",
            "Ciprofloxacino",
            "Prednisona",
            "Ranitidina",
            "Diclofenaco",
            "Clonazepam",
            "Levotiroxina",
            "Metronidazol",
            "Losartán",
            "Lisinopril"
    ));

    public Medico(String nombre, Integer edad, List<Receta> recetas, Map<Integer, Turno> turnos,
                  Map<Integer, ObraSocial> obraSocialMap, Especialidad especialidad, AtencionParticular atencionParticular) {
        super(nombre, edad, recetas, turnos);
        this.obraSocialMap = obraSocialMap;
        this.especialidad = especialidad;
        this.atencionParticular = atencionParticular;
    }

    public Receta atender(Turno turno) throws ObjectNotFoundException, NotEnabledToRunException {
        if (this.turnos.get(turno.getObjectID()) == null) {
            throw new ObjectNotFoundException("El turno "+turno.getObjectID()+
                    " no le pertenece al medico "+this.objectID);
        } else if (turno.getTipoDeAtencion() instanceof AtencionParticular) {
            return this.atenderParticular(turno);
        } else if (this.obraSocialMap.get(turno.getTipoDeAtencion().getObjectID()) == null) {
            throw new NotEnabledToRunException("El medico "+this.objectID+" no atiende la obra social ID: "
                    +turno.getTipoDeAtencion().getObjectID());
        } else {
            this.especialidad.tratar();
            this.obraSocialMap.get(turno.getTipoDeAtencion().getObjectID()).registrarAtencion();
            this.eliminarTurno(turno);
            return this.generarReceta(turno);
        }
    }

    private Receta atenderParticular(Turno turno) throws NotEnabledToRunException {
        if (!this.habilitadoAtencionParticular()) {
            throw new NotEnabledToRunException("El medico "+this.objectID+" no atiende de forma particular");
        } else {
            this.especialidad.tratar();
            this.atencionParticular.registrarAtencion();
            try {
                this.eliminarTurno(turno);
            } catch (ObjectNotFoundException e) {
                throw new RuntimeException(e);
            }
            return this.generarReceta(turno);
        }
    }

    public boolean habilitadoAtencionParticular() {
        return this.atencionParticular != null;
    }

    public Receta generarReceta(Turno turno) {
        Random random = new Random();
        if (random.nextInt(3) == 0) {
            return null;
        } else {
            Receta receta = new Receta(new HashMap<>(), new IniciadoImp(),
                    turno.getMedico(), turno.getPaciente());
            for (int counter=0; counter<(random.nextInt(3)+1); counter++) {
                receta.agregarMedicamento(new Medicamento(
                        nombresMedicamentos.get(random.nextInt(nombresMedicamentos.size())),
                        random.nextInt(4)+1,
                        random.nextInt(100, 1000)));
            }
            return receta;
        }
    }

    public void agregarObraSocial(ObraSocial obraSocial) {
        ORMMapManager.addObject(obraSocial, this.obraSocialMap);
    }

    public void eliminarObraSocial(ObraSocial obraSocial) throws ObjectNotFoundException {
        ORMMapManager.delObject(obraSocial, this.obraSocialMap);
    }
}
