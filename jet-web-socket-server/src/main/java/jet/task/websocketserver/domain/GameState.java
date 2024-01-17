package jet.task.websocketserver.domain;

public enum GameState {

    /** The game has just been created, no players registered yet. */
    NEW,

    /** One player is registered and waiting for the second. */
    PENDING,

    /** Both players registered, the game is ready to start. */
    READY,

    /** The game has been started, i.e. at least one turn was played. */
    IN_PROGRESS,

    /** The game is ended. */
    ENDED
}
