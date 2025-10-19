package com.portfolio.backend.configs;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.portfolio.backend.services.userService.UserService;
import com.portfolio.backend.utils.JwtUtil;

/**
 * This class is a Spring filter used to authenticate users based on a JWT (JSON Web Token) in the request.
 * It extends {@link OncePerRequestFilter} to ensure that the filter is executed only once per request.
 * The filter extracts the JWT token from the "Authorization" header, validates the token, and sets the
 * authentication in the Spring Security context if the token is valid.
 *
 * The filter works in conjunction with the {@link JwtUtil} utility for handling JWT operations
 * and the {@link UserService} for retrieving user details from the database.
 *
 * <p>
 * This filter checks if the "Authorization" header contains a valid JWT token. If the token is valid,
 * it creates a new authentication token and sets it into the {@link SecurityContextHolder} for the
 * current request thread, enabling subsequent access to the authenticated user in the security context.
 * </p>
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;
    private final UserService userService;

    /**
     * This method is called once per request to filter incoming requests for authentication based on JWT token.
     * It checks the "Authorization" header, extracts the JWT token, and verifies it. If the token is valid,
     * the user is authenticated and the security context is updated accordingly.
     *
     * @param request the HttpServletRequest that contains the incoming HTTP request
     * @param response the HttpServletResponse that will be used to send a response
     * @param filterChain the chain of filters that will be invoked after this filter is executed
     * @throws ServletException if there is a servlet error during filter processing
     * @throws IOException if an I/O error occurs during filter processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        // Extract the "Authorization" header from the request
        final String authHeader = request.getHeader("Authorization");

        // If the header is empty or does not start with "Bearer", proceed with the filter chain
        if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt;
        jwt = authHeader.substring(7);
        final String userEmail;
        userEmail = jwtUtil.extractUserName(jwt);
        if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
            if(jwtUtil.isTokenValid(jwt, userDetails)){
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }

}