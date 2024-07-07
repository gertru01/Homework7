package ru.gertru;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.Collection;

public class Task2 {

    public static void main(String[] args) throws JAXBException, IOException {

        PlayerServiceXML service = new PlayerServiceXML();

        int playerId = service.createPlayer("WinMaster_777");
        service.addPoints(playerId, 100);

        Collection<Player> players = service.getPlayers();
        for (Player player : players) {
            System.out.println(player);
        }
    }
}
