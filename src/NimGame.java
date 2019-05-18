import java.util.Scanner;

public class NimGame {

    private Scanner scanner;
    private int upperBound;

    public NimGame(int numTotalStones, int upperBound, NimPlayer player1, NimPlayer player2, Scanner scanner){
        this.scanner = scanner;
        this.upperBound = upperBound;

        printInfo(numTotalStones, player1, player2);
        processGame(numTotalStones, player1, player2);
    }

    private void printInfo(int numTotalStone, NimPlayer player1, NimPlayer player2){
        System.out.println();
        System.out.println("Initial stone count: " +  numTotalStone);
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.println("Player 1: " + player1.getGivenName() + " " + player1.getFamilyName());
        System.out.println("Player 2: " + player2.getGivenName() + " " + player2.getFamilyName());
        System.out.println();
    }

    private void processGame(int numTotalStones, NimPlayer player1, NimPlayer player2){
        int order = 1;
        int numStonesLeft = numTotalStones;
        while(numStonesLeft > 0){
            printRemainingStones(numStonesLeft);
            NimPlayer player = playerTurn(order, player1, player2);
            int pick = stoneRemove(player, numStonesLeft, upperBound);
            numStonesLeft -= pick;
            order *= -1;
        }
        updatePlayerRecords(order, player1, player2);
    }

    // Calculate the number of stones that every player remove
    private int stoneRemove(NimPlayer player , int sum , int upper){
        System.out.println();
        System.out.println(player.getGivenName() + "'s turn - remove how many?");
        int pick = scanner.nextInt();
        scanner.nextLine();

        if(pick > upper) {
            System.out.println();
            System.out.println("Invalid move. You must remove between 1 and" + " " + upperBound + " stones.");

            printRemainingStones(sum);

            System.out.println();
            System.out.println(player.getGivenName() + "'s turn - remove how many?");
            pick = scanner.nextInt();
            scanner.nextLine();
        }
        else if(pick > sum || pick == 0) {
            System.out.println();
            System.out.println("Invalid move. You must remove between 1 and " + sum + " stones.");
            printRemainingStones(sum);
            System.out.println();
            System.out.println(player.getGivenName() + "'s turn - remove how many?");
            pick = scanner.nextInt();
            scanner.nextLine();

        }
        System.out.println();
        return pick;
    }

    // Show the result of this game
    private void updatePlayerRecords(int order, NimPlayer player1, NimPlayer player2){
        System.out.println("Game Over");
        String winnerName;

        if (order == 1){
            winnerName = player1.getGivenName() + " " + player1.getFamilyName();
            player1.increaseNumOfGamesWonByOne();
        } else {
            winnerName = player2.getGivenName() + " " + player2.getFamilyName();
            player2.increaseNumOfGamesWonByOne();
        }

        player1.increaseNumOfGamesPlayedByOne();
        player2.increaseNumOfGamesPlayedByOne();
        player1.updateWinningPercentage();
        player2.updateWinningPercentage();

        System.out.println(winnerName + " wins!");
    }

    // Print the total number of stones
    private void printRemainingStones(int leftStones) {
        System.out.print(leftStones + " stones left:");

        for(int i = 0; i < leftStones; i++)
            System.out.print(" *");
    }

    // Record the order of player
    private NimPlayer playerTurn(int order,NimPlayer player1, NimPlayer player2){
        return order==1? player1:player2;
    }

}
