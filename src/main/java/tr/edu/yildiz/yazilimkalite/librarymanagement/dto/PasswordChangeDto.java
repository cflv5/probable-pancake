package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import javax.validation.constraints.NotBlank;

public class PasswordChangeDto {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    public PasswordChangeDto() {
        super();
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
