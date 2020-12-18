package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
    
}
