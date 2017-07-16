package controller;

import constants.ControllerConstants;
import constants.RequestParameter;
import dispatcher.HttpWrapper;

import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;

/**
 * Controller that provide localization.
 *
 * @author Yaroslav Baranov
 */
public class LocaleController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LocaleController.class);

    /**
     * Method that locale from request ans save it in session.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        String lang = httpWrapper.getRequest().getParameter(RequestParameter.LANGUAGE);

        if(lang != null && !lang.isEmpty()) {
            httpWrapper.getRequest().getSession().setAttribute(ControllerConstants.LANGUAGE, lang);
        } else {
            lang = (String) httpWrapper.getRequest().getSession().getAttribute(ControllerConstants.LANGUAGE);
            if(lang == null) {
                lang = ControllerConstants.DEFAULT_LANGUAGE_IDENTIFICATOR;
            }
        }

        String langDescriptor = lang + ControllerConstants.UNDERSCORE_SYMBOL + lang.toUpperCase();

        Config.set(httpWrapper.getRequest().getSession(), Config.FMT_LOCALE, langDescriptor);
        httpWrapper.getRequest().getSession().setAttribute(ControllerConstants.LOCALE, langDescriptor);
    }
}
