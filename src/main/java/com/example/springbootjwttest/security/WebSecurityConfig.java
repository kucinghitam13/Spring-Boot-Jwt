package com.example.springbootjwttest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		http.csrf()
				.disable()
			.headers()
				.frameOptions().disable();
		
		http.authorizeRequests()
//				.antMatchers("/**").permitAll()
				.antMatchers("/users/signin").permitAll()
				.antMatchers("/users/signup").permitAll()
				.anyRequest().authenticated();
		
		http.exceptionHandling()
			.accessDeniedPage("/login");
		
//		no session
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//		apply jwt
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
		
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
}
