package com.assignment.springboot.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "member_acount")
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberId;
    private String  memberUser;
    private String  memberPassword;
    private String  memberEmail;
    private String  memberAddress;
    private String  membersPhone;
    private boolean memberStatus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private List<Receipt> receipts;

    public Member() {
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", memberUser='" + memberUser + '\'' +
                ", memberPassword='" + memberPassword + '\'' +
                ", memberEmail='" + memberEmail + '\'' +
                ", memberAddress='" + memberAddress + '\'' +
                ", membersPhone='" + membersPhone + '\'' +
                ", memberStatus=" + memberStatus +
                ", receipts=" + receipts +
                '}';
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberUser() {
        return memberUser;
    }

    public void setMemberUser(String memberUser) {
        this.memberUser = memberUser;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public boolean isMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(boolean memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMembersPhone() {
        return membersPhone;
    }

    public void setMembersPhone(String membersPhone) {
        this.membersPhone = membersPhone;
    }
}
