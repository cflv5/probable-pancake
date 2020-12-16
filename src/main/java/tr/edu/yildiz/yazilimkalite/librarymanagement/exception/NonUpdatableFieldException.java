package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class NonUpdatableFieldException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NonUpdatableFieldException() {
    }

    public NonUpdatableFieldException(String message) {
        super(message);
    }
    
}