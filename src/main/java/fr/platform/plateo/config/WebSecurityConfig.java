package fr.platform.plateo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	String[] resources = new String[] {
			"/include/**","/css/**","/icons/**","/IMG/**","/js/**",
			"/layer/**","/resources/","/static/**","/webjars/**"
	};
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	//Authorisations anonyme
    	http.authorizeRequests()
    	.antMatchers(resources).permitAll()
    	.antMatchers("/","/new/**").permitAll()
    	.and()
    	.formLogin()
	        .loginPage("/pro_login")
	        .permitAll()
	        .failureUrl("/login?error=true")
	        .usernameParameter("client_email_address")
	        .passwordParameter("client_password")
	        .and()
	        .logout()
	        .permitAll()
	        .logoutSuccessUrl("/")
	    .and()
	    .formLogin()
	    	.loginPage("client_login")
    		.permitAll()
    		.usernameParameter("pro_email_address")
            .passwordParameter("pro_password")
            .and()
	        .logout()
	        .permitAll()
	        .logoutSuccessUrl("/");
    		
	    	
    	//Auth client
    	http.authorizeRequests()
        .antMatchers("/client*").hasRole("CLIENT");
    	
        //Auth pro
    	http.authorizeRequests()
    	.antMatchers("/pro*").hasRole("PRO");
    	
    }
	
}
