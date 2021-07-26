package exception;

import java.util.concurrent.ExecutionException;

public class MyExecutionException extends ExecutionException {
    public MyExecutionException(String message) {
        super(message);
    }
}
