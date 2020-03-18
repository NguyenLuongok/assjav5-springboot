package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.Member;

public interface MemberRepository extends Repository<Member>{
    Member loginMember(String userName);
    void update (Member member);
}
