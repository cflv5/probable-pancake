package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Member;
import tr.edu.yildiz.yazilimkalite.librarymanagement.model.MemberStatus;

public class MemberDto {
    private String memberId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotNull
    private MemberStatus status;

    public MemberDto() {
        super();
    }

    public static MemberDto of(Member member) {
        return new MemberDto().libraryId(member.getMemberId()).name(member.getName()).surname(member.getSurname())
                .status(member.getStatus());
    }

    public MemberDto libraryId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public MemberDto name(String name) {
        this.name = name;
        return this;
    }

    public MemberDto surname(String surname) {
        this.surname = surname;
        return this;
    }

    public MemberDto status(MemberStatus status) {
        this.status = status;
        return this;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public MemberStatus getStatus() {
        return this.status;
    }

    public void setStatus(MemberStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MemberDto [libraryId=" + memberId + ", name=" + name + ", status=" + status + ", surname=" + surname
                + "]";
    }

}
