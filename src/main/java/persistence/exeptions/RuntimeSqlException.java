package persistence.exeptions;

/**
 * SQL exception in runtime.
 *
 * @author Yaroslav Baranov
 */
public class RuntimeSqlException extends RuntimeException {
    public RuntimeSqlException(Throwable cause) {
        super(cause);
    }
}
