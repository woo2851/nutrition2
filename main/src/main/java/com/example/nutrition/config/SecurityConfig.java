//package com.example.nutrition.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    // SecurityFilterChain을 사용한 보안 설정
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user/**").authenticated() // 인증된 사용자만 접근 가능
//                        .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN 권한 필요
//                        .requestMatchers("/login","/loginForm","/joinForm","/basic/idCheck","/join").permitAll()
//                        .requestMatchers("/css/**", "/js/**", "/img/**","/trakker_video/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/loginForm")
//                        .loginProcessingUrl("/auth/login") // 로그인 처리 URL
//                        .failureHandler(customFailureHandler)
//                        .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 URL
//                )
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .loginPage("/loginForm")
//                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
//                                .userService(principalOauth2UserService) // OAuth2 로그인 사용자 서비스
//                        )
//                        .failureUrl("/loginForm?error=true")
//                        .defaultSuccessUrl("/")
//
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // 로그아웃 요청을 처리할 URL 설정
//                        .logoutSuccessUrl("/loginForm") // 로그아웃 성공 시 리다이렉트할 URL 설정
//                        .addLogoutHandler((request, response, authentication) -> { // 로그아웃 핸들러 추가 (세션 무효화 처리)
//                            HttpSession session = request.getSession();
//                            session.invalidate();
//                        })
//                        .logoutSuccessHandler((request, response, authentication) -> // 로그아웃 성공 핸들러 추가 (리다이렉션 처리)
//                                response.sendRedirect("/loginForm"))
//                        .deleteCookies("remember-me") // 로그아웃 시 쿠키 삭제 설정
//                        .invalidateHttpSession(true) // HTTP session reset
//                );
//
//
//        return http.build();
//    }
//
//    // WebSecurityCustomizer를 사용하여 Spring Security 기본 설정 제외
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/api/auth/**", "/static/**");  // 특정 경로는 Spring Security에서 제외
//    }
//
//    // 사용자 세부 정보 서비스 예시
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("{noop}password").roles("USER").build());
//        manager.createUser(User.withUsername("admin").password("{noop}admin").roles("ADMIN").build());
//        return manager;
//    }
//}
