package jet.task.websocketclient.messaging;

import jet.task.websocketclient.domain.Player;

public class MessageBuilder {

    public static Message buildStartGameMsg() {
        return buildMsg(PayloadType.START_GAME, null);
    }

    public static Message buildTurnMadeMsg(int number) {
        return buildMsg(PayloadType.TURN_MADE, number);
    }

    private static Message buildMsg(PayloadType type, Object payload) {
        Message message = new Message();
        message.setType(type);
        message.setSender(Player.getMe().getName());
        message.setPayload(payload);
        return message;
    }
}
