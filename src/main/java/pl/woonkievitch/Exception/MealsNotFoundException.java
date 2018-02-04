package pl.woonkievitch.Exception;

public class MealsNotFoundException extends Exception {
    public MealsNotFoundException() {
        super("Meals not added yet.");
    }
}
