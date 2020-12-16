package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.MemberDto;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToMany(mappedBy = "member")
    private List<Borrowing> borrowings;

    @CreationTimestamp
    private Timestamp createdAt;

    public Member() {
        super();
    }

    public static Member of(MemberDto memberDto) {
		return new Member().name(memberDto.getName()).surname(memberDto.getSurname()).status(memberDto.getStatus());
	}

    public Member id(Long id) {
        this.id = id;
        return this;
    }

    public Member memberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public Member name(String name) {
        this.name = name;
        return this;
    }

    public Member surname(String surname) {
        this.surname = surname;
        return this;
    }

    public Member status(MemberStatus status) {
        this.status = status;
        return this;
    }

    public Member borrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
        return this;
    }

    public Member createdAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

    public MemberStatus getStatus() {
        return status;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public String toString() {
        return "Member [borrowings=" + borrowings + ", creationTime=" + createdAt + ", id=" + id + ", libraryId="
                + memberId + ", name=" + name + ", status=" + status + ", surname=" + surname + "]";
    }

}
