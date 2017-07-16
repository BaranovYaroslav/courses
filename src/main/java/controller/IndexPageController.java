package controller;

import constants.ControllerConstants;
import dispatcher.HttpWrapper;
import service.InformationService;
import service.ServiceLoader;

import java.io.IOException;

/**
 * Controller that provide information to be represented at index page.
 *
 * @author Yaroslav Baranov
 */
public class IndexPageController implements Controller {

    private InformationService informationService = ServiceLoader.getInstance().getService(InformationService.class);

    /**
     * Method that send response to user browser.
     *
     * @param httpWrapper holder of http request and response.
     * @see dispatcher.HttpWrapper
     */
    @Override
    public void execute(HttpWrapper httpWrapper) {
        try {
            httpWrapper.getResponse().getWriter().write(getInformationAsJSON());
            httpWrapper.getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method provide analytic information.
     *
     * @see service.InformationService
     */
    private String getInformationAsJSON() {
        return String.format(ControllerConstants.INFORMATION_AS_JSON_PATTERN,
                             informationService.getCoursesNumber(),
                             informationService.getStudentsNumber(),
                             informationService.getProfessorsNumber());
    }

}
