package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class ImproperMemberStatusException extends RuntimeException{
    private static final long serialVersionUID = -4873394791759140902L;


    public ImproperMemberStatusException() {
        super();
    }

    public ImproperMemberStatusException(String message) {
        super(message);
    }


}
