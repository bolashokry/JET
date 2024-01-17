package jet.task.websocketserver.service;

import jet.task.websocketserver.domain.Game;
import jet.task.websocketserver.exception.GameValidationError;
import jet.task.websocketserver.messeging.Message;
import jet.task.websocketserver.messeging.MessageBuilder;
import jet.task.websocketserver.repo.GameRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static jet.task.websocketserver.domain.GameState.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final GameRepo gameRepo;
    private final GameValidator gameValidator;

    public Message handleStartGameMsg(Message startGameMsg) {
        final Game currentGame = gameRepo.startOrGetCurrentGame();
        final String msgSender = startGameMsg.getSender();
        if (NEW == currentGame.getState()) {
            currentGame.setPlayer1(msgSender);
            currentGame.setState(PENDING);
            log.info("Player 1 {} registered. The game is in PENDING state", msgSender);
            return MessageBuilder.buildWaitMsg(msgSender);

        } else if (PENDING == currentGame.getState()) {
            currentGame.setPlayer2(msgSender);
            currentGame.setState(READY);
            log.info("Player 2 {} registered. The game is in READY state", msgSender);
            return MessageBuilder.buildDoFirstTurnMsg(currentGame.getPlayer1());

        }
        final String errorMsg = "Can not process START_GAME msg because there is already an open game";
        log.error(errorMsg);
        return MessageBuilder.buildErrorMsg(msgSender, errorMsg);

    }

    public Message handleTurnMadeMsg(Message turnMadeMsg) {
        final String msgSender = turnMadeMsg.getSender();
        final int playedNumber = (int) turnMadeMsg.getPayload();

        final Game currentGame = gameRepo.getCurrentGame().orElse(new Game());
        if (currentGame.getState() == READY) {
            currentGame.setState(IN_PROGRESS);
            log.info("Current game moved to IN_PROGRESS state");
        }
        try {
            gameValidator.validateGameInProgress(currentGame);
            gameValidator.validatePlayerHasTurn(currentGame, msgSender);

            // update game state
            currentGame.setState(IN_PROGRESS);
            currentGame.switchTurn();
            currentGame.setCurrentNumber(playedNumber);
            log.info("The turn {} by {} was made successfully", playedNumber, msgSender);

            if (isGameEnded(currentGame)) {
                gameRepo.endCurrentGame();
                log.info("The current game has ended, {} is the winner", msgSender);
                return MessageBuilder.buildGameEndedMsg(msgSender);
            } else {
                return MessageBuilder.buildDoMoveMsg(getOpponentName(msgSender), playedNumber);
            }

        } catch (GameValidationError ex) {
            log.error(ex.getMessage());
            return MessageBuilder.buildErrorMsg(msgSender, ex.getMessage());
        }
    }


    private boolean isGameEnded(Game currentGame) {
        return currentGame.getCurrentNumber() == 1;
    }

    private String getOpponentName(String oneOpponent) {
        final Game currentGame = gameRepo.getCurrentGame().orElseThrow();
        if (currentGame.getPlayer1().equals(oneOpponent)) {
            return currentGame.getPlayer2();
        }
        if (currentGame.getPlayer2().equals(oneOpponent)) {
            return currentGame.getPlayer1();
        }
        throw new IllegalArgumentException();
    }


}
