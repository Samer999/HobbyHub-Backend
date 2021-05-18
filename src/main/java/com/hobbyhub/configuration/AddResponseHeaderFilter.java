package com.hobbyhub.configuration;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class AddResponseHeaderFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
