package pl.woonkievitch.Exception;

public class EmptyOrderException extends Exception {
    public EmptyOrderException() {
        super("You forgot add porduct to order");
    }
}
