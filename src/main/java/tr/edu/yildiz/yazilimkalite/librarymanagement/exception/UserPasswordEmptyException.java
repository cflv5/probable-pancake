package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class UserPasswordEmptyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserPasswordEmptyException() {
    }

    public UserPasswordEmptyException(String message) {
        super(message);
    }
    
}
