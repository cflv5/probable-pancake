package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class ForwardRecordException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForwardRecordException() {
        super();
    }

    public ForwardRecordException(String message) {
        super(message);
    }
}
