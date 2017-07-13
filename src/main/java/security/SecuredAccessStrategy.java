package security;

import dispatcher.HttpWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Interface that provide processing of user access to system.
 *
 * @author Yaroslav Baranov
 */
public interface SecuredAccessStrategy {

    /**
     * Method that process user access to app resources.
     *
     * @param url resource to be accessed
     * @param httpWrapper holder of http request and response
     */
    public void processAccess(String url, HttpWrapper httpWrapper, FilterChain filterChain) throws IOException, ServletException;
}
