package dispatcher;

import controller.Controller;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class that holds mapping between urls and controllers.
 *
 * @author Yaroslav Baranov
 */
public class HttpMatcher {

    private static HttpMatcher instance;

    private Map<String, Controller> entries;

    private HttpMatcher() {
        entries = new HashMap<>();
    }

    /**
     * Method to retrieve controller by giver url.
     *
     * @param url url to find controller.
     * @return Optional of controller.
     */
    public Optional<Controller> getMatcherEntry(String url){
        Controller controller = entries.get(url);
        if(controller == null) {
            return Optional.empty();
        }
        return Optional.of(controller);
    }

    public static HttpMatcher getInstance() {
        if(instance == null) {
            instance = new HttpMatcher();
        }
        return instance;
    }

    public static Builder newBuilder(){
        if(instance == null) {
            instance = new HttpMatcher();
            return instance.new Builder();
        }
        return null;
    }

    public class Builder {

        public Builder addEntry(String url, Controller controller) {
            HttpMatcher.this.entries.put(url, controller);
            return this;
        }

        public HttpMatcher build() {
            return HttpMatcher.this;
        }
    }
}
