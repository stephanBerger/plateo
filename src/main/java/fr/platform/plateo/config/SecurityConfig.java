package fr.platform.plateo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	static String[] resources = new String[] {
			"/include/**","/css/**","/icons/**","/IMG/**","/js/**",
			"/layer/**","/resources/","/static/**","/webjars/**"
	};
	
	@Configuration
	@Order(1)
	public static class ProConfigurationAdapter extends WebSecurityConfigurerAdapter {
	
	public ProConfigurationAdapter() {
		super();
	}
		
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.authorizeRequests()
    	.antMatchers(resources).permitAll()
    	.antMatchers("/","/index","/public/**").permitAll()
    	.antMatchers("/pro/**").hasRole("PRO")
    	.anyRequest().authenticated()
    	.and()
    	.formLogin()
	    	.loginPage("/pro_login")
    		.permitAll()
	        .failureUrl("/pro_login?error=true")
    		.usernameParameter("pro_email_address")
            .passwordParameter("pro_password")
            .and()
	        .logout()
	        .permitAll()
	        .logoutSuccessUrl("/");
    	
    	}
	}
	
	@Configuration
	@Order(2)
	public static class ClientConfigurationAdapter extends WebSecurityConfigurerAdapter {
	
	public ClientConfigurationAdapter() {
		super();
	}
		
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.authorizeRequests()
    	.antMatchers(resources).permitAll()
    	.antMatchers("/","/public/**").permitAll()
    	.antMatchers("/client/**").hasRole("CLIENT")
    	.and()
    	.formLogin()
	        .loginPage("/client_login")
	        .permitAll()
	        .failureUrl("/client_login?error=true")
	        .usernameParameter("client_email_address")
	        .passwordParameter("client_password")
	        .and()
	        .logout()
	        .permitAll()
	        .logoutSuccessUrl("/");
    	
    	}
	}
	
}
