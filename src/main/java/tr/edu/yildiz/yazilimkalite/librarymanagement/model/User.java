package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.hibernate.annotations.CreationTimestamp;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.UserRegistrationDto;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRole> roles;

    @CreationTimestamp
    private Timestamp creationTime;

    public User() {
        super();
    }

	public static User of(UserRegistrationDto userToSave) {
        return new User()
                    .email(userToSave.getEmail()).name(userToSave.getName())
                    .surname(userToSave.getSurname()).status(userToSave.getStatus())
                    .password(userToSave.getPassword());
    }
    public User id(Long id) {
        this.id = id;
        return this;
    }

    public User name(String name) {
        this.name = name;
        return this;
    }

    public User surname(String surname) {
        this.surname = surname;
        return this;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User status(UserStatus status) {
        this.status = status;
        return this;
    }

    public User roles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public User creationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
        return this;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    
    public Timestamp getCreationTime() {
        return creationTime;
    }
    
    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
    
    @Override
    public String toString() {
        return "User [creationTime=" + creationTime + ", email=" + email + ", id=" + id + ", name=" + name
                + ", password=" + password + ", roles=" + roles + ", status=" + status + ", surname=" + surname + "]";
    }
    
}
