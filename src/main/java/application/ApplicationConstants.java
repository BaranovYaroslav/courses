package application;

/**
 * Created by Ярослав on 15.04.2017.
 */
public interface ApplicationConstants {

    String BASE_APPLICATION_URL = "/";

    String RESOURCES_LOCATION = "/resources/";

    String PAGES_LOCATION = "/pages";

    String URL_FOR_LOGIN = "/app/login";

    String HASHING_ALGORITHM = "SHA-256";

    int DEFAULT_NON_INITIALIZED_VALUE_OF_INTEGER = -1;

    int DEFAULT_NULL_VALUE_FOR_NUMBERS = 0;

    long DEFAULT_TIMEOUT_TIME_FOR_DB = 60000;

    String INCORRECT_INPUT_DATA_MESSAGE = "Incorrect input data";
}
