package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents professor entity.
 *
 * @author Yaroslav Baranov
 */
public class Professor extends User {

    private List<Course> courses;

    public Professor (){}

    public Professor(Builder builder) {
        super(builder);
        courses = builder.courses;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static class Builder extends User.Builder<Builder> {

        protected List<Course> courses;

        public Builder setCourses(List<Course> courses) {
            this.courses = courses;
            return this;
        }

        public Professor build() {
            return new Professor(this);
        }

    }

}
