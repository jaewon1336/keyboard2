package com.keyboard2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 실제로 인증을 진행하는 클래스
 *  - 인증은 두 단계로 나누어진다.
 *  1.
 *
 */
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService,
                                        PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 실제로 인증을 진행하는 메소드
     *  - AuthenticationManager 구현체가 파라미터로 Authentication 객체의 구현체인
     *    usernamePasswordAuthenticationToken을 전달해준다.
     *  - 전달받은 객체에서 username(id)을 UserDetailsService 구현체의
     *    loadUserByUsername()메소드에 전달해준다.
     *  - loadUserByUsername()메소드는 디비에 조회해서 해당 사용자를 조회하고
     *    그 사용자를 UserDetails 인터페이스 구현체 클래스에 담겨져서 전달받음.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
    	log.info("여기는 CustomAuthenticationProvider 클래스의 authenticate() 메소드");
    	
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
        	
            return new UsernamePasswordAuthenticationToken(userDetails, 
            												password, 
            												userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
