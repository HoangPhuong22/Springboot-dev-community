package com.zerocoder.devsearch.securiry;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/","/reset-password/**", "/media/**", "/users/**", "/profiles/**", "/projects/**", "/register", "/change-password/**").permitAll()
                        .requestMatchers("/inbox/**","/myaccount/**", "/projects/add/**", "/projects/edit/**", "/projects/delete/**", "profiles/edit").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                        .formLogin(form ->
                                form
                                        .loginPage("/login")
                                        .loginProcessingUrl("/authenticate")
                                        .permitAll()

                        )
                        .logout(logout ->
                                logout
                                        .logoutUrl("/logout")
                                        .permitAll()
                                        .logoutSuccessUrl("/profiles")
                        );
        return http.build();
    }
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager
                .setUsersByUsernameQuery("select username, password, enable from user where username = ?");
        userDetailsManager
                .setAuthoritiesByUsernameQuery("select username, role_name from user s join user_role sr on s.user_id = sr.user_id join role r on sr.role_id = r.role_id where s.username = ?");
        return userDetailsManager;
    }
}
