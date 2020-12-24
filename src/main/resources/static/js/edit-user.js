$(document).ready(function () {
    const formChangeSubmitButton = $("#password-change-form-submit");

    if(formChangeSubmitButton) {
        formChangeSubmitButton.click(function (e) {
            e.preventDefault();

            $("#no-password-match").remove();

            const newPassword = $("#new-password").val();
            const newPasswordConfirm = $("#new-password-confirm").val();

            if (newPassword !== newPasswordConfirm ) {
                $(".modal-body").prepend("<div class='alert alert-danger' id='no-password-match'>\n" + 
                                            "Yeni şifre tekrarıyla eşleşmiyor.\n" +
                                        "</div>");
            } else {
                $("#password-change-form").submit();
            }
        });
    }
});