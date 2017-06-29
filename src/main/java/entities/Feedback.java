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

    public int getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public User getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public static Builder newBuilder() {
        return new Feedback().new Builder();
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", student=" + student +
                ", course=" + course +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;

        Feedback feedback = (Feedback) o;

        if (id != feedback.id) return false;
        if (Double.compare(feedback.score, score) != 0) return false;
        if (comment != null ? !comment.equals(feedback.comment) : feedback.comment != null) return false;
        if (course != null ? !course.equals(feedback.course) : feedback.course != null) return false;
        if (student != null ? !student.equals(feedback.student) : feedback.student != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }

    public class Builder {

        private Builder() {}

        public Builder setId(int id) {
            Feedback.this.id = id;
            return this;
        }

        public Builder setComment(String comment) {
            Feedback.this.comment = comment;
            return this;
        }

        public Builder setScore(double score) {
            Feedback.this.score = score;
            return this;
        }

        public Builder setStudent(User student) {
            Feedback.this.student = student;
            return this;
        }

        public Builder setCourse(Course course) {
            Feedback.this.course = course;
            return this;
        }

        public Feedback build() {
            return Feedback.this;
        }
    }
}
