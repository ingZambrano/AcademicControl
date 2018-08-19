package com.avior.academic.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private AuthenticationProvider authProvider;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/findMail", "/login", "/fcm/notify", "/loginToken", "/privacidad", "/tos", "/*", "/fcm/*")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				//.loginPage("/login")
				// .permitAll()
				.successHandler(successHandler())
				.failureHandler(failureHandler())
				.and().logout().permitAll()
				.and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler())
				.authenticationEntryPoint(authenticationEntryPoint())
				
				;

	}

    /**
     * Este método agrega las excepciones a Spring Security, para que puedan ser consultadas sin una sesión activa
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-**", "/webjars/**", "/v2/**", "/configuration/**", "/resource/**");
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authProvider);		
		
	}



	private AuthenticationSuccessHandler successHandler() {
		return new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(
					HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse,
					Authentication authentication) throws IOException,
					ServletException {
				httpServletResponse.getWriter().append("OK");
				httpServletResponse.setStatus(HttpServletResponse.SC_OK);
				

			}
		};
	}

	private AuthenticationFailureHandler failureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(
					HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse,
					AuthenticationException e) throws IOException,
					ServletException {			
				
				httpServletResponse.getWriter()
				.append("Authentication failure");
				if(e.getMessage().contains("returned null")){					
					httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
				}else{					
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		};
	}

	private AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {

			@Override
			public void handle(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse,
					AccessDeniedException e) throws IOException,
					ServletException {
				httpServletResponse.getWriter().append("Access denied");
				httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		};
	}
	
	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse,
					AuthenticationException e) throws IOException,
					ServletException {
				httpServletResponse.getWriter().append("Not authenticated");
				httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			}
		};
	}

}
