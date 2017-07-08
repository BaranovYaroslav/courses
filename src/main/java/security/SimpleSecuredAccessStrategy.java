package security;

import constants.ApplicationConstants;
import constants.Messages;
import dispatcher.HttpWrapper;
import service.NavigationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import service.NotificationService;

import java.io.IOException;

/**
 * Created by Ярослав on 21.04.2017.
 */
public class SimpleSecuredAccessStrategy implements SecuredAccessStrategy {

    private Logger LOGGER = Logger.getLogger(SimpleSecuredAccessStrategy.class);

    private ApplicationSecurityContext securityContext;

    private ResourceToRoleMapper resourceToRoleMapper = ResourceToRoleMapper.getInstance();

    public SimpleSecuredAccessStrategy(ApplicationSecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @Override
    public void processAccess(String url, HttpWrapper httpWrapper, FilterChain filterChain) throws IOException, ServletException{
        if(url == null) {
            url = ApplicationConstants.BASE_APPLICATION_URL;
        }

        String accessibleRole = resourceToRoleMapper.getAccessibleRoleForUrl(url);
        if (accessibleRole == null) {
            filterChain.doFilter(httpWrapper.getRequest(), httpWrapper.getResponse());
        }

        else {
            if(securityContext.isUserInRole(securityContext.getCurrentUser(httpWrapper.getRequest()), accessibleRole)){
                filterChain.doFilter(httpWrapper.getRequest(), httpWrapper.getResponse());
            }
            else {
                LOGGER.warn("Unsuccessful attempt to access resource: " + url + "!");
                if(securityContext.getCurrentUser(httpWrapper.getRequest()) == null) {
                    NavigationService.navigateTo(httpWrapper, ApplicationConstants.URL_FOR_LOGIN);
                }
                else {
                    NotificationService.notify(httpWrapper, Messages.MESSAGE_403);
                }
            }
        }
    }

    public ApplicationSecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(ApplicationSecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public ResourceToRoleMapper getResourceToRoleMapper() {
        return resourceToRoleMapper;
    }

    public void setResourceToRoleMapper(ResourceToRoleMapper resourceToRoleMapper) {
        this.resourceToRoleMapper = resourceToRoleMapper;
    }
}
