package jet.task.websocketserver.messeging;

import lombok.Data;

@Data
public class Message {
    private PayloadType type;
    private String sender;
    private String recipient;
    private Object payload;
}
