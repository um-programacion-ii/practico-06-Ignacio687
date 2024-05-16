package ar.edu.um.programacion2.curso2024.entity.customExceptions;

public class NotEnoughStockException extends Exception {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
