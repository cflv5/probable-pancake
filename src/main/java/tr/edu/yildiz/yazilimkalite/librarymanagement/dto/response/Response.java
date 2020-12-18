package tr.edu.yildiz.yazilimkalite.librarymanagement.dto.response;

public class Response<T> {
    protected boolean success;
    protected T data;
    protected String message;

    public Response() {
        super();
    }

    public Response(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
