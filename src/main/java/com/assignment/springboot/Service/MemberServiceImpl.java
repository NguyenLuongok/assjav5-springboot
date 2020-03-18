package com.assignment.springboot.Service;

import com.assignment.springboot.Model.Member;
import com.assignment.springboot.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void remove(Long id) {
        memberRepository.remove(id);
    }

    @Override
    public Member loginMember(String userName) {
        return memberRepository.loginMember(userName);
    }

    @Override
    public void update(Member member) {
        memberRepository.update(member);
    }
}
