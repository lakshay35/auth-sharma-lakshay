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

/**
 * Cors filter
 */
@WebFilter("/*")
public class Cors implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse res = (HttpServletResponse) response;
    HttpServletRequest req = (HttpServletRequest) request;

    if (req.getHeader("Origin").endsWith("sharmalakshay.com")) {
      res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
      res.setHeader("Access-Control-Allow-Methods", "*");
      res.setHeader("Access-Control-Allow-Headers", "*");
    } else {
      res.setHeader("Access-Control-Allow-Origin", "none");
      res.setHeader("Access-Control-Allow-Methods", "none");
      res.setHeader("Access-Control-Allow-Headers", "none");
    }

    chain.doFilter(req, res);
  }

}