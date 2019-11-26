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
			.antMatchers("/clients/**").hasRole("CLIENT")
			.antMatchers("/pro/**").hasRole("PRO").anyRequest().authenticated().and()

					.formLogin().loginPage("/clients/login").permitAll()
					.failureUrl("/clients/login?error=loginError")
					.defaultSuccessUrl("/clients/clientDashboard")
					.usernameParameter("email").and()
					.exceptionHandling().accessDeniedPage("/403")
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
			http.authorizeRequests().antMatchers("/pro/**").hasRole("PRO").and()

					.formLogin().loginPage("/pro/login").permitAll()
					.failureUrl("/pro/login?error=loginError")
					.defaultSuccessUrl("/pro/proDashboard")
					.usernameParameter("email").and()
					.exceptionHandling().accessDeniedPage("/403")
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
