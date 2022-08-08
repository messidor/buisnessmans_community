package com.example.springSecurity.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springSecurity.model.Member;
import com.example.springSecurity.oauth.PrincipalDetails;
import com.example.springSecurity.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private MemberRepository memberRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소 요청 됨.");

        String jwtHeader = request.getHeader("Authorization");  //키값이 Authorization

        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {   //의 벨류가 Bearer 인지 체크
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = jwtHeader.replace("Bearer ", "");// Bearer인지 체크후 ""으로 변경 토큰

        String username = JWT.require(Algorithm.HMAC512("cos_jwt_token")).build().verify(jwtToken).getClaim("username").asString();
        //유저네임이 정상적으로 들어오는지체크
        if (username != null) {
            Member memberEntity = memberRepository.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
        super.doFilterInternal(request, response, chain);
    }
}