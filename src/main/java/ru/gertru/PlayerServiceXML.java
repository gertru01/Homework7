package ru.gertru;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

public class PlayerServiceXML implements PlayerService {

    private final Path filePath = Path.of("src/main/java/resources/playersData.xml");
    private final JAXBContext context = JAXBContext.newInstance(Players.class);
    private final Marshaller marshaller = context.createMarshaller();
    private final Unmarshaller unmarshaller = context.createUnmarshaller();
    private Collection<Player> playersCollection = new ArrayList<>();
    private Players players = new Players(playersCollection);
    private Player player;

    public PlayerServiceXML() throws JAXBException {
    }


    @Override
    public Player getPlayerById(int id) throws IOException, JAXBException {

        playersCollection = getPlayers();
        player = null;
        for (Player pl : playersCollection) {
            if (id == pl.getId()) {
                player = pl;
            }
        }
        return player;
    }

    @Override
    public Collection<Player> getPlayers() throws IOException, JAXBException {

        players = (Players) unmarshaller.unmarshal(filePath.toFile());
        return players.getPlayers();
    }

    @Override
    public int createPlayer(String nickname) throws IOException, JAXBException {

        player = new Player(getUniqID(), nickname, 0, false);
        playersCollection.add(player); // Не обновляем players, т.к. коллекция была обновлена вызовом getUniqID
        saveChanges(playersCollection);
        return player.getId();
    }

    @Override
    public Player deletePlayer(int id) throws IOException, JAXBException {

        player = getPlayerById(id);
        playersCollection = getPlayers();
        playersCollection.remove(player);
        saveChanges(playersCollection);
        return player;
    }

    @Override
    public int addPoints(int playerId, int points) throws IOException, JAXBException {

        player = getPlayerById(playerId);
        playersCollection = getPlayers();
        playersCollection.remove(player);
        player.setPoints(player.getPoints() + points);
        playersCollection.add(player);
        saveChanges(playersCollection);
        return player.getPoints();
    }

    private void saveChanges(Collection<Player> newPlayers) throws JAXBException {

        players.setPlayers(newPlayers);
        marshaller.marshal(players, filePath.toFile());
    }

    private int getUniqID() throws IOException, JAXBException {

        playersCollection = getPlayers();
        int max = 0;
        for (Player pl : playersCollection) {
            if (max < pl.getId()) {
                max = pl.getId();
            }
        }
        return (max + 1);
    }
}
