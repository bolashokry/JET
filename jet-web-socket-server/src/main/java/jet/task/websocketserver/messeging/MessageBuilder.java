package jet.task.websocketserver.messeging;

public class MessageBuilder {

    public static Message buildWaitMsg(String recipient) {
        return buildMsg(PayloadType.WAIT, recipient, "Please wait till another player joins");
    }

    public static Message buildDoFirstTurnMsg(String recipient) {
        return buildMsg(PayloadType.DO_FIRST_TURN, recipient, null);
    }

    public static Message buildDoMoveMsg(String recipient, int currentNumber) {
        return buildMsg(PayloadType.DO_MOVE, recipient, currentNumber);
    }

    public static Message buildGameEndedMsg(String winner) {
        return buildMsg(PayloadType.GAME_ENDED, null,
                String.format("The game ended and %s is the winner!", winner));
    }

    public static Message buildErrorMsg(String recipient, String error) {
        return buildMsg(PayloadType.ERROR, recipient, error);
    }

    private static Message buildMsg(PayloadType type, String recipient, Object payload) {
        Message message = new Message();
        message.setType(type);
        message.setSender("server");
        message.setRecipient(recipient);
        message.setPayload(payload);
        return message;
    }
}
