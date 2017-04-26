package entities;

/**
 * Created by Ярослав on 11.04.2017.
 */
public class User {

    private int id;

    private String login;

    private String fullName;

    private String email;

    private String password;

    private Role role;

    public User() {}

    public User(int id, String login, String fullName, String email, String password, Role role) {
        this.id = id;
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return id + " " + login  + " " + fullName + " " + email + " " + password;
    }

    @Override
    public int hashCode() {
        return 31*(id + login.length() + fullName.length());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof User)){
            return false;
        }

        User user = (User) obj;

        return login.equals(user.getLogin());
    }
}
