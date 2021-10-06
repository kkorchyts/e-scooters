package com.kkorchyts.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    private String jwtAuthorizationHeader;

    private String jwtTokenStartwith;

    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(getJwtAuthorizationHeader());

        String userName = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(getJwtTokenStartwith())) {
            jwt = authorizationHeader.substring(getJwtTokenStartwith().length());
            userName = jwtUtil.extractUsername(jwt);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null && !jwtUtil.isExpired(jwt)) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            if (this.jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //https://virgo47.files.wordpress.com/2014/07/authenticated-access.png
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String getJwtAuthorizationHeader() {
        return jwtAuthorizationHeader;
    }

    @Value("${jwt.authorization.header}")
    public void setJwtAuthorizationHeader(String jwtAuthorizationHeader) {
        this.jwtAuthorizationHeader = jwtAuthorizationHeader;
    }

    public String getJwtTokenStartwith() {
        return jwtTokenStartwith;
    }

    @Value("${jwt.token.startwith}")
    public void setJwtTokenStartwith(String jwtTokenStartwith) {
        this.jwtTokenStartwith = jwtTokenStartwith;
    }
}