package com.portfolio.backend.configs;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * A filter that handles Cross-Origin Resource Sharing (CORS) by setting the appropriate headers
 * in HTTP responses to allow cross-origin requests. It is used to enable client-side web applications
 * from different origins to access the server resources.
 *
 * This filter allows the HTTP request methods (POST, GET, PUT, OPTIONS, DELETE) and sets the necessary
 * headers for a preflight request, including `Access-Control-Allow-Origin`, `Access-Control-Allow-Methods`,
 * and `Access-Control-Allow-Headers`. It also allows the OPTIONS HTTP method to pass without further processing
 * and responds with status OK for OPTIONS requests.
 *
 * The filter is configured with the highest precedence to ensure it runs before other filters in the filter chain.
 *
 * <p>
 * This filter is typically used in REST APIs to allow the frontend application, which might be hosted on a
 * different domain or port, to communicate with the backend server without running into cross-origin issues.
 * </p>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter{

    /**
     * Cleans up any resources when the filter is destroyed. In this case, it does nothing.
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    /**
     * This method processes the HTTP request and response for CORS support. It adds the necessary
     * headers to the response to enable cross-origin requests and handles preflight OPTIONS requests.
     *
     * <p>
     * For requests with the method "OPTIONS", it sends an HTTP 200 OK response and does not proceed with
     * further filtering. For other HTTP methods, it adds the CORS headers and forwards the request to the next
     * filter in the chain.
     * </p>
     *
     * @param servletRequest the incoming HTTP request
     * @param servletResponse the outgoing HTTP response
     * @param filterChain the chain of filters that the request and response will go through
     * @throws IOException if an I/O error occurs during filter processing
     * @throws ServletException if a servlet error occurs during filter processing
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
        HttpServletResponse responce = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String originHeader = request.getHeader("origin");
        responce.setHeader("Access-Control-Allow-Origin", originHeader);
        responce.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTION, DELETE");
        responce.setHeader("Access-Control-Max-Age", "3600");
        responce.setHeader("Access-Control-Allow-Headers", "*");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            responce.setStatus(HttpServletResponse.SC_OK);
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * Initializes the filter. This method can be used to set up any necessary resources or configuration.
     * In this case, it does nothing.
     *
     * @param filterConfig the configuration object for the filter
     * @throws ServletException if an error occurs during filter initialization
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }


}
