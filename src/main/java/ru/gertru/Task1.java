package ru.gertru;

import java.io.IOException;
import java.util.Collection;

public class Task1 {

    public static void main(String[] args) throws IOException {

        PlayerServiceJSON service = new PlayerServiceJSON();

        int playerId = service.createPlayer("WinMaster_777");
        service.addPoints(playerId, 100);

        Collection<Player> players = service.getPlayers();
        for (Player player : players) {
            System.out.println(player);
        }

    }
}
