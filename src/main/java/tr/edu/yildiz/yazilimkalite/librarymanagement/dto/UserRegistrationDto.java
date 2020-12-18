package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.UserStatus;

public class UserRegistrationDto {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    private String email;

    private String password;

    @NotNull
    private UserStatus status;

    @NotEmpty
    private List<Long> roles;

    public UserRegistrationDto() {
        super();
    }

    public UserRegistrationDto name(String name) {
        this.name = name;
        return this;
    }

    public UserRegistrationDto surname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserRegistrationDto email(String email) {
        this.email = email;
        return this;
    }

    public UserRegistrationDto password(String password) {
        this.password = password;
        return this;
    }

    public UserRegistrationDto status(UserStatus status) {
        this.status = status;
        return this;
    }

    public UserRegistrationDto roles(List<Long> roles) {
        this.roles = roles;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return this.status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Long> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserRegistrationDto [email=" + email + ", name=" + name + ", password=" + password + ", roles=" + roles
                + ", status=" + status + ", surname=" + surname + "]";
    }

}
