package org.example;

import org.example.model.Cell;
import org.example.model.Ladder;
import org.example.model.Player;
import org.example.model.Snake;

import java.util.Objects;

public class PlayService {
    Repository repository;

    public PlayService(Repository repository){
        this.repository = repository;
    }

    public Cell makeMove(Cell cell, Player player, Integer steps){
        Integer row= cell.getRow();
        Integer col = cell.getCol();
        Integer id= cell.getId();
        int pos = id + steps;
        if(pos>100){
            return cell;
        }
        Cell finalCell= repository.cellByID(pos);
        Ladder ladder = repository.getLadder(finalCell);
        Snake snake= repository.getSnake(finalCell);
        if(!Objects.isNull(ladder)){
            cell.removePlayer(player);
            finalCell = repository.cellByID(ladder.getEnPos());
            finalCell.addPlayer(player);
        }
        else if(!Objects.isNull(snake)){
            cell.removePlayer(player);
            finalCell = repository.cellByID(snake.getEnPos());
            finalCell.addPlayer(player);
        }
        return finalCell;
    }

    public boolean anyoneWon(Cell cell){
        if(cell.getPlayers().isEmpty()){
            return false;
        }
        Player player = cell.getPlayers().getFirst();
        if(cell.getId()==100){
            System.out.println(player.getName() + " won the game");
            return true;
        }
        return false;
    }
    public void addSnake(Integer st, Integer en){
        repository.addSnake(st, en);
    }

    public void addLadder(Integer st, Integer en){
        repository.addLadder(st, en);
    }

}
