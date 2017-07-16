package entities;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class that represents student entity.
 *
 * @author Yaroslav Baranov
 */
public class Student extends User {

    private List<Course> courses;

    private List<Feedback> feedbacks;

    public Student() {
        this.courses = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public Student(Builder builder) {
        super(builder);
        courses = builder.courses;
        feedbacks = builder.feedbacks;
    }

    public Student(User user) {
        super(user);
        this.courses = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder extends User.Builder<Builder> {

        protected List<Course> courses;

        protected List<Feedback> feedbacks;

        public Builder setCourses(List<Course> courses) {
            this.courses = courses;
            return this;
        }

        public Builder setFeedbacks(List<Feedback> feedbacks) {
            this.feedbacks = feedbacks;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
