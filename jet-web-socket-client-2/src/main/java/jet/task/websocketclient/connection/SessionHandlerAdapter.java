package jet.task.websocketclient.connection;

import jet.task.websocketclient.messagehandler.MessageHandlerResolver;
import jet.task.websocketclient.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@Slf4j
public class SessionHandlerAdapter extends StompSessionHandlerAdapter {

    private StompSession session;
    private final MessageHandlerResolver messageHandlerResolver;

    private final String gameDestination;
    private final String responseTopic;

    public SessionHandlerAdapter(@Lazy MessageHandlerResolver messageHandlerResolver,
                                 @Value("${game.destination}") String gameDestination,
                                 @Value("${game.response-topic}") String responseTopic) {
        this.messageHandlerResolver = messageHandlerResolver;
        this.gameDestination = gameDestination;
        this.responseTopic = responseTopic;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        if (this.session == null) {
            this.session = session;
        }
        session.subscribe(responseTopic, this);
        log.info("Subscribed to {}", responseTopic);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message message = (Message) payload;
        log.info("Received payload: [{}]", message);
        messageHandlerResolver
                .resolve(message.getType())
                .handle(message);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    public void sendMessage(Message message) {
        log.debug("Sending message {}", message);
        session.send(gameDestination, message);
    }
}
