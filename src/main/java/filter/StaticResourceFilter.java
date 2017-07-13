package filter;

import org.apache.log4j.Logger;
import persistence.dao.UserDao;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter for static resources.
 *
 * @author Yaroslav Baranov
 */
@WebFilter("/*")
public class StaticResourceFilter implements Filter {

    private static Logger LOGGER = Logger.getLogger(UserDao.class);

    public StaticResourceFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if(path.indexOf("/css") > 0){
            chain.doFilter(request, response);
        }
        else if(path.equals("/")) {
            chain.doFilter(request, response);
        }
        else if(path.indexOf(".jsp") > 0){
            chain.doFilter(request, response);
        }
        else  if(path.indexOf(".js") > 0) {
            chain.doFilter(request, response);
        }
        else {
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
