package jet.task.websocketserver.controller;

import jet.task.websocketserver.messeging.Message;
import jet.task.websocketserver.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final GameService gameService;

    @MessageMapping("/play")
    @SendTo("/topic/response")
    public Message process(@Payload Message message) {
        log.info("Received message: {}", message);
        switch (message.getType()) {
            case START_GAME -> {
                return gameService.handleStartGameMsg(message);
            }
            case TURN_MADE -> {
                return gameService.handleTurnMadeMsg(message);
            }
            default -> throw new UnsupportedOperationException();
        }
    }
}
