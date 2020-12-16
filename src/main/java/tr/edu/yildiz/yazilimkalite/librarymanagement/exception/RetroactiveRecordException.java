package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class RetroactiveRecordException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public RetroactiveRecordException() {
        super();
    }

    public RetroactiveRecordException(String message) {
        super(message);
    }
}
