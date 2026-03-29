package Sunday_CC_Week35_SnakesAndLadders_SpringApp.web;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class GameState {
    private String gameId;
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean isFinished;
    private String winnerName;
    private int lastRoll;
    private String logMessage;
    private Map<Integer, Integer> boardConfig;
}