package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class MemberNotExistException extends NotExistingEntityException{
    private static final long serialVersionUID = -2955697850377801661L;
    

    public MemberNotExistException() {
        super();
    }

    public MemberNotExistException(String message) {
        super(message);
    }

}
