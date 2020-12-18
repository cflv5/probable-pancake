package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.BorrowingStatus;

public class BorrowingRecordingDto {
    private Long id;

    @NotNull
    private Date startDate;

    @NotNull
    private Date deadline;

    @NotEmpty
    private List<Long> books;

    @NotEmpty
    private String member;

    private BorrowingStatus status;

    public BorrowingRecordingDto() {
        super();
    }

    public BorrowingRecordingDto id(Long id) {
        this.id = id;
        return this;
    }

    public BorrowingRecordingDto startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public BorrowingRecordingDto deadline(Date deadline) {
        this.deadline = deadline;
        return this;
    }

    public BorrowingRecordingDto books(List<Long> books) {
        this.books = books;
        return this;
    }

    public BorrowingRecordingDto member(String member) {
        this.member = member;
        return this;
    }

    public BorrowingRecordingDto status(BorrowingStatus status) {
        this.status = status;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<Long> getBooks() {
        return this.books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }

    public String getMember() {
        return this.member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public BorrowingStatus getStatus() {
        return this.status;
    }

    public void setStatus(BorrowingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BorrowingRecordingDto [books=" + books + ", deadline=" + deadline + ", id=" + id + ", member=" + member
                + ", startDate=" + startDate + ", status=" + status + "]";
    }

}
