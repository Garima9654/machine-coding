package org.example;

import java.util.*;

public class Repository {
    private List<List<String>> grid;
    private Map<Type, Player> players;

    public Repository(){
        grid = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add("-");
            }
            grid.add(row);
        }

        players= new HashMap<>();
    }

    public void addPlayer(Player player){
        players.put(player.getType(), player);
    }

    public String makeMove(String move, int r, int c){
        if(!Type.isValid(move)){
            return "Invalid Move";
        }
        if (r < 0 || r >= 3 || c < 0 || c >= 3) {
            return "Invalid Move";
        }
        Type type = Type.of(move);
        Player player = players.get(type);
        if(player==null || !grid.get(r).get(c).equals("-")){
            return "Invalid Move";
        }
        String winner = player.getName() + " won the game";
        grid.get(r).set(c, move);
        String tmp = grid.get(r).get(c);
        boolean flag= true;
        for(int i=0; i<3; i++){
            if(!flag) break;
            if(!tmp.equals(grid.get(r).get(i))){
                flag= false;
            }
        }
        if(flag){
            return winner;
        }
        flag= true;
        for(int i=0; i<3; i++){
            if(!flag) break;
            if(!tmp.equals(grid.get(i).get(c))){
                flag= false;
            }
        }
        if(flag){
            return winner;
        }
        flag= true;
        if(r==c){
            int rTmp= 0, cTmp=0;
            for(; rTmp<3 && cTmp<3; rTmp++, cTmp++){
                if(!flag) break;
                if(!tmp.equals(grid.get(rTmp).get(cTmp))){
                    flag= false;
                }
            }
            if(flag){
                return winner;
            }
            flag= true;
            rTmp= 0; cTmp=2;
            for(; rTmp<3 && cTmp>=0; rTmp++, cTmp--){
                if(!flag) break;
                if(!tmp.equals(grid.get(rTmp).get(cTmp))){
                    flag= false;
                }
            }
            if(flag){
                return winner;
            }
        }
        return "move next";
    }

    public boolean gameOver(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(grid.get(i).get(j).equals("-")){
                    return false;
                }
            }
        }
        return true;
    }

    public void diplay(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(grid.get(i).get(j)+ " ");
            }
            System.out.println();
        }
    }
}
