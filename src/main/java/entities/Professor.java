package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ярослав on 17.04.2017.
 */
public class Professor extends User {

    private List<Course> courses;

    public Professor(int id, String login, String fullName, String email, String password, Role role) {
        super(id, login, fullName, email, password, role);
        this.courses = new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
