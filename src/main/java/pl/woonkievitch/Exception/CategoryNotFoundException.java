package pl.woonkievitch.Exception;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("Categories not added yet.");
    }
}
