package jet.task.websocketclient.messaging;

public enum PayloadType {
    /** CLIENT -> SERVER
     * Message sent from client to server telling that a client wants to start game or join an existing game.
     * This will be decided by the server based on there are already PENDING games or not. */
    START_GAME,

    /** SERVER -> CLIENT
     * Message sent from server to client as an answer to START_GAME message, in case there were no PENDING games
     * on server side. It tells the client to wait until another client joins. */
    WAIT,

    /** SERVER -> CLIENT
     * Message sent from server to client to inform a client to do the first turn. This message is sent
     * as an answer to START_GAME message when game becomes in READY state. */
    DO_FIRST_TURN,

    /** CLIENT -> SERVER
     * Message sent from client to server indicates that a client made a turn. This message could be an answer
     * either to DO_FIRST_TURN or DO_MOVE commands from the server. */
    TURN_MADE,

    /** SERVER -> CLIENT
     * Message sent from server to client to do turn, means the client should do
     * either +1,0,-1 to keep the game going */
    DO_MOVE,

    /** SERVER -> CLIENT
     * Message sent from the server to client informing them the game has ended and there is a winner. */
    GAME_ENDED,

    /** SERVER -> CLIENT
     * Message sent from server to client whenever a generic error happens. */
    ERROR
}
