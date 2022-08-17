package api.v1.member.entity;


import lombok.*;

import javax.persistence.Id;
import org.springframework.data.domain.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String memberName;

    @Column(length = 100, nullable = false)
    private String memberPW;

    @Column(length = 10, nullable = false)
    private String memberSex;

    @Column(length = 100, nullable = false)
    private String memberCompanyName;

    @Column(length = 10, nullable = false)
    private Long memberCompanyType;

    @Column(length = 10, nullable = false)
    private Long memberCompanyLocation;


    public Member( String email, String memberName, String memberPW, String memberSex, String memberCompanyName, Long memberCompanyType, Long memberCompanyLocation) {
        this.email = email;
        this.memberName = memberName;
        this.memberPW = memberPW;
        this.memberSex = memberSex;
        this.memberCompanyName = memberCompanyName;
        this.memberCompanyType = memberCompanyType;
        this.memberCompanyLocation = memberCompanyLocation;
    }
//    public Member(String email){
//        this.email = email;
//    }

}
