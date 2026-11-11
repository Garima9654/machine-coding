package org.example;

import org.example.model.Cell;
import org.example.model.Dice;
import org.example.model.Player;

import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();
        PlayService playService = new PlayService(repository);
        Dice dice = new Dice();
        int id=1;
        try (Scanner scanner = new Scanner(System.in)) {
            int command = parseInt(scanner.nextLine());
            while(command>0){
                String commands[] = scanner.nextLine().trim().split(" ");
                Integer st = parseInt(commands[0]);
                Integer en = parseInt(commands[1]);
                playService.addSnake(st, en);
                command--;
            }
            command = parseInt(scanner.nextLine());
            while(command>0){
                String commands[] = scanner.nextLine().trim().split(" ");
                Integer st = parseInt(commands[0]);
                Integer en = parseInt(commands[1]);
                playService.addLadder(st, en);
                command--;
            }

            command = parseInt(scanner.nextLine());
            while (command>0){
                String name = scanner.nextLine();
                repository.addPlayer(new Player(id, name));
                id++;
                command--;
            }
        }
        int i=1;
        Cell cell = repository.cellByID(1);
        List<Player> all = repository.allPlayers();
        cell.setPlayers(all);
        for(int j=0; j<all.size(); j++){
            Player p = all.get(j);
            repository.addPos(p.getId(), 1);
        }
        while(true){
            Player curr = repository.playerById(i);
            int step = dice.rollDice();
//            cell = repository.cellByID(repository.getPos(curr.getId()));
            cell = playService.makeMove(cell,curr,step);
            if(cell == null){
                break;
            }
            if(playService.anyoneWon(cell)){
                break;
            }
            i++;
            if(i > all.size()){
                i=1;
            }
        }
    }
}