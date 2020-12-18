package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class NonUpdatableFieldException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String name;

    public NonUpdatableFieldException(String name) {
        this.name = name;
    }

    public NonUpdatableFieldException(String message, String name) {
        super(message);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}