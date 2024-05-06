package ar.edu.um.programacion2.curso2024.entity.atencion;

public class AtencionParticular implements Atencion {
    private static AtencionParticular instanciaDeClase;

    private AtencionParticular() {}

    public static AtencionParticular obtenerInstancia() {
        if (instanciaDeClase == null) {
            instanciaDeClase = new AtencionParticular();
        }
        return instanciaDeClase;
    }

    @Override
    public void registrarAtencion() {
        // Posible implementación de atención particular, podría generar una factura o algo por el estilo.
    }
}
