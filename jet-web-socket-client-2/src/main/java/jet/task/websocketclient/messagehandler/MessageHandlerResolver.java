package jet.task.websocketclient.messagehandler;

import jet.task.websocketclient.messaging.PayloadType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageHandlerResolver {

    private final WaitHandler waitHandler;
    private final DoFirstTurnHandler doFirstTurnHandler;
    private final DoMoveHandler doMoveHandler;
    private final GameEndedHandler gameEndedHandler;

    public MessageHandler resolve(PayloadType type) {
        switch (type) {
            case WAIT -> {
                return waitHandler;
            }
            case DO_FIRST_TURN -> {
                return doFirstTurnHandler;
            }
            case DO_MOVE -> {
                return doMoveHandler;
            }
            case GAME_ENDED -> {
                return gameEndedHandler;
            }
            default -> throw new UnsupportedOperationException();
        }

    }
}
