package api.v1;


import api.v1.dto.SingleResponseDto;
import api.v1.member.Dto.MemberDto;
import api.v1.member.controller.MemberController;
import api.v1.member.entity.Member;
import api.v1.member.mapper.MemberMapper;
import api.v1.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberRestApiDocTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper memberMapper;
    @Autowired
    private Gson gson;


    @Test
    public void getMemberTest() throws Exception{
        //given
        MemberDto.response response = new MemberDto.response(
                1L,
                "alrdlrsha1@naver.com",
                "?????????",
                "?????????????????????.",
                "MAN",
                "??????????????????",
                1L,
                1L
        );

        //when
        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(memberMapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(
                get("/v1/members/{member-id}", 1)
        );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberName").value(response.getMemberName()))
                .andDo(document(
                        "get-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("member-id").description("?????? ?????????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.memberPW").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data.memberSex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.memberCompanyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data.memberCompanyType").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data.memberCompanyLocation").type(JsonFieldType.NUMBER).description("?????? ????????????")
                                )
                        )
                ));



    }

    @Test
    public void getMembersTest() throws Exception{

        Member member1 = new Member();
        member1.setMemberId(1L);
        member1.setEmail("alrdlrsha1@naver.com");
        member1.setMemberName("?????????");
        member1.setMemberPW("?????????????????????.");
        member1.setMemberSex("MAN");
        member1.setMemberCompanyName("??????????????????");
        member1.setMemberCompanyType(1L);
        member1.setMemberCompanyLocation(1L);


        Member member2 = new Member();
        member2.setMemberId(2L);
        member2.setEmail("abcde@naver.com");
        member2.setMemberName("?????????");
        member2.setMemberPW("?????????????????????.");
        member2.setMemberSex("MAN");
        member2.setMemberCompanyName("???????????????");
        member2.setMemberCompanyType(2L);
        member2.setMemberCompanyLocation(2L);

//        Member member3 = new Member();
//        member3.setMemberId(3L);
//        member3.setEmail("qwer@naver.com");
//        member3.setMemberName("?????????");
//        member3.setMemberPW("?????????????????????.");
//        member3.setMemberSex("WOMAN");
//        member3.setMemberCompanyName("???????????????");
//        member3.setMemberCompanyType(2L);
//        member3.setMemberCompanyLocation(2L);


        //given
        MemberDto.response response1 = new MemberDto.response(
                1L,
                "alrdlrsha1@naver.com",
                "?????????",
                "?????????????????????.",
                "MAN",
                "??????????????????",
                1L,
                1L
        );
        MemberDto.response response2 = new MemberDto.response(
                2L,
                "abcde@naver.com",
                "?????????",
                "?????????????????????.",
                "MAN",
                "???????????????",
                2L,
                2L
        );
//        MemberDto.response response3 = new MemberDto.response(
//                3L,
//                "qwer@naver.com",
//                "?????????",
//                "?????????????????????.",
//                "WOMAN",
//                "???????????????",
//                2L,
//                2L
//        );

        PageImpl<Member> members = new PageImpl<>(List.of(member1, member2),
                PageRequest.of(0, 2, Sort.by("memberId").descending()), 2);

        List<MemberDto.response> responses = List.of(response1,response2);

        given(memberService.findMembers(Mockito.anyInt(),
                Mockito.anyInt())).willReturn(members);

        given(memberMapper.membersToMemberResponses(Mockito.anyList())).willReturn(responses);
        //when
        ResultActions actions = mockMvc.perform(get("/v1/members").param("page", "1").param("size",
                "1").accept(MediaType.APPLICATION_JSON));


        //then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "get-members",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                        fieldWithPath("data.[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.[].email").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data.[].memberName").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.[].memberPW").type(JsonFieldType.STRING).description("????????????"),
                                        fieldWithPath("data.[].memberSex").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.[].memberCompanyName").type(JsonFieldType.STRING).description("?????????"),
                                        fieldWithPath("data.[].memberCompanyType").type(JsonFieldType.NUMBER).description("?????? ??????"),
                                        fieldWithPath("data.[].memberCompanyLocation").type(JsonFieldType.NUMBER).description("?????? ????????????"),

                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("?????????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("??? ??????"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("????????? ??? ??????")

                        )
                ));


    }

}
