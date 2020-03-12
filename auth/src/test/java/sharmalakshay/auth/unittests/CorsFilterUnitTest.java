package sharmalakshay.auth.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sharmalakshay.auth.filter.Cors;

/**
 * CorsUnitTest
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Cors.class)
public class CorsFilterUnitTest {

  @InjectMocks
  private Cors corsFilter = new Cors();

  @Test
  public void testDoFilter_shouldReturnOkResponse() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRemoteHost("https://microservice.sharmalakshay.com");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain filterChain = new MockFilterChain();

    try {
      corsFilter.doFilter(request, response, filterChain);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testDoFilter_shouldReturnBadResponse() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRemoteHost("https://abc.com");
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockFilterChain filterChain = new MockFilterChain();

    try {
      corsFilter.doFilter(request, response, filterChain);
    } catch (Exception e) {
      assertEquals(response.getStatus(), HttpStatus.FORBIDDEN);
    }
  }
}