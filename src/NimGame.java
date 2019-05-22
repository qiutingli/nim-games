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

    // Print the beginning information
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
            int pick = player.removeStones(scanner, numStonesLeft, upperBound);
            if (pick != 0){
                numStonesLeft -= pick;
                order *= -1;
            }
        }
        updatePlayerRecords(order, player1, player2);
    }

    // Return the player on whose turn
    private NimPlayer playerTurn(int order, NimPlayer player1, NimPlayer player2){
        return order == 1? player1 : player2;
    }

    // Update the game record and show the result of this game
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
        player1.updateWinningPercentage();
        player2.increaseNumOfGamesPlayedByOne();
        player2.updateWinningPercentage();

        System.out.println(winnerName + " wins!");
    }

    // Print the remaining stones
    private void printRemainingStones(int leftStones) {
        System.out.print(leftStones + " stones left:");
        for(int i = 0; i < leftStones; i++)
            System.out.print(" *");
    }


}
