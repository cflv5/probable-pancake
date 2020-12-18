package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class NoMoreExtensionAllowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoMoreExtensionAllowedException() {
        super();
    }

    public NoMoreExtensionAllowedException(String message) {
        super(message);
    }
}
