package controller;

import dispatcher.Controller;
import dispatcher.HttpWrapper;
import service.InformationService;
import service.ServiceLoader;

import java.io.IOException;

/**
 * Created by Ярослав on 08.06.2017.
 */
public class IndexPageController implements Controller {

    private InformationService informationService = ServiceLoader.getInstance().getService(InformationService.class);

    @Override
    public void execute(HttpWrapper httpWrapper) {
        try {
            httpWrapper.getResponse().getWriter().write(getInformationAsJSON());
            httpWrapper.getResponse().getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getInformationAsJSON() {
        return String.format("{\"courseNumber\": %d," +
                        "\"studentNumber\": %d," +
                        "\"professorNumber\": %d}",
                            informationService.getCoursesNumber(),
                            informationService.getStudentsNumber(),
                            informationService.getProfessorsNumber());
    }

}
