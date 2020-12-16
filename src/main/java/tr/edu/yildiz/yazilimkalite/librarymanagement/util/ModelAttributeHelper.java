package tr.edu.yildiz.yazilimkalite.librarymanagement.util;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ModelAttributeHelper {
    private ModelAttributeHelper() {
        super();
    }

    private static final String ERROR_NOTFOUND_PATH = "error/notfound";

    public static void addNotFoundFragmentAndErrorMessage(Model model, String message) {
        model.addAttribute(ViewConstants.FRAGMENT, ERROR_NOTFOUND_PATH);
        model.addAttribute(ViewConstants.Error.ERROR_MESSAGE, message);
    }

    public static void addFlashAttrNotFoundFragmentAndErrorMessage(RedirectAttributes model, String message) {
        model.addFlashAttribute(ViewConstants.FRAGMENT, ERROR_NOTFOUND_PATH);
        model.addFlashAttribute(ViewConstants.Error.ERROR_MESSAGE, message);
    }

    public static void addFlashAttrHasErrorIntegrityErrorAndErrorMessage(RedirectAttributes model, String message) {
        model.addFlashAttribute(ViewConstants.Error.INTEGRITY_ERROR, true);
        model.addFlashAttribute(ViewConstants.Error.HAS_ERROR, true);
        model.addFlashAttribute(ViewConstants.Error.ERROR_MESSAGE, message);
    }
}
