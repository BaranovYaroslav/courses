package persistence.exeptions;

/**
 * Created by Ярослав on 12.07.2017.
 */
public class RuntimeSqlException extends RuntimeException {
    public RuntimeSqlException(Throwable cause) {
        super(cause);
    }
}
