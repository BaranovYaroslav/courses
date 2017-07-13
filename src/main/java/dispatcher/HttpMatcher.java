package dispatcher;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class that holds mapping between urls and controllers.
 *
 * @author Yaroslav Baranov
 */
public class HttpMatcher {

    private static HttpMatcher instance;

    private List<HttpMatcherEntry> entries;

    private HttpMatcher() {
        entries = new ArrayList<>();
    }

    /**
     * Method to retrieve holder of controller by giver url.
     *
     * @param url url to find controller.
     * @return Optional of holder.
     */
    public Optional<HttpMatcherEntry> getMatcherEntry(String url){
        for(HttpMatcherEntry entry: entries){
            if(entry.url.equals(url)){
                return Optional.of(entry);
            }
        }
        return Optional.empty();
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
            HttpMatcherEntry httpMatcherEntry = new HttpMatcherEntry(url, controller);
            HttpMatcher.this.entries.add(httpMatcherEntry);
            return this;
        }

        public HttpMatcher build() {
            return HttpMatcher.this;
        }
    }

    /**
     * Inner class that hold url and controller.
     *
     * @author Yaroslav Baranov
     */
    public static class HttpMatcherEntry {

        private String url;

        private final Controller controller;

        public HttpMatcherEntry (String url, Controller controller){
            this.url = url;
            this.controller = controller;
        }

        public void executeController(HttpWrapper httpWrapper){
            this.controller.execute(httpWrapper);
        }

        public String getUrl() {
            return url;
        }
    }
}
