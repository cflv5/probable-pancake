package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Borrowing {
    @Id
    @GeneratedValue(generator = "borrowingIdGenerator")
    @GenericGenerator(name = "borrowingIdGenerator", strategy = "tr.edu.yildiz.yazilimkalite.librarymanagement.util.BorrowingRecordIdGenerator")
    private String id;

    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date deadline;

    private Date refundDate;

    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"borrowing_id", "books_id"}))
    private List<Book> books;

    @ManyToOne
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @Column(nullable = false)
    private Integer extension;

    public Borrowing() {
        super();
    }

    public Borrowing id(String id) {
        this.id = id;
        return this;
    }

    public Borrowing createdAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Borrowing startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Borrowing deadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    public Borrowing refundDate(Date refundDate) {
        this.refundDate = refundDate;
        return this;
    }

    public Borrowing books(List<Book> books) {
        this.books = books;
        return this;
    }

    public Borrowing member(Member member) {
        this.member = member;
        return this;
    }

    public Borrowing status(BorrowingStatus status) {
        this.status = status;
        return this;
    }

    public Borrowing creator(User creator) {
        this.creator = creator;
        return this;
    }

    public Borrowing extension(Integer extension) {
        this.extension = extension;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getRefundDate() {
        return this.refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BorrowingStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowingStatus status) {
        this.status = status;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Integer getExtension() {
        return this.extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Borrowing [books=" + books + ", createdAt=" + createdAt + ", creator=" + creator + ", deadline="
                + deadline + ", extension=" + extension + ", id=" + id + ", member=" + member + ", refundDate="
                + refundDate + ", startDate=" + startDate + ", status=" + status + "]";
    }

}
