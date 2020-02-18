package sharmalakshay.auth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Cors filter
 */
@Component
@Order(1)
public class Cors implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse res = (HttpServletResponse) response;
    HttpServletRequest req = (HttpServletRequest) request;

    String origin = req.getHeader("Origin");
    boolean validOrigin = origin.endsWith("sharmalakshay.com");

    if (validOrigin) {
      res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
      res.setHeader("Access-Control-Allow-Methods", "*");
      res.setHeader("Access-Control-Allow-Headers", "*");
    } else {
      res.reset();
      res.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return;
    }

    chain.doFilter(req, res);
  }

}