public class NimPlayer implements Comparable {
    /**
     *
     * @param args
     */

    private String username;
    private String familyName;
    private String givenName;
    private Integer numOfGamesPlayed = 0;
    private Integer numOfGamesWon = 0;
    private float winningPercentage = 0;

    public void setUserName(String username){
        this.username = username;
    }

    public String getUserName(){
        return this.username;
    }

    public void setGivenName(String givenName){
        this.givenName = givenName;
    }

    public String getGivenName(){
        return this.givenName;
    }

    public void setFamilyName(String familyName){
        this.familyName = familyName;
    }

    public String getFamilyName(){
        return this.familyName;
    }

    public void setNumOfGamesPlayed(Integer num){
        this.numOfGamesPlayed = num;
    }

    public Integer getNumOfGamesPlayed(){
        return this.numOfGamesPlayed;
    }

    public void increaseNumOfGamesPlayedByOne(){
        this.numOfGamesPlayed += 1;
    }

    public void setNumOfGamesWon(Integer num){
        this.numOfGamesWon = num;
    }

    public Integer getNumOfGamesWon(){
        return this.numOfGamesWon;
    }

    public void increaseNumOfGamesWonByOne(){
        this.numOfGamesWon += 1;
    }

    public void setWinningPercentage(){
        this.winningPercentage = this.numOfGamesWon/this.numOfGamesPlayed;
    }

    public double getWinningPercentage(){
        return this.winningPercentage;
    }

    public void resetStatsOfPlayer(){
        setNumOfGamesPlayed(0);
        setNumOfGamesWon(0);
    }

    @Override
    public int compareTo(Object o) {
        NimPlayer player = (NimPlayer) o;
        int compareResult = winningPercentage > player.winningPercentage?
                1:(winningPercentage == player.winningPercentage? 0:-1);
        if (compareResult == 0){
            compareResult = username.compareTo(player.username);
        }
        return compareResult;
    }
}
