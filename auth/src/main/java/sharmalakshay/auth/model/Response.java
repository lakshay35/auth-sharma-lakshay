package sharmalakshay.auth.model;

/**
 * Response
 */
public class Response<T> {

  private T response;
  private String errorMessage;
  private boolean error;

  public Response(T response, String errorMessage, boolean error) {
    this.response = response;
    this.errorMessage = errorMessage;
    this.error = error;
  }

  public T getResponse() {
    return response;
  }

  public void setResponse(T response) {
    this.response = response;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }
}