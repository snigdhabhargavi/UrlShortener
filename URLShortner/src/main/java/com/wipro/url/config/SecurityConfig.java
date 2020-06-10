package com.wipro.url.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf() 
			.disable()
			.authorizeRequests()
		    .antMatchers("/login","/")
		    .permitAll()
		    .anyRequest()
		    .authenticated()
		    .and()
		    .oauth2Login()
		    .loginPage("/")
		    .authorizationEndpoint()
		    .baseUri("/oauth2/authorize-client")
		    .authorizationRequestRepository(authorizationRequestRepository())
		    .and()
		    .tokenEndpoint()
		    .accessTokenResponseClient(accessTokenResponseClient())
		    .and()
		    .defaultSuccessUrl("/success")
		    .failureUrl("/failure")
		    .and()
		    .logout()
		    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		    .invalidateHttpSession(true)
		    .clearAuthentication(true)
		    .logoutSuccessUrl("/")
		    .permitAll(); 	    
	}
	
	 @Bean
	    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
	        return new HttpSessionOAuth2AuthorizationRequestRepository();
	    }
	 
	 @Bean
	    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
	        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
	        return accessTokenResponseClient;
	    }
}
