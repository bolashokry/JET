package jet.task.websocketclient.messagehandler;

import jet.task.websocketclient.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WaitHandler extends MessageHandler {

    @Override
    protected void doHandle(Message message) {
        System.out.println(message.getPayload());
    }
}
