package jet.task.websocketserver.exception;

public class GameValidationError extends RuntimeException {

    public GameValidationError(String message) {
        super(message);
    }
}
