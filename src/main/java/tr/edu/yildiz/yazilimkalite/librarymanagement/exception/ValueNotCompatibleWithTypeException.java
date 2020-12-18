package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class ValueNotCompatibleWithTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValueNotCompatibleWithTypeException() {
        super();
    }

    public ValueNotCompatibleWithTypeException(String message) {
        super(message);
    }

}
