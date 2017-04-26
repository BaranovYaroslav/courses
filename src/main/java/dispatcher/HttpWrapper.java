package dispatcher;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class HttpWrapper {

    private static Logger logger = Logger.getLogger(HttpWrapper.class);

    private HttpServletRequest request;

    private HttpServletResponse response;

    public HttpWrapper() {}

    public HttpWrapper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void redirect(String path){
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
