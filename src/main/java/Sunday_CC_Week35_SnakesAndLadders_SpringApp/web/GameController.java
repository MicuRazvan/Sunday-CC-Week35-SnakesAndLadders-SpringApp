package Sunday_CC_Week35_SnakesAndLadders_SpringApp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public GameState start(@RequestParam String p1, @RequestParam String p2) {
        return gameService.startNewGame(p1, p2);
    }

    @PostMapping("/roll")
    public GameState roll(@RequestParam String gameId) {
        return gameService.playTurn(gameId);
    }
}
