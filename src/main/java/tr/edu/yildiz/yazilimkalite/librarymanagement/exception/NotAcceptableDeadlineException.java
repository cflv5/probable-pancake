package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class NotAcceptableDeadlineException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public NotAcceptableDeadlineException() {
        super();
    }

    public NotAcceptableDeadlineException(String message) {
        super(message);
    }
}
