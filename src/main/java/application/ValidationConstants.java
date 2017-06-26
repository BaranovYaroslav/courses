package application;

/**
 * Created by Ярослав on 26.06.2017.
 */
public interface ValidationConstants {
    String LOGIN_REGEX = "^[a-z0-9_-]{3,15}$";
    String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}$";
    String NAME_REGEX = "^\\pL+[\\pL\\pZ\\pP]{0,}$";
    String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}
