package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
//    @Autowired
//    MemberRepository memberRepository;

//    @AfterEach
//    public void afterEach() {
//        memberRepository.clearStore();
//    }

    @Test
    public void 회원가입() throws Exception {
        System.out.println("--------------------------->>start");

        //given : 이러한상황이 주어져서
        Member member = new Member();
        member.setName("홍길동");

        //when : 이렇게 실행되면
        Long saveId = memberService.join(member);

        //then : 이러한 결과가 나온다.
        Member findMember = memberService.findOne(member.getId()).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

        System.out.println("--------------------------->>end");
    }

    @Test
    public void 중복회원예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("홍길동");
        Member member2 = new Member();
        member2.setName("홍길동");

        //when
        memberService.join(member1);

        //then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}