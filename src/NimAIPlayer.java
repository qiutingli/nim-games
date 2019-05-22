import java.util.Random;
import java.util.Scanner;

public class NimAIPlayer extends NimPlayer implements Testable{

    public NimAIPlayer() {

    }

    @Override
    public int removeStones(Scanner scanner, int numStonesLeft, int upperBound) {
        System.out.println();
        System.out.println(getGivenName() + "'s turn - remove how many?");
        int numPick;
        int mod = numStonesLeft % (upperBound + 1);
        if (mod != 1){
            int k = (numStonesLeft - mod)/(upperBound + 1);
            if (mod == 0) {
                numPick = numStonesLeft - ((k - 1) * (upperBound + 1) + 1);
            } else {
                numPick = numStonesLeft - (k * (upperBound + 1) + 1);
            }
        } else {
            Random rand = new Random();
            numPick = rand.nextInt(upperBound < numStonesLeft? upperBound+1 : numStonesLeft+1);
        }
        System.out.println();
        return numPick;
    }

    public String advancedMove(boolean[] available, String lastMove){
        String move = "";
        return move;
    };

}
