package dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ярослав on 12.07.2017.
 */
public class HttpMatcher {

    private static HttpMatcher instance;

    private List<HttpMatcherEntry> entries;

    private HttpMatcher() {
        entries = new ArrayList<>();
    }

    public Optional<HttpMatcherEntry> getMatcherEntry(String url, String method){
        for(HttpMatcherEntry entry: entries){
            if(entry.url.equals(url) && entry.httpMethod.getMethod().equals(method)){
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

        public Builder addEntry(String url, HttpMethod httpMethod, Controller controller) {
            HttpMatcherEntry httpMatcherEntry = new HttpMatcherEntry(url, httpMethod, controller);
            HttpMatcher.this.entries.add(httpMatcherEntry);
            return this;
        }

        public HttpMatcher build() {
            return HttpMatcher.this;
        }
    }

    static class HttpMatcherEntry {

        private String url;

        private HttpMethod httpMethod;

        private final Controller controller;

        public HttpMatcherEntry (String url, HttpMethod httpMethod, Controller controller){
            this.url = url;
            this.httpMethod = httpMethod;
            this.controller = controller;
        }

        public void executeController(HttpWrapper httpWrapper){
            this.controller.execute(httpWrapper);
        }

        public String getUrl() {
            return url;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }
    }
}
