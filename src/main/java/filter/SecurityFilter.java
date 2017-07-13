package filter;

import dispatcher.HttpWrapper;
import security.ApplicationSecurityContext;
import security.SimpleSecuredAccessStrategy;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Filter that provide security access to requested resource.
 *
 * @author Yaroslav Baranov
 */
@WebFilter("/*")
public class SecurityFilter implements Filter {

    private static Logger LOGGER = Logger.getLogger(SecurityFilter.class);

    private ApplicationSecurityContext securityContext = new ApplicationSecurityContext();

    public SecurityFilter() {
        securityContext.setSecuredAccessStrategy(new SimpleSecuredAccessStrategy(securityContext));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Method that process user access to resource according security constrains.
     *
     * @see security.ApplicationSecurityContext
     * @see security.SecuredAccessStrategy
     * @see security.SimpleSecuredAccessStrategy
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpWrapper httpWrapper = new HttpWrapper(request,  response);
        String url = request.getPathInfo();

        securityContext.getSecuredAccessStrategy().processAccess(url, httpWrapper, filterChain);
    }

    @Override
    public void destroy() {
    }
}
