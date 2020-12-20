package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class AllowedSizeExceededException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String value;

    public AllowedSizeExceededException(String message, String value) {
        super(message);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
