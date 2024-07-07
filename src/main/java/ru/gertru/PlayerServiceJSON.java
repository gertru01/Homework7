package ru.gertru;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

public class PlayerServiceJSON implements PlayerService {

    private final Path filePath = Path.of("src/main/java/resources/playersData.json");
    private final ObjectMapper mapper = new ObjectMapper();
    private Collection<Player> players = new ArrayList<>();
    private Player player;

    @Override
    public Player getPlayerById(int id) throws IOException {

        players = getPlayers();
        player = null;
        for (Player pl : players) {
            if (id == pl.getId()) {
                player = pl;
            }
        }
        return player;
    }

    @Override
    public Collection<Player> getPlayers() throws IOException {
        try {
            return mapper.readValue(filePath.toFile(), new TypeReference<>() {
            });
        } catch (MismatchedInputException e) {
            return players; //Если файл пустой, возникает данное исключение
        }
    }

    @Override
    public int createPlayer(String nickname) throws IOException {

        player = new Player(getUniqID(), nickname, 0, false);
        players.add(player); // Не обновляем players, т.к. коллекция была обновлена вызовом getUniqID
        saveChanges(players);
        return player.getId();
    }

    @Override
    public Player deletePlayer(int id) throws IOException {

        player = getPlayerById(id);
        players = getPlayers();
        players.remove(player);
        saveChanges(players);
        return player;
    }

    @Override
    public int addPoints(int playerId, int points) throws IOException {

        player = getPlayerById(playerId);
        players = getPlayers();
        players.remove(player);
        player.setPoints(player.getPoints() + points);
        players.add(player);
        saveChanges(players);
        return player.getPoints();
    }

    private void saveChanges(Collection<Player> newPlayers) throws IOException {

        mapper.writeValue(filePath.toFile(), newPlayers);
    }

    private int getUniqID() throws IOException {

        players = getPlayers();
        int max = 0;
        for (Player pl : players) {
            if (max < pl.getId()) {
                max = pl.getId();
            }
        }
        return (max + 1);
    }
}
