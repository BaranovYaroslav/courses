package security;

import dispatcher.HttpWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Ярослав on 21.04.2017.
 */
public interface SecuredAccessStrategy {
    public void processAccess(String url, HttpWrapper httpWrapper, FilterChain filterChain) throws IOException, ServletException;
}
