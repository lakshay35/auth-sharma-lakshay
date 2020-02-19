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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
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

    try {
      String origin = req.getHeader(HttpHeaders.ORIGIN);
      boolean validOrigin = origin.endsWith("sharmalakshay.com");

      if (validOrigin) {
        res.setHeader("Access-Control-Allow-Origin", origin);
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Allow-Headers", "*");
      } else {
        res.reset();
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return;
      }
    } catch (NullPointerException exception) {
      res.reset();
      res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    chain.doFilter(req, res);
  }

}