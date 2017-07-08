package controller;

import constants.RequestParameter;
import dispatcher.Controller;
import dispatcher.HttpWrapper;

import javax.servlet.jsp.jstl.core.Config;
import org.apache.log4j.Logger;

/**
 * Created by Ярослав on 25.04.2017.
 */
public class LocaleController implements Controller {

    private static Logger LOGGER = Logger.getLogger(LocaleController.class);

    private final String LANG = "lang";

    private final String LOCALE = "locale";

    @Override
    public void execute(HttpWrapper httpWrapper) {
        String lang = (String) httpWrapper.getRequest().getParameter(RequestParameter.LANGUAGE);

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
