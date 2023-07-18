package com.keyboard2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및 인증을 미리 체크하겠다는 설정을 활성화한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	// BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체 
	// (BCrypt라는 해시 함수를 이용하여 패스워드를 암호화 한다.)
	// 회원 비밀번호 등록시 해당 메서드를 이용하여 암호화해야 로그인 처리시 동일한 해시로 비교한다.
	@Bean
	public BCryptPasswordEncoder encryptPassword() {
		return new BCryptPasswordEncoder();
	}

	
	/*
	 * 인가(Authorization) : Http의 url 요청에 대한 사용 권한 설정
	 *  - 이 url에 접근하기 위해서는 이런 권한이 필요하다는 형태로 설정
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
        // sample/all url을 요청하기 위해서는 모든 권한(누구나) 사용자가 가능
        .antMatchers("/").permitAll()
        
        // sample/member url을 요청하기 위해서는 USER권한이 있어야 함.
        .antMatchers("/cart").hasRole("USER")
        // sample/admin url을 요청하기 위해서는 ADMIN권한이 있어야 함.
        .antMatchers("/admin/**").hasRole("ADMIN");

        /*
         * formLogin() : 로그인 화면
         *  - 권한이 없는 메뉴에 접근하려고 했을 때 보여줄 로그인 화면
         *  - 스프링 시큐리티에서 이미 만들어놓은 화면으로 수정 불가.
         */

		http.formLogin()
//				.loginPage("/login") // 커스텀 로그인 페이지 경로 설정
				.permitAll();

		
		/*
		 * CSRF(Cross Site Request Forgery 공격을 방어하기 위해서
		 * C/U/D의 post 요청에 대해서 로그인시 발급받은 인증 토큰을
		 * 달고 서버로 요청을 하도록 한다. 지금은 미사용(disable()) 
		 */
		http.csrf().disable();
		

		http.logout()
				.logoutUrl("/logout") // 로그아웃 URL 설정
				.logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트할 URL 설정
				.invalidateHttpSession(true) // HTTP 세션 무효화 설정
				.deleteCookies("JSESSIONID") // 로그아웃 시 삭제할 쿠키 설정
				.permitAll(); // 모든 사용자에게 로그아웃 허용
		
    }
}
