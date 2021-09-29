package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;


    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> resultList = em.createQuery("select m from Member m where m.name= :name ", Member.class)
                .setParameter("name", name)
                .getResultList();
        return resultList.stream().findAny();

    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Override
    public void clearStore() {

    }
}
