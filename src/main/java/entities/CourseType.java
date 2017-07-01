package entities;

/**
 * Created by Ярослав on 30.06.2017.
 */
public enum CourseType {
    MATH("Math"), IT("IT"), NATURAL("Natural"),
    HUMANITIES("Humanities"), OTHER("Other");

    private String type;

    CourseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
