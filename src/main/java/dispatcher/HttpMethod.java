package dispatcher;

/**
 * Enumeration of http methods.
 *
 * @author Yaroslav Baranov
 */
public enum HttpMethod {
    GET("GET"), POST("POST"), UPDATE("UPDATE"), DELETE("DELETE");

    private String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
