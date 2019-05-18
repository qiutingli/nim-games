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
    public double winningPercentage = 0;

    public void setUserName(String username){
        this.username = username;
    }

    public String getUserName(){
        return this.username;
    }

    public void setGivenName(String givenName){
        this.givenName = givenName;
    }

    public String getGivenName(){ return this.givenName; }

    public void setFamilyName(String familyName){
        this.familyName = familyName;
    }

    public String getFamilyName(){ return this.familyName; }

    public Integer getNumOfGamesPlayed(){ return this.numOfGamesPlayed; }

    public void increaseNumOfGamesPlayedByOne(){
        this.numOfGamesPlayed += 1;
    }

    public Integer getNumOfGamesWon(){
        return this.numOfGamesWon;
    }

    public void increaseNumOfGamesWonByOne(){
        this.numOfGamesWon += 1;
    }

    public void updateWinningPercentage(){
        double percentage = 0;
        double numGameWon = this.numOfGamesWon;
        double numGamePlayed = this.numOfGamesPlayed;
        if (this.numOfGamesPlayed != 0){
            percentage = numGameWon/numGamePlayed;
        }
        this.winningPercentage = percentage;
    }

    public double getWinningPercentage(){
        return this.winningPercentage;
    }

    public void resetStatsOfPlayer(){
        this.numOfGamesPlayed = 0;
        this.numOfGamesWon = 0;
        this.winningPercentage = 0;
    }

    @Override
    public int compareTo(Object o) {
        NimPlayer player = (NimPlayer) o;
        int compareResult = winningPercentage < player.winningPercentage?
                1:(winningPercentage == player.winningPercentage? 0:-1);
        if (compareResult == 0){
            compareResult = username.compareTo(player.username);
        }
        return compareResult;
    }
}
