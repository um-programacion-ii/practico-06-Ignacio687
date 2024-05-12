package ar.edu.um.programacion2.curso2024.entity;

import ar.edu.um.programacion2.curso2024.service.IoCConteinerService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Main {

    public void start() {
        IoCConteinerService.obtenerInstancia();
        // Código de inicio del servicio.
    }

    public void finish() {
        // Código de terminación del servicio.
    }
}
