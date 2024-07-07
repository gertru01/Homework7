package ru.gertru;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Collection;
import java.util.Objects;

@XmlRootElement
public class Players {

     private Collection<Player> players;

    public Players(Collection<Player> players) {
        this.players = players;
    }

    public Players() {}

    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Players players1 = (Players) o;
        return Objects.equals(players, players1.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players);
    }

    @Override
    public String toString() {
        return "Players{" +
                "players=" + players +
                '}';
    }
}
