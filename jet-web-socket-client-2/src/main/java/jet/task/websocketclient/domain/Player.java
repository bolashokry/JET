package jet.task.websocketclient.domain;

import lombok.Data;

@Data
public class Player {

    private static Player player;

    private Player() {
    }

    public static Player getMe() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    private String name;
}
