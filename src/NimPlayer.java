public class NimHumanPlayer extends NimPlayer implements Comparable {

    @Override
    public int compareTo(Object o) {
        NimHumanPlayer player = (NimHumanPlayer) o;
        int compareResult = winningPercentage < player.winningPercentage?
                1:(winningPercentage == player.winningPercentage? 0:-1);
        return compareResult;
    }

}
