package entities;

/**
 * Enumeration of course types.
 *
 * @author Yaroslav Baranov
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
