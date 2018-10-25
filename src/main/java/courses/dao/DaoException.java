package courses.dao;


public class DaoException extends RuntimeException {

    DaoException(String message) {
        super(message);
    }

    DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    DaoException(Throwable cause) {
        super(cause);
    }
}
