import java.util.Scanner;

public class NimHumanPlayer extends NimPlayer{

    public NimHumanPlayer(){

    }

    // Judge the validity of the number of stones that a player wants to move
    @Override
    public int removeStones(Scanner scanner, int numStonesLeft , int upperBound){
        System.out.println();
        System.out.println(getGivenName() + "'s turn - remove how many?");
        int numPick = 0;

        int numWishPick = scanner.nextInt();
        scanner.nextLine();

        if (numWishPick > upperBound) {
            System.out.print("\nInvalid move. You must remove between 1 and" + " " + upperBound + " stones.\n");
        } else if (numWishPick > numStonesLeft || numWishPick == 0) {
            System.out.print("\nInvalid move. You must remove between 1 and " + numStonesLeft + " stones.\n");
        } else {
            numPick = numWishPick;
        }

        System.out.println();
        return numPick;
    }

}
