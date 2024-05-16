package ar.edu.um.programacion2.curso2024.entity.customExceptions;

public class NotEnabledToRunException extends Exception {
    public NotEnabledToRunException(String message) {
        super(message);
    }
}
