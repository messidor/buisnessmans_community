package api.v1.member.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class MemberDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private String email;
        private String memberName;
        private String memberPW;
        private String memberSex;
        private String memberCompanyName;
        private Long memberCompanyType;
        private Long memberCompanyLocation;
    }


    @AllArgsConstructor
    @Getter
    public static class response {
        private Long memberId;
        private String email;
        private String memberName;
        private String memberPW;
        private String memberSex;
        private String memberCompanyName;
        private Long memberCompanyType;
        private Long memberCompanyLocation;

    }
}
