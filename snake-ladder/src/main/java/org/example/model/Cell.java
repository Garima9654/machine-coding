package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final Integer id;
    private final Integer row;
    private final Integer col;
    private List<Player> players;

    public Cell(Integer id, Integer row, Integer col) {
        this.id = id;
        this.row = row;
        this.col = col;
        players = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }
}
