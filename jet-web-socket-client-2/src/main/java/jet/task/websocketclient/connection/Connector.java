package jet.task.websocketclient.connection;

import jet.task.websocketclient.messaging.MessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
@RequiredArgsConstructor
public class Connector {

    @Value("${server.url}")
    private String serverUrl;

    private final WebSocketStompClient stompClient;
    private final SessionHandlerAdapter sessionHandlerAdapter;


    public void connect() {
        try {
            log.info("Connecting to the server: {} ...", serverUrl);
            stompClient.connect(serverUrl, sessionHandlerAdapter).get();
            log.info("Connected!");
            sessionHandlerAdapter.sendMessage(MessageBuilder.buildStartGameMsg());
            Thread.currentThread().join(); // Just to not let the program close immediately.

        } catch (InterruptedException | ExecutionException ex) {
            log.error("An error occurred while connecting to the server", ex);
            throw new RuntimeException(ex);
        }
    }
}
