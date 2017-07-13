package controller;

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

    private final String LANG = "lang";

    private final String LOCALE = "locale";

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
            httpWrapper.getRequest().getSession().setAttribute(LANG, lang);
        } else {
            lang = (String) httpWrapper.getRequest().getSession().getAttribute(LANG);
            if(lang == null) {
                lang = "en";
            }
        }

        String langDescriptor = lang + "_" + lang.toUpperCase();

        Config.set(httpWrapper.getRequest().getSession(), Config.FMT_LOCALE, langDescriptor);
        httpWrapper.getRequest().getSession().setAttribute(LOCALE, langDescriptor);
    }
}
