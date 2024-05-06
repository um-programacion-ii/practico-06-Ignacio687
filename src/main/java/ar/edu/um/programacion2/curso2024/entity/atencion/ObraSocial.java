package ar.edu.um.programacion2.curso2024.entity.atencion;

import ar.edu.um.programacion2.curso2024.entity.ORMObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ObraSocial extends ORMObject implements Atencion {
    private String nombre;

    public ObraSocial(String nombre) {
        super();
        this.nombre = nombre;
    }

    @Override
    public void registrarAtencion() {
        // Posible implementación de atención por obra social, podría generar una factura o comprobante para presentar
        // en la obra social.
    }
}
