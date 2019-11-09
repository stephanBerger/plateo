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

import fr.platform.plateo.business.service.ClientService;
import fr.platform.plateo.business.service.ProService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] RESOURCES = new String[] { "/include/**",
			"/css/**", "/icons/**", "/img/**", "/js/**", "/layer/**",
			"/resources/**", "/static/**", "/webjars/**", "/photos/**" };

	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter
			extends WebSecurityConfigurerAdapter {

		@Autowired
		private ClientService clientAuthService;

		@Autowired
		private BCryptPasswordEncoder decrypt;

		@Override
		@Autowired
		public void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.userDetailsService(this.clientAuthService)
					.passwordEncoder(this.decrypt);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/clients/**").authorizeRequests()
					.antMatchers(SecurityConfig.RESOURCES).permitAll()
					.anyRequest().hasAuthority("CLIENT").and()
				
					.formLogin()
					.loginPage("/clients/clientLogin").permitAll()
					.failureUrl("/clients/clientLogin?error=loginError")
					.defaultSuccessUrl("/")
					.usernameParameter("email")
					.and()
					
					.logout().logoutUrl("/clients/logout")
					.logoutSuccessUrl("/").permitAll();
		}

	}

	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter
			extends WebSecurityConfigurerAdapter {
		public App2ConfigurationAdapter() {
			super();
		}

		@Autowired
		private ProService proAuthService;

		@Autowired
		private BCryptPasswordEncoder decrypt;

		@Override
		@Autowired
		public void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.userDetailsService(this.proAuthService)
					.passwordEncoder(this.decrypt);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/pro/**").authorizeRequests()
					.antMatchers(SecurityConfig.RESOURCES).permitAll()
					.anyRequest().hasAuthority("PRO").and()
					
					.formLogin()
					.loginPage("/pro/proLogin").permitAll()
					.failureUrl("/pro/proLogin?error=loginError")
					.defaultSuccessUrl("/").usernameParameter("email")
					.and()
					
					.logout().logoutUrl("/pro/logout")
					.logoutSuccessUrl("/").permitAll();
		}
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

}
