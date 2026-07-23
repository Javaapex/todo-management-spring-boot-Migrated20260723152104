package net.guides.springboot.todomanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration{
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
            .passwordEncoder(NoOpPasswordEncoder.getInstance())
        		.withUser("admin").password("admin")
                .roles("USER", "ADMIN");
    }

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(requests -> requests.requestMatchers("/login", "/h2-console/**").permitAll()
				.requestMatchers("/", "/*todo*/**").access("hasRole('USER')"))
				.formLogin(withDefaults());

		http.csrf(csrf -> csrf.disable());
		http.headers(headers -> headers.frameOptions(options -> options.disable()));
		return http.build();
    }
}
