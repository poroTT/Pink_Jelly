package com.example.pink_jelly.security.handler;

import com.example.pink_jelly.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("-------------------------");
        log.info("CustomLoginSuccessHandler onAuthenticationSuccess......");
        log.info(authentication.getPrincipal());

        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

        String encodedPw = memberDTO.getPasswd();

        // 소셜 로그인이고 회원의 패스워드가 1111이라면
        if (memberDTO.isSocial()
                && (memberDTO.getPasswd().equals("1111")
                || passwordEncoder.matches("1111", memberDTO.getPasswd())
        )) {
            log.info("Should Change Password");

            log.info("Redirect to MemberModify");
            response.sendRedirect("/member/kakaoSignup");
        } else {
            response.sendRedirect("/");
        }
    }
}
