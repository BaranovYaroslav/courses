package entities;

import constants.ControllerConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that represents course entity.
 *
 * @author Yaroslav Baranov
 */
public class Course {

    private int id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private User professor;

    private int numberOfStudents;

    private CourseType type;

    private double price;

    private boolean isFree;

    private Location location;

    private List<Student> students;

    public Course() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public User getProfessor() {
        return professor;
    }

    public List<Student> getStudents() {
        return students;
    }

    public CourseType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean getIsFree() {
        return isFree;
    }

    public Location getLocation() {
        return location;
    }

    public String getStartDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ControllerConstants.DEFAULT_TIME_PATTERN);
        return formatter.format(startDate);
    }

    public String getEndDateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ControllerConstants.DEFAULT_TIME_PATTERN);
        return  formatter.format(endDate);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setProfessor(User professor) {
        this.professor = professor;
    }

    public static Builder newBuilder() {
        return new Course().new Builder();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", professor=" + professor +
                ", numberOfStudents=" + numberOfStudents +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", isFree=" + isFree +
                ", location=" + location +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (isFree != course.isFree) return false;
        if (numberOfStudents != course.numberOfStudents) return false;
        if (price != course.price) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (endDate != null ? !endDate.equals(course.endDate) : course.endDate != null) return false;
        if (location != null ? !location.equals(course.location) : course.location != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (professor != null ? !professor.equals(course.professor) : course.professor != null) return false;
        if (startDate != null ? !startDate.equals(course.startDate) : course.startDate != null) return false;
        if (students != null ? !students.equals(course.students) : course.students != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (professor != null ? professor.hashCode() : 0);
        result = 31 * result + numberOfStudents;
        result = (int) (31 * result + price);
        result = 31 * result + (isFree ? 1 : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (students != null ? students.hashCode() : 0);
        return result;
    }

    public class Builder {

        private Builder() {}

        public Builder setId(int id) {
            Course.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            Course.this.name = name;
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            Course.this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            Course.this.endDate = endDate;
            return this;
        }

        public Builder setDescription(String description) {
            Course.this.description = description;
            return this;
        }

        public Builder setProfessor(User professor) {
            Course.this.professor = professor;
            return this;
        }

        public Builder setNumberOfStudents(int numberOfStudents) {
            Course.this.numberOfStudents = numberOfStudents;
            return this;
        }

        public Builder setStudents(List<Student> students) {
            Course.this.students = students;
            return this;
        }

        public Builder setPrice(double price) {
            Course.this.price = price;
            return this;
        }

        public Builder setFree(boolean isFree) {
            Course.this.isFree = isFree;
            return this;
        }

        public Builder setLocation(Location location) {
            Course.this.location = location;
            return this;
        }

        public Builder setType(CourseType type) {
            Course.this.type = type;
            return this;
        }

        public Course build() {
            return Course.this;
        }
    }
}
