package tr.edu.yildiz.yazilimkalite.librarymanagement.exception;

public class NotExistingEntityException extends RuntimeException {
    private static final long serialVersionUID = -9214474888398409112L;

    private final String entityName;

    public NotExistingEntityException() {
        entityName = "";
    }
    
    public NotExistingEntityException(String message) {
        super(message);
        entityName = "";
    }

    public NotExistingEntityException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName() {
        return this.entityName;
    }

}
