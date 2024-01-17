package jet.task.websocketclient.messagehandler;

import jet.task.websocketclient.domain.Player;
import jet.task.websocketclient.messaging.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MessageHandler {
    protected abstract void doHandle(Message payload);

    public void handle(Message message) {
        if (isToMe(message.getRecipient())) {
            doHandle(message);
        } else {
            log.debug("Ignoring not-to-me message");
        }
    }

    private boolean isToMe(String recipient) {
        return recipient == null || Player.getMe().getName().equals(recipient) ;
    }
}
