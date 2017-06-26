package entities;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private List<Course> courses;

    private List<Feedback> feedbacks;

    public Student() {
        this.courses = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public Student(int id, String login, String fullName, String email, String password, Role role) {
        super(id, login, fullName, email, password, role);
    }

    public static Student create(User user) {
        return new Student(user.getId(), user.getLogin(), user.getFullName(), user.getEmail(), user.getPassword(), user.getRole());
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
