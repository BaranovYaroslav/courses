package dispatcher;

/**
 * Created by Ярослав on 04.07.2017.
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
