package jet.task.websocketclient.messagehandler;

import jet.task.websocketclient.config.GameConfig;
import jet.task.websocketclient.connection.SessionHandlerAdapter;
import jet.task.websocketclient.messaging.MessageBuilder;
import jet.task.websocketclient.messaging.Message;
import jet.task.websocketclient.ui.GameUI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoMoveHandler extends MessageHandler {

    private final SessionHandlerAdapter sessionHandlerAdapter;

    @Override
    protected void doHandle(Message message) {
        int currentNumber = (int) message.getPayload();

        int newNumber = currentNumber;
        switch (GameConfig.getPlayingMode()) {
            case MANUAL -> newNumber = GameUI.doMove(currentNumber);
            case AUTOMATIC -> newNumber = doMoveAutomatically(currentNumber);
        }

        log.info("New number is [{}]", newNumber);
        sessionHandlerAdapter.sendMessage(MessageBuilder.buildTurnMadeMsg(newNumber));
    }

    private int doMoveAutomatically(int currentNumber) {
        int newNumber = 0;
        if (currentNumber % 3 == 0) {
            newNumber = currentNumber;
        } else if ((currentNumber - 1) % 3 == 0) {
            newNumber = --currentNumber ;
        } else if ((currentNumber + 1) % 3 == 0) {
            newNumber = ++currentNumber;
        }
        return newNumber / 3;
    }
}
