package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class FieldNotMatchingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FieldNotMatchingException() {
        super();
    }

    public FieldNotMatchingException(String message) {
        super(message);
    }

}
