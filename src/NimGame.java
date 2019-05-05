import java.util.Scanner;

public class NimGame {

    private int numOfInitialStones;
    private int maxRemoval;
    private NimPlayer player1;
    private NimPlayer player2;
    private Scanner scanner = new Scanner(System.in);

    public NimGame(int numOfInitialStones, int maxRemoval, NimPlayer player1, NimPlayer player2){
        this.numOfInitialStones = numOfInitialStones;
        this.maxRemoval = maxRemoval;
        this.player1 = player1;
        this.player2 = player2;
    }

    private int stoneRemove(NimPlayer player , int sum , int upper){
        System.out.println();
        System.out.println(player.getUserName() + "'s turn - remove how many?");
        int pick = scanner.nextInt();

        while(pick > upper || pick > sum || pick < 1) {
            System.out.println("error! Please enter the correct number of the stone you want to pick");
            pick = scanner.nextInt();
        }
        System.out.println();
        return pick;
    }

    private void printAndRecordWinner(int order, NimPlayer player1, NimPlayer player2){
        System.out.println("Game Over");
        player1.increaseNumOfGamesPlayedByOne();
        player2.increaseNumOfGamesPlayedByOne();

        String name;
        if (order == 1) {
            player1.increaseNumOfGamesWonByOne();
            name = player1.getUserName();
        } else {
            player2.increaseNumOfGamesWonByOne();
            name = player2.getUserName();
        }

        player1.setWinningPercentage();
        player2.setWinningPercentage();

        System.out.println(name + " wins!");
        System.out.println();
    }

    private void printNumStoneLeft(int leftStones) {
        System.out.print(leftStones + " stones left:");
        for(int i = 0; i < leftStones; i++)
            System.out.print(" *");
    }

    private NimPlayer playerTurn(int order,NimPlayer player1, NimPlayer player2){
        return order==1? player1:player2;
    }

    public void runGame(){
        String playAgain = "";
        do{
            int order = 1;
            int leftStones = numOfInitialStones;
            NimPlayer onTurnPlayer;

            while(leftStones > 0){
                printNumStoneLeft(leftStones);
                onTurnPlayer = playerTurn(order, player1, player2);
                int pick = stoneRemove(onTurnPlayer, leftStones, maxRemoval);
                leftStones -= pick;
                order = order * (-1);
            }

            printAndRecordWinner(order, player1, player2);

            System.out.println("Do you want to play again (Y/N):");
            playAgain = scanner.next();

        } while(playAgain.equalsIgnoreCase("Y"));
    }
}
