package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String role;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp creationTime;

    /*
     * User who granted the role to the grantee
     */
    @OneToOne
    @JoinColumn
    private User grantor;

    @OneToMany
    @JoinColumn(name = "role_id")
    private List<UserPermission> permissions;

    public UserRole() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public User getGrantor() {
        return grantor;
    }

    public void setGrantor(User grantor) {
        this.grantor = grantor;
    }

    public List<UserPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UserPermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserRole [creationTime=" + creationTime + ", grantor=" + grantor + ", id=" + id + ", permissions="
                + permissions + ", role=" + role + "]";
    }

}
