package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository();

        try (Scanner scanner = new Scanner(System.in)) {
            // Read two separate player lines.
            for (int i = 0; i < 2; i++) {
                String[] commands =
                        scanner.nextLine().trim().split("\\s+", 2);

                Type type = Type.of(commands[0]);
                String name = commands[1];

                repository.addPlayer(new Player(name, type));
            }

            repository.diplay();

            Type currentTurn = Type.X;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.equalsIgnoreCase("exit")) {
                    break;
                }

                String[] commands = line.split("\\s+");

                if (commands.length != 2) {
                    System.out.println("Invalid Move");
                    continue;
                }

                int r;
                int c;

                try {
                    r = Integer.parseInt(commands[0]) - 1;
                    c = Integer.parseInt(commands[1]) - 1;
                } catch (NumberFormatException exception) {
                    System.out.println("Invalid Move");
                    continue;
                }

                String result =
                        repository.makeMove(currentTurn.name(), r, c);

                if (result.equalsIgnoreCase("Invalid Move")) {
                    System.out.println(result);
                    continue;
                }

                repository.diplay();

                if (!result.equals("move next")) {
                    System.out.println(result);
                    break;
                }

                if (repository.gameOver()) {
                    System.out.println("Game Over");
                    break;
                }

                currentTurn =
                        currentTurn == Type.X ? Type.O : Type.X;
            }
        }
    }
}