package jet.task.websocketserver.service;

import jet.task.websocketserver.domain.Game;
import jet.task.websocketserver.exception.GameValidationError;
import jet.task.websocketserver.messeging.MessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static jet.task.websocketserver.domain.GameState.IN_PROGRESS;

@Component
@Slf4j
public class GameValidator {

    public void validateGameInProgress(Game game) {
        final Optional<Game> currentGameOpt = Optional.ofNullable(game)
                .filter(g -> IN_PROGRESS.equals(g.getState()));
        if (currentGameOpt.isEmpty()) {
            throw new GameValidationError("Can not process TURN_MADE msg because there is no IN-PROGRESS game");
        }
    }

    public void validatePlayerHasTurn(Game game, String currentTurnMadeBy) {
        if (!game.getNextTurnOn().equals(currentTurnMadeBy)) {
            throw new GameValidationError("It is not " + currentTurnMadeBy + "'s turn");
        }
    }
}
