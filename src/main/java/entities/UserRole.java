package entities;

/**
 * Created by Ярослав on 19.07.2017.
 */
public enum UserRole {
    ADMIN("admin"), STUDENT("student"), PROFESSOR("professor");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
