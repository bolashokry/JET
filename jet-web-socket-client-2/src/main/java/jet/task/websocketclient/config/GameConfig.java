package jet.task.websocketclient.config;

import jet.task.websocketclient.domain.PlayingMode;
import lombok.Getter;
import lombok.Setter;

public class GameConfig {

    @Getter
    @Setter
    private static PlayingMode playingMode;

}
