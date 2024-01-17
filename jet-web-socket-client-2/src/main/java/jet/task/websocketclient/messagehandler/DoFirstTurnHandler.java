package jet.task.websocketclient.messagehandler;

import jet.task.websocketclient.connection.SessionHandlerAdapter;
import jet.task.websocketclient.messaging.Message;
import jet.task.websocketclient.messaging.MessageBuilder;
import jet.task.websocketclient.ui.GameUI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoFirstTurnHandler extends MessageHandler {

    private final SessionHandlerAdapter sessionHandlerAdapter;

    @Override
    protected void doHandle(Message message) {
        int startingNumber = GameUI.chooseStaringNumber();
        sessionHandlerAdapter.sendMessage(MessageBuilder.buildTurnMadeMsg(startingNumber));
    }
}
