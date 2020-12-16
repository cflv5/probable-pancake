package tr.edu.yildiz.yazilimkalite.librarymanagement.util;

public class ViewConstants {
    public static final String FRAGMENT = "fragment";
    public static final String BOILERPLATE = "boilerplate";
    public static final String UPDATE_FORM = "updateForm";
    public static final String WRITER = "writer";
    public static final String PUBLISHER = "publisher";
	public static final String MEMBER = "member";
	public static final String BORROWING = "borrowing";
	public static final String SUCCESS = "success";
	public static final String BOOK = "book";
	public static final String SUCCESS_MESSAGE = "successMessage";

    public static class Error {
        public static final String HAS_ERROR = "hasError";
        public static final String INTEGRITY_ERROR = "integrityError";
        public static final String ERROR_MESSAGE = "errorMessage";
        public static final String MEMBER_STATUS_ERROR = "memberStatusError";

        private Error() {
            super();
        }

    }

    private ViewConstants() {
        super();
    }
}
