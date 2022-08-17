package api.v1.member.mapper;

import api.v1.member.Dto.MemberDto;
import api.v1.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface  MemberMapper {
    Member memberPostToMember(MemberDto.Post requestBody);
    MemberDto.response memberToMemberResponse(Member member);
    List<MemberDto.response> membersToMemberResponses(List<Member> members);
}
