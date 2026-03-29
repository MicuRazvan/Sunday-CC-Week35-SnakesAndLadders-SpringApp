package Sunday_CC_Week35_SnakesAndLadders_SpringApp.web;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {
    private final Map<String, GameData> games = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> boardMap = new HashMap<>();

    public GameService() {
        boardMap.put(2, 38); boardMap.put(7, 14); boardMap.put(8, 31);
        boardMap.put(15, 26); boardMap.put(21, 42); boardMap.put(36, 44);
        boardMap.put(51, 67); boardMap.put(71, 91); boardMap.put(78, 98);

        boardMap.put(16, 6); boardMap.put(46, 25); boardMap.put(49, 11);
        boardMap.put(62, 19); boardMap.put(64, 60); boardMap.put(74, 53);
        boardMap.put(89, 68); boardMap.put(92, 88); boardMap.put(95, 75); boardMap.put(99, 80);
    }

    public GameState startNewGame(String p1Name, String p2Name) {
        String id = UUID.randomUUID().toString();
        List<Player> players = Arrays.asList(
                new Player("p1", p1Name, 1),
                new Player("p2", p2Name, 1)
        );
        GameData data = new GameData(id, players);
        games.put(id, data);
        return buildState(data, "Game Started! " + p1Name + "'s turn.");
    }

    public GameState playTurn(String gameId) {
        GameData game = games.get(gameId);
        if (game == null || game.isFinished) return null;

        Player currentPlayer = game.players.get(game.currentPlayerIndex);
        int roll = new Random().nextInt(6) + 1;
        int nextPos = currentPlayer.getPosition() + roll;

        String msg = currentPlayer.getName() + " rolled a " + roll + ". ";

        if (nextPos > 100) {
            msg += "Too high! Stay at 100.";
        } else {
            if (boardMap.containsKey(nextPos)) {
                int finalPos = boardMap.get(nextPos);
                msg += (finalPos > nextPos) ? "LADDER! 🪜" : "SNAKE! 🐍";
                nextPos = finalPos;
            }
            currentPlayer.setPosition(nextPos);
        }

        if (currentPlayer.getPosition() == 100) {
            game.isFinished = true;
            game.winnerName = currentPlayer.getName();
            msg = "🎉 " + currentPlayer.getName() + " WINS!";
        } else {
            game.currentPlayerIndex = (game.currentPlayerIndex == 0) ? 1 : 0;
        }

        game.lastRoll = roll;
        return buildState(game, msg);
    }

    private GameState buildState(GameData data, String msg) {
        return GameState.builder()
                .gameId(data.gameId).players(data.players)
                .currentPlayerIndex(data.currentPlayerIndex)
                .isFinished(data.isFinished).winnerName(data.winnerName)
                .lastRoll(data.lastRoll)
                .boardConfig(boardMap).logMessage(msg)
                .build();
    }

    private static class GameData {
        String gameId;
        List<Player> players;
        int currentPlayerIndex = 0;
        boolean isFinished = false;
        String winnerName;
        int lastRoll;
        GameData(String id, List<Player> p) { this.gameId = id; this.players = p; }
    }
}
