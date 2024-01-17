package jet.task.websocketclient.ui;

import jet.task.websocketclient.config.GameConfig;
import jet.task.websocketclient.domain.Player;
import jet.task.websocketclient.domain.PlayingMode;

import java.util.Scanner;

public class GameUI {

    public static void collectPlayerAndGameInfo() {
        System.out.print("Enter your name:");
        String nameInput = new Scanner(System.in).nextLine();
        Player me = Player.getMe();
        me.setName(nameInput);

        boolean modeSelected = false;
        do {
            char modeInput = acceptUserCharInput("Play Manual (M) or Automatic (A)? ");
            switch (modeInput) {
                case 'M' -> {
                    GameConfig.setPlayingMode(PlayingMode.MANUAL);
                    modeSelected = true;
                    }
                case 'A' -> {
                    GameConfig.setPlayingMode(PlayingMode.AUTOMATIC);
                    modeSelected = true;
                }
                default -> System.out.println("Invalid input please choose from (M | A)");
            }
        } while (!modeSelected);
    }

    public static int chooseStaringNumber() {
        System.out.print("Choose the starting number:");
        String input = new Scanner(System.in).nextLine();
        return Integer.parseInt(input);
    }

    public static int doMove(int currentNumber) {
        System.out.printf("The current number is %d. What is your next move? [+1 | -1 | 0] (Type + | - | 0)%n", currentNumber);
        boolean validNumberSelected = false;
        int newNumber = currentNumber;
        do {
            newNumber = currentNumber;
            char input = acceptUserCharInput("Please select (+ | - | 0): ");
            switch (input) {
                case '+' -> newNumber++;
                case '-' -> newNumber--;
                case '0' -> {}
                default -> {
                    System.out.println("Invalid input please choose from (+ | - | 0)");
                    continue;
                }
            }
            validNumberSelected = isValidNumber(newNumber);
        } while (!validNumberSelected);
        return newNumber / 3;
    }

    private static char acceptUserCharInput(String inputPrompt) {
        System.out.print(inputPrompt);
        return new Scanner(System.in).next().charAt(0);
    }

    private static boolean isValidNumber(int newNumber) {
        boolean validNumber = newNumber % 3 == 0;
        if (!validNumber) {
            System.out.println("The resulted number should be dividable by 3. Try again!");
        }
        return validNumber;
    }
}
