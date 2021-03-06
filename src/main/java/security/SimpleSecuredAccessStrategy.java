package security;

import constants.ApplicationConstants;
import constants.LoggerMessage;
import constants.Messages;
import dispatcher.HttpWrapper;
import entities.UserRole;
import service.NavigationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import service.NotificationService;

import java.io.IOException;

/**
 * Realization of SecuredAccessStrategy.
 *
 * @see security.SecuredAccessStrategy
 * @author Yaroslav Baranov
 */
public class SimpleSecuredAccessStrategy implements SecuredAccessStrategy {

    private Logger LOGGER = Logger.getLogger(SimpleSecuredAccessStrategy.class);

    private ApplicationSecurityContext securityContext;

    private ResourceToRoleMapper resourceToRoleMapper = ResourceToRoleMapper.getInstance();

    public SimpleSecuredAccessStrategy(ApplicationSecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    /**
     * Method that process user access to app resources.
     *
     * @param url resource to be accessed
     * @param httpWrapper holder of http request and response
     */
    @Override
    public void processAccess(String url, HttpWrapper httpWrapper, FilterChain filterChain) throws IOException, ServletException{
        if(url == null) {
            url = ApplicationConstants.BASE_APPLICATION_URL;
        }

        UserRole accessibleRole = resourceToRoleMapper.getAccessibleRoleForUrl(url);
        if (accessibleRole == null) {
            filterChain.doFilter(httpWrapper.getRequest(), httpWrapper.getResponse());
        }

        else {
            if(securityContext.isUserInRole(securityContext.getCurrentUser(httpWrapper.getRequest()), accessibleRole)){
                filterChain.doFilter(httpWrapper.getRequest(), httpWrapper.getResponse());
            }
            else {
                LOGGER.warn(LoggerMessage.ON_UNSUCCESSFUL_ACCESS_TO_RESOURCE_MESSAGE + url);
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
