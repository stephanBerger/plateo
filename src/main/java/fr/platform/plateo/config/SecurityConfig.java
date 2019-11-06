package fr.platform.plateo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.ProService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private ClientService clientAuthService;

		@Autowired
		private BCryptPasswordEncoder decrypt;

		@Autowired
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(clientAuthService).passwordEncoder(decrypt);
		}

		String[] resources = new String[] { "/include/**", "/css/**", "/icons/**", "/IMG/**", "/js/**", "/layer/**",
				"/resources/**", "/static/**", "/webjars/**", "/photos/**" };

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/clients/**").authorizeRequests()
					
				.antMatchers("/clients/*").hasRole("CLIENT")
				.antMatchers("/pro/**").hasRole("PRO")
				
				.anyRequest().authenticated()
				.and()
				
	            
				.formLogin().loginPage("/clients/clientLogin").permitAll()
				.failureUrl("/clients/clientLogin?error=loginError").defaultSuccessUrl("/")
				.usernameParameter("email")
				.passwordParameter("password")
				.and()

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/clients/logout"))
				.logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedPage("/403");

		}

	}

	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {
		public App2ConfigurationAdapter() {
			super();
		}

		@Autowired
		private ProService proAuthService;

		@Autowired
		private BCryptPasswordEncoder decrypt;

		@Autowired
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(proAuthService).passwordEncoder(decrypt);
		}

		String[] resources = new String[] { "/include/**", "/css/**", "/icons/**", "/IMG/**", "/js/**", "/layer/**",
				"/resources/**", "/static/**", "/webjars/**", "/photos/**" };

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/pro/**").authorizeRequests()
			
				.antMatchers("/pro/**").hasRole("PRO")		
				.antMatchers("/clients/**").hasRole("CLIENT")
				
				.anyRequest()
				.authenticated()
				.and()

	            
				.formLogin()
				.loginPage("/pro/proLogin")
				.permitAll()
				.failureUrl("/pro/proLogin?error=loginError")
				.defaultSuccessUrl("/").usernameParameter("email")
				.passwordParameter("password").and()

				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/pro/logout")).logoutSuccessUrl("/")
				.permitAll().and().exceptionHandling().accessDeniedPage("/403");

		}
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

}
