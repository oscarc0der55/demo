package com.example.demo.security;


import com.example.demo.service.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.repository.UserRepository;

//Öppnar hemsidan localhost:8080 och ser om du har rättigheterna

@Configuration
public class SecurityConfig {

    private final MyUserDetailService myUserDetailService;

    // Constructor injection for MyUserDetailService
    public SecurityConfig(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                        authorize -> {
                            // Permit access to static resources and login, home, and error pages
                            authorize.requestMatchers("/css/**", "/js/**", "/images/**").permitAll();
                            authorize.requestMatchers("/login", "/error/**", "/logout", "/", "/home").permitAll();
                            // Restrict access to admin and user pages based on roles
                            authorize.requestMatchers("/admin/**").hasRole("ADMIN");
                            authorize.requestMatchers("/user/**").hasRole("USER");
                            // All other requests require authentication
                            authorize.anyRequest().authenticated();
                        }
                ).formLogin(formLogin -> formLogin
                        .loginPage("/login")  // Custom login page
                        .defaultSuccessUrl("/", true)  // Redirect to home after successful login
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity (not recommended for production)
                .build();
    }


    /**
     * Configures the UserDetailsService.
     *
     * @return the configured UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailService() {
        return myUserDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

