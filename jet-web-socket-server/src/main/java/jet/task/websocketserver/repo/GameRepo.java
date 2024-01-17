package jet.task.websocketserver.repo;

import jet.task.websocketserver.domain.Game;
import jet.task.websocketserver.domain.GameState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GameRepo {
    private final List<Game> oldGames = new ArrayList<>();
    private Game currentGame;

    public Optional<Game> getCurrentGame() {
        return Optional.ofNullable(currentGame);
    }

    public Game startOrGetCurrentGame() {
        if (currentGame == null) {
            currentGame = new Game();
            currentGame.setState(GameState.NEW);
        }
        return currentGame;
    }

    public void endCurrentGame() {
        if (getCurrentGame().isPresent()) {
            getCurrentGame().get().setState(GameState.ENDED);
            oldGames.add(currentGame);
            currentGame = null;
        } else {
            throw new IllegalStateException("There is no current game");
        }
    }
}
