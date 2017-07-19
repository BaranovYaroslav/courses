package constants;

/**
 * Holder logger messages constants.
 *
 * @author Yaroslav Baranov
 */
public interface LoggerMessage {
    String ON_APPLICATION_STARTED_MESSAGE = "Application started";
    String ON_COURSE_DELETED_MESSAGE = "Course deleted: ";
    String ON_UNSUCCESSFUL_LOGIN_MESSAGE = "Unsuccessful login:";
    String ON_COURSE_CREATED_MESSAGE = "New course created: ";
    String ON_NEW_PROFESSOR_ACCOUNT_CREATED_MESSAGE = "New professor account created: ";
    String ON_COURSE_UPDATED_MESSAGE = "Course updated: ";
    String ON_INCORRECT_RESOURCE_ACCESS_MESSAGE = "Try to access incorrect resource: ";
    String ON_CANT_CREATE_CONNECTION_MESSAGE = "Cannot create connection to the database: ";
    String ON_CANT_CREATE_CONTEXT_MESSAGE = "Can't create jndi context: ";
    String ON_SAVE_TO_DB_EXCEPTION_MESSAGE = "Exception when trying save entity to DB: ";
    String ON_UPDATE_DATA_IN_DB_EXCEPTION_MESSAGE_ = "Cannot execute update query: ";
    String ON_CANT_CREATE_PREPARED_STATEMENT_MESSAGE = "Error creating prepared statement. Query: ";
    String ON_RESULT_SET_EXCEPTION_MESSAGE = "ResultSetFunctions has thrown an exception: ";
    String ON_CANT_CLOSE_RESULT_SET_MESSAGE = "Cannot close ResultSet";
    String ON_SQL_SCRIPT_NOT_FOUND_MESSAGE = "SQL script file not found";
    String ON_EXECUTION_SQL_SCRIPT_EXCEPTION_MESSAGE = "Errors while executing SQL script: ";
    String ON_CANT_CLOSE_CONNECTION_MESSAGE = "Can't close connection: ";
    String ON_UNSUCCESSFUL_ACCESS_TO_RESOURCE_MESSAGE = "Unsuccessful access to resource: ";
    String ON_FORWARD_EXCEPTION_MESSAGE = "Error when trying forward: ";
    String ON_REDIRECT_EXCEPTION_MESSAGE = "Error when trying redirect: ";
    String ON_SERVICE_INITIALIZATION_EXCEPTION_MESSAGE = "Error in initializing of service: ";
    String ON_INVALID_HASHING_ALGORITHM_MESSAGE = "Invalid hashing algorithm: ";
    String ON_BAD_PATH_MESSAGE = "Bad path: ";
    String ON_UNRESOLVED_RESOURCE_MESSAGE = "Cannot load resource";
}
