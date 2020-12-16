package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;

    @CreationTimestamp
    private Timestamp creationTime;

    public UserRole() {
        super();
    }

    public UserRole id(long id) {
        this.id = id;
        return this;
    }

    public UserRole role(String role) {
        this.role = role;
        return this;
    }

    public UserRole creationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "UserRole [creationTime=" + creationTime + ", id=" + id + ", role=" + role + "]";
    }

}
