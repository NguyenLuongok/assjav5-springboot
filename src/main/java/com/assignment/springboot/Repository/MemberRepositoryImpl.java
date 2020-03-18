package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.Member;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class MemberRepositoryImpl implements MemberRepository {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Member> findAll() {
        TypedQuery<Member> typedQuery = entityManager.createQuery("select me from Member me", Member.class);
        return typedQuery.getResultList();
    }

    @Override
    public Member findById(Long id) {
        return entityManager.find(Member.class,id);
    }

    @Override
    public void save(Member model) {
        Session session = entityManager.unwrap(Session.class);
       if (model != null){
           session.save(model);
       }
       else {
           entityManager.persist(model);
       }
    }

    @Override
    public void remove(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public Member loginMember(String userName) {
        try{
            String hql = "select me from Member me where  me.memberEmail=:email";
            TypedQuery<Member> typedQuery = entityManager.createQuery(hql,Member.class);
            typedQuery.setParameter("email", userName);
            return typedQuery.getSingleResult();
        }
        catch (Exception e){

        }
        return null;
    }

    @Override
    public void update(Member member) {
        entityManager.merge(member);
    }
}
