package pl.kowalskidawid.skishop.exeption;

public class NotEnoughInStockException extends Exception {
    public NotEnoughInStockException(String message) {
        super(message);
    }
}
