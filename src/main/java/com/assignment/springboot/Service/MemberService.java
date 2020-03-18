package com.assignment.springboot.Service;

import com.assignment.springboot.Model.Member;

import java.util.List;

public interface MemberService {
    List<Member> findAll();
    Member findById(Long id);
    void save(Member member);
    void remove(Long id);
    Member loginMember(String userName);
    void update(Member member);
}
