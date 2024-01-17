package jet.task.websocketclient;

import jet.task.websocketclient.connection.Connector;
import jet.task.websocketclient.ui.GameUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WebSocketClientApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(WebSocketClientApplication.class, args);
		GameUI.collectPlayerAndGameInfo();

		Connector connector = context.getBean(Connector.class);
		connector.connect();
	}

}
