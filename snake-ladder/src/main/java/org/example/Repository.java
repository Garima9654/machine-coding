package org.example;

import org.example.model.Cell;
import org.example.model.Ladder;
import org.example.model.Player;
import org.example.model.Snake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    Map<Integer, Player> players;
    Map<Integer, Snake> snakes;
    Map<Integer, Ladder> ladders;
    Map<Integer, Cell> cells;
    List<List<Cell>> grid;
    Map<Integer, Integer> pos;

    public Repository(){
        players = new HashMap<>();
        snakes = new HashMap<>();
        ladders = new HashMap<>();
        cells = new HashMap<>();
        pos = new HashMap<>();
        Integer num=1;
        grid = new ArrayList<>(10);
        for(int i=0; i<10; i++){
            List<Cell> tmp= new ArrayList<>(10);
            for(int j=0; j<10; j++){
                Cell t= new Cell(num, i, j);
                tmp.add(t);
                cells.put(t.getId(), t);
                num++;
            }
            grid.add(tmp);
        }
    }

    public void addPlayer(Player player){
        players.put(player.getId(), player);
        Cell cell = grid.get(0).get(0);
        cell.addPlayer(player);
    }

    public void addPos(Integer id, Integer cellId){
        pos.put(id, cellId);
    }

    public Integer getPos(Integer id){
        return pos.get(id);
    }

    public List<Player> allPlayers(){
        return  new ArrayList<>(players.values());
    }

    public Snake getSnake(Cell cell){
        return snakes.get(cell.getId());
    }

    public Ladder getLadder(Cell cell){
        return ladders.get(cell.getId());
    }

    public void addSnake(Integer st, Integer en){
        Snake s =new Snake(st, en);
        snakes.put(st, s);
    }

    public void addLadder(Integer st, Integer en){
        Ladder l =new Ladder(st, en);
        ladders.put(st, l);
    }

    public Cell cellByID(Integer id){
        return cells.get(id);
    }

    public Player playerById(Integer id){
        return players.get(id);
    }

    public void display(){
        boolean flag = false;
        for (int i=9; i>=0; i--){
            if(flag){
                for(int j=0; j<10; j++){
                    Cell cell = grid.get(i).get(j);
                    if(cell.getId()<10){
                        System.out.print("0"+ cell.getId() + " ");
                    }else{
                        System.out.print(cell.getId() + " ");
                    }
                }
            }
            else{
                for(int j=9; j>=0; j--){
                    Cell cell = grid.get(i).get(j);
                    if(cell.getId()<10){
                        System.out.print("0"+ cell.getId() + " ");
                    }else{
                        System.out.print(cell.getId() + " ");
                    }
                }
            }
            flag = !flag;
            System.out.println();
        }
    }

}
