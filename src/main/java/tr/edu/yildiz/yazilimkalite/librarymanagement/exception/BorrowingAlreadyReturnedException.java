package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class BorrowingAlreadyReturnedException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public BorrowingAlreadyReturnedException() {
        super();
    }

    public BorrowingAlreadyReturnedException(String message) {
        super(message);
    }
}
