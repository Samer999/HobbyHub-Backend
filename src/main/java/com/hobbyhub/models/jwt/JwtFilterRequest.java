package com.hobbyhub.models.jwt;

import com.hobbyhub.models.users.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class JwtFilterRequest extends OncePerRequestFilter {

  @Autowired private JwtUtils jwtUtils;

  @Autowired private UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = httpServletRequest.getHeader("Authorization");
    String username = null, password = null;
    String jwtToken = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwtToken = authorizationHeader.substring(7);
      username = jwtUtils.extractUsername(jwtToken);
    }
    // && SecurityContextHolder.getContext().getAuthentication() == null
    if (username != null) {
      UserDetails currentUserDetails = userService.loadUserByUsername(username);
      Boolean tokenValidated = jwtUtils.validateToken(jwtToken, currentUserDetails);
      if (tokenValidated) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                currentUserDetails, null, currentUserDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
