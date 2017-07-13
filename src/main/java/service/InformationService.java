package service;

/**
 * Interface of service to provide analytic information.
 *
 * @author Yaroslav Baranov
 */
public interface InformationService {

    /**
     * Method number of courses.
     *
     * @return number of courses
     */
    public long getCoursesNumber();

    /**
     * Method nu,ber of professors.
     *
     * @return number of professors
     */
    public long getProfessorsNumber();

    /**
     * Method number of students.
     *
     * @return number of students
     */
    public long getStudentsNumber();
}
