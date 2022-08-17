package api.v1.member.service;

import api.v1.exception.BusinessLogicException;
import api.v1.exception.ExceptionCode;
import api.v1.member.MemberRepository.MemberRepository;
import api.v1.member.entity.Member;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;


    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

   public Member updateMember(Member member) {
       Member findMember = findVerifiedMember(member.getMemberId());

       Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findMember.setEmail(email));

       Optional.ofNullable(member.getMemberName())
               .ifPresent(name -> findMember.setMemberName(name));

       Optional.ofNullable(member.getMemberPW())
                .ifPresent(pw -> findMember.setMemberPW(pw));

       Optional.ofNullable(member.getMemberSex())
               .ifPresent(sex -> findMember.setMemberSex(sex));

       Optional.ofNullable(member.getMemberCompanyName())
               .ifPresent(comName -> findMember.setMemberCompanyName(comName));

       Optional.ofNullable(member.getMemberCompanyType())
               .ifPresent(comType -> findMember.setMemberCompanyType(comType));

       Optional.ofNullable(member.getMemberCompanyLocation())
               .ifPresent(comLocation -> findMember.setMemberCompanyLocation(comLocation));

        return memberRepository.save(findMember);
    }


    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}