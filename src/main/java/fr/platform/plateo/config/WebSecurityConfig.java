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

    	http.authorizeRequests()
    	.antMatchers(resources).permitAll()
    	.antMatchers("/","/new/**").permitAll()
        .antMatchers("/particulier*").hasRole("CLIENT")
        .anyRequest()
        .authenticated()
        .and()
        .authorizeRequests()
        .antMatchers("/pro*").hasRole("PRO")
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        // login ok route vers index du site
        .defaultSuccessUrl("/")
        .failureUrl("/login?error=true")
        .usernameParameter("client_email_address")
        .passwordParameter("client_password")
        .and()
        .logout()
        .permitAll()
        .logoutSuccessUrl("/");
        
    }
	
}
