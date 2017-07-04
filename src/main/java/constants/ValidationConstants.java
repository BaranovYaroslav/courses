package constants;

/**
 * Created by Ярослав on 26.06.2017.
 */
public interface ValidationConstants {
    String LOGIN_REGEX = "^[a-z0-9_-]{3,15}$";
    String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}$";
    String NAME_REGEX = "^\\pL+[\\pL\\pZ\\pP]{0,}$";
    String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String WHITESPACES_AND_MIN_TWO_CHARACTER_REGEX = "^(?![^@]*@)(?!.*\\s\\s)(?=(?:\\P{L}*\\p{L}){2})\\S.{0,14}\\S$";
    String DOUBLE_REGEX = "^-?\\d+(?:\\.\\d+)?$";
    String POSITIVE_DOUBLE_REGEX = "^\\(?[\\d.]+\\)?$";
    String INTEGER_GREATER_THAN_ZERO_REGEX = "^[1-9][0-9]*$";
    String DATE_REGEX = "^(0?[1-9]|[12][0-9]|3[01])[\\.\\-](0?[1-9]|1[012])[\\.\\-]\\d{4}$";
}
