package com.hnev.toy.config;

import com.hnev.toy.handler.AuthFailureHandler;
import com.hnev.toy.handler.AuthSucessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final AuthSucessHandler authSucessHandler;
    private final AuthFailureHandler authFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .mvcMatchers("/", "/member/**", "/error/**", "/js/**", "/css/**", "/image/**").permitAll() // 해당 경로들은 접근을 허용
                .anyRequest().authenticated(); // 그 외 요청은 인증요구
        http.formLogin()
                .loginPage("/member/login") // 로그인 페이지 URL 지정
                .loginProcessingUrl("/login/action") // 로그인 처리 URL 지정
                .successHandler(authSucessHandler) // 성공 handler
                .failureHandler(authFailureHandler); // 실패 handler
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 URL
                .logoutSuccessUrl("/member/login") // 성공 리턴 URL
                .invalidateHttpSession(true) // 인증정보를 지우하고 세션을 무효화
                .deleteCookies("JSESSIONID", "remember-me"); // JSESSIONID, remember-me 쿠키 삭제
        http.sessionManagement()
                .maximumSessions(1) // 세션 최대 허용 수 1, -1인 경우 무제한 세션 허용
                .maxSessionsPreventsLogin(false) // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
                .expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired"); // 세션이 만료된 경우 이동 할 페이지를 지정
        http.rememberMe() // 로그인 유지
                .key("0467EC591838570F48CC386CEE6ED9FBA53B4593A283BAFD5A94347AD3428408") // 토큰 생성시 키 값
                .alwaysRemember(false) // 항상 기억할 것인지 여부
                .tokenValiditySeconds(43200) // in seconds, 12시간 유지
                .rememberMeParameter("remember-me"); //rememberMe 파라미터 이름 지정

        return http.build();
    }
}
