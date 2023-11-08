package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 해당 메서드의 리턴되는 오브젝트를 Ioc로 등록해줌
    @Bean
    public BCryptPasswordEncoder endcodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // /user/** 로 들어오면 인증이 필요
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                // 로그인을 해도 role admin이나 role manager가 있어야 들어갈 수 있음
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // role admin이 있어야 들어갈 수 있음
                .anyRequest().permitAll() // 위 3개 주소가 아니면 모두 권한 허용
                .and()
                .formLogin() // 권한이 없는 경우 로그인 페이지로 이동됨.
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌 => controller에 /login을 안만들어도 됨.
                .defaultSuccessUrl("/");  // 매인페이지로 가게 함.(특정 페이지에서 로그인으로 이동이 되었을 때 로그인시 특정 페이지로 가짐)
    }
}
