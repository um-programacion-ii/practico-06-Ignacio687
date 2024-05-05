package ar.edu.um.programacion2.curso2024.entity.atencion;

import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ObraSocial extends ORMObject implements Atencion{
    private String nombre;

    @Override
    public void registrarAtencion() {

    }
}
