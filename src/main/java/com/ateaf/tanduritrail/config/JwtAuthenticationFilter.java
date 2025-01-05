package com.ateaf.tanduritrail.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private  final JwtUtils jwtUtils;
   private final UserDetailsService userDetailsService;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

      log.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());

     try{
        String token = jwtUtils.getJwtFromHeaders(request);

        if( token != null && jwtUtils.validateJwtToken(token)){
           String username = jwtUtils.getUsernameFromToken(token);
           UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("user details : {}",userDetails);
           UsernamePasswordAuthenticationToken authentication =
                   new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

           SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
           log.warn("JWT token is missing or invalid");
        }
     }catch (Exception e){
        log.error("error while setting user authentication: {}", e.getMessage());
     }

      chain.doFilter(request, response);
   }
}
