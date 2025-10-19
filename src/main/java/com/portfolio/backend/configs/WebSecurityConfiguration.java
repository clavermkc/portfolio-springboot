package com.portfolio.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import com.portfolio.backend.services.userService.UserService;


/**
 * This class provides the security configuration for the web application.
 * It integrates Spring Security to handle authentication and authorization of requests.
 * The configuration defines HTTP security settings, including:
 * <ul>
 *   <li>Disabling CSRF protection (since the application uses stateless authentication)</li>
 *   <li>Specifying which API endpoints are publicly accessible and which require authentication</li>
 *   <li>Configuring session management to be stateless (no session is created or maintained)</li>
 *   <li>Configuring a custom authentication provider with BCrypt password encoding</li>
 *   <li>Adding a JWT-based authentication filter to verify tokens for authenticated requests</li>
 * </ul>
 *
 * This configuration ensures that:
 * <ul>
 *   <li>Only `/api/auth/**` endpoints are publicly accessible.</li>
 *   <li>Any other endpoint requires authentication.</li>
 *   <li>The application is stateless, relying on JWT tokens for authentication instead of sessions.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures HTTP security for the application, setting up authorization rules and session management.
     * <p>
     * This method:
     * <ul>
     *   <li>Disables CSRF protection as it's unnecessary in stateless applications.</li>
     *   <li>Permits access to `/api/auth/**` endpoints (public access).</li>
     *   <li>Requires authentication for any other request.</li>
     *   <li>Configures session management to be stateless, meaning no HTTP sessions are used.</li>
     *   <li>Registers a JWT authentication filter to validate incoming JWT tokens before the request reaches other filters.</li>
     * </ul>
     *  @param http the HTTP security configuration object
     * @return the configured {@link SecurityFilterChain} for the application
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/v0/**").permitAll()
                                .requestMatchers(
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/swagger-ui/index.html",
                                        "/v3/api-docs/**",
                                        "/api-docs/**",
                                        "/webjars/**",
                                        "/swagger-resources/**",
                                        "/favicon.ico").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Configures and returns an {@link AuthenticationManager} for handling authentication requests.
     *
     * @param config the configuration for authentication
     * @return the {@link AuthenticationManager} bean
     * @throws Exception if an error occurs while creating the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


    /**
     * Configures and returns a custom {@link AuthenticationProvider} that uses the {@link UserService}
     * for loading user details and BCryptPasswordEncoder for password encoding.
     *
     * @return the configured {@link AuthenticationProvider} for the application
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService.userDetailsService());
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return authProvider;
    }
}