package jet.task.websocketserver.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Game {

    /**  Game initiator, always does the first turn. */
    private String player1;

    /** Game joiner. */
    private String player2;

    /** the latest played turn, [null] means no turn played yet. */
    private Integer currentNumber;

    /** 1 = next turn on player 1.
     *  2 = next turn on player 2 */
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private int nextTurnOn = 1; // 1 or 2

    /** Current {@link GameState} of the game */
    private GameState state;

    /** 1 = player 1 won.
     *  2 = player 2 won.
     *  0 = the game has not ended yet. */
    @Setter(AccessLevel.NONE)
    private int winner;

    public void setWinner(String playerName) {
        if (playerName.equals(player1)) {
            winner = 1;
        } else if (playerName.equals(player2)) {
            winner = 2;
        } else {
            throw new IllegalArgumentException("Unknown player name " + playerName);
        }
    }

    public void switchTurn() {
        nextTurnOn = nextTurnOn == 1 ? 2 : 1;
    }

    public String getNextTurnOn() {
        return switch (nextTurnOn) {
            case 1 -> player1;
            case 2 -> player2;
            default -> null;
        };
    }
}
