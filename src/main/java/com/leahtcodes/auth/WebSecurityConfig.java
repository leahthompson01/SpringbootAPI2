package com.leahtcodes.auth;

import com.leahtcodes.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig  {
    @Value("${spring.websecurity.debug:false}")
    boolean webSecurityDebug;



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(webSecurityDebug);
    }
    @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/", "/login.html", "/error", "/webjars/**", "/api/**").permitAll()
                .anyRequest().authenticated()
        ).oauth2Login()
                .loginPage("/login").permitAll()
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        UserService userService = new UserService();

                        userService.processOAuthPostLogin(oauthUser.getEmail());

                        response.sendRedirect("/api/user");
                    }
                });


        return http.build();



        }


    @Autowired
    private CustomerOAuth2UserService oauthUserService;
}
