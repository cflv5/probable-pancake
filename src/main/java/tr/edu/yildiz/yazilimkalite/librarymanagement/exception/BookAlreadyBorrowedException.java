package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class BookAlreadyBorrowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String bookName;

    public BookAlreadyBorrowedException(String message, String bookId) {
        super(message);
        this.bookName = bookId;
    }

    public String getBookName() {
        return this.bookName;
    }

}
