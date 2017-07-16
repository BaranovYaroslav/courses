package entities;

/**
 * Class that represents user entity.
 *
 * @author Yaroslav Baranov
 */
public class User {

    protected int id;

    protected String login;

    protected String fullName;

    protected String email;

    protected String password;

    protected Role role;

    public User() {}

    public User(Builder<?> b) {
        this.id = b.id;
        this.login = b.login;
        this.fullName = b.fullName;
        this.email = b.email;
        this.password = b.password;
        this.role = b.role;
    }

    public User(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.getRole().equals(user.role.getRole()) : user.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    public static class Builder<B extends Builder<B>> {

        protected int id;

        protected String login;

        protected String fullName;

        protected String email;

        protected String password;

        protected Role role;

        protected Builder() {
        }

        public B setId(int id) {
            this.id = id;
            return (B) this;
        }

        public B setLogin(String login) {
            this.login = login;
            return (B) this;
        }

        public B setFullName(String fullName) {
            this.fullName = fullName;
            return (B) this;
        }

        public B setEmail(String email) {
            this.email = email;
            return (B) this;
        }

        public B setPassword(String password) {
            this.password = password;
            return (B) this;
        }

        public B setRole(Role role) {
            this.role = role;
            return (B) this;
        }

        public User build(){
            return new User(this);
        }
    }
}
