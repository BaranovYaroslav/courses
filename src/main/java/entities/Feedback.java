package entities;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class Feedback {

    int id;

    private double score;

    private String comment;

    private User student;

    private Course course;

    public Feedback() {}

    public Feedback(double score, String comment, User student, Course course) {;
        this.score = score;
        this.comment = comment;
        this.student = student;
        this.course = course;
    }

    public static Feedback createEmptyFeedback(Course course, User user) {
        return new Feedback(0, "", user, course);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return getStudent().getFullName() + " " + score + " " + comment;
    }
}
