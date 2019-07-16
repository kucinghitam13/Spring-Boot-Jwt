package com.example.springbootjwttest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springbootjwttest.entity.Role;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/**").permitAll();
//		
//		http.csrf().disable();
//        http.headers().frameOptions().disable();
//		//		super.configure(http);
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf()
				.disable()
			.headers()
				.frameOptions().disable()
			.and()
			.authorizeRequests()
//				.antMatchers("/**").permitAll()
				.antMatchers(HttpMethod.POST, "/users/signin").permitAll()
				.antMatchers(HttpMethod.POST, "/users/signup").permitAll()
				.antMatchers("/users/me").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.exceptionHandling()
				.accessDeniedPage("/login")
			.and()
//			no session
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
//			apply jwt
			.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
		
		//		super.configure(http);
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
    	//allowing swagger access without authentication
    	web.ignoring()
    			.antMatchers("/v2/api-docs")
    			.antMatchers("/configuration/ui")
    			.antMatchers("/swagger-resources")
    			.antMatchers("/configuration/security")
    			.antMatchers("/swagger-ui.html")
    			.antMatchers("/webjars/**")
    		.and()
    		.ignoring()
    			.antMatchers("/h2-console/**")
    	;
//    	super.configure(web);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(12);
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
    	return super.authenticationManagerBean();
    }
}
