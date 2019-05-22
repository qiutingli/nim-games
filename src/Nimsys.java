import java.io.*;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;

public class Nimsys {

    private NimPlayer[] players = new NimPlayer[0];
    private Scanner scanner = new Scanner(System.in);
    private File statsFile = new File("players.txt");

    private void statsFileExitenceCheck(){
        if (!statsFile.exists()){
            try {
                statsFile.createNewFile();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private Integer getIndexOfUsername(String username){
        for(int i = 0; i < players.length; i++){
            NimPlayer player = players[i];
            if(player.getUserName().equals(username)){
                return i;
            }
        }
        return -1;
    }

    // Indicator is to distinguish human players (0) and AI players (1).
    private NimPlayer createPlayer(Integer indicator, String username, String givenName, String familyName,
                                   Integer numOfGamesPlayed, Integer numOfGamesWon){
        NimPlayer player;
        if (indicator == 0){
            player = new NimHumanPlayer();
        } else {
            player = new NimAIPlayer();
        }
        player.setUserName(username);
        player.setGivenName(givenName);
        player.setFamilyName(familyName);
        player.setNumOfGamesPlayed(numOfGamesPlayed);
        player.setNumOfGamesWon(numOfGamesWon);
        player.updateWinningPercentage();

        return player;
    }

    private void updatePlayersArray(NimPlayer player, String addOrRemove, Integer index){

        NimPlayer[] newPlayers;

        if (addOrRemove.equals("add")){
            newPlayers = new NimPlayer[players.length + 1];
            System.arraycopy(players, 0, newPlayers, 0, players.length);
            newPlayers[players.length] = player;
            players = newPlayers;

        } else if (addOrRemove.equals("remove")){
            newPlayers = new NimPlayer[players.length - 1];
            for(int i = index; i < players.length - 1; i++){
                players[i] = players[i+1];
            }
            System.arraycopy(players, 0, newPlayers, 0, players.length - 1);
            players = newPlayers;
        }
    }

    private void addPlayer(String username, String familyName, String givenName){
        int index = getIndexOfUsername(username);
        if(index == -1){
            NimPlayer player = createPlayer(0, username, givenName, familyName, 0, 0);
            updatePlayersArray(player, "add", null);
        }else{
            System.out.println("The player already exists.");
        }
    }

    private void addAIPlayer(String username, String familyName, String givenName){
        int index = getIndexOfUsername(username);
        if(index == -1){
            NimPlayer player = createPlayer(1, username, givenName, familyName, 0, 0);
            updatePlayersArray(player, "add", null);
        }else{
            System.out.println("The player already exists.");
        }
    }

    private void removePlayer(String username){
        int index = getIndexOfUsername(username);
        if(index == -1){
            System.out.println("The player does not exist.");
        }else {
            updatePlayersArray(null, "remove", index);
        }
    }

    private void editPlayer(String username, String newFamilyName, String newGivenName){
        int index = getIndexOfUsername(username);
        if(index == -1){
            System.out.println("The player does not exist.");
        }else{
            NimPlayer player = players[index];
            player.setGivenName(newGivenName);
            player.setFamilyName(newFamilyName);
            players[index] = player;
        }

    }

    private void resetStats(String username){
        int index = getIndexOfUsername(username);
        if(index == -1){
            System.out.println("The player does not exist.");
        }else{
            NimPlayer player = players[index];
            player.resetStatsOfPlayer();
        }
    }

    private void displayPlayer(NimPlayer player){
        System.out.println(player.getUserName() + "," + player.getGivenName() + "," + player.getFamilyName()
                + "," + player.getNumOfGamesPlayed() + " games," + player.getNumOfGamesWon() + " wins");
    }

    private void caseAddHandler(String[] inputs) {
        try{
            String parameters = inputs[1];
            String[] names = parameters.split(",");
            addPlayer(names[0], names[1], names[2]);
        } catch (Exception e){
            System.out.println("Incorrect number of arguments supplied to command.");
        }
    }

    private void caseAddAIHandler(String[] inputs){
        try{
            String parameters = inputs[1];
            String[] names = parameters.split(",");
            addAIPlayer(names[0], names[1], names[2]);
        } catch (Exception e){
            System.out.println("Incorrect number of arguments supplied to command.");
        }
    }

    private void caseRemoveHandler(String[] inputs, int inputsLength){
        if (inputsLength == 1){
            System.out.println("Are you sure you want to remove all players? (y/n)");
            String confirm = scanner.nextLine().split(" ")[0];
            if (confirm.equals("y")){
                for(NimPlayer player: players){
                    removePlayer(player.getUserName());
                }
            }

        } else if (inputsLength == 2) {
            String username = inputs[1];
            removePlayer(username);
        }
    }

    private void caseEditHandler(String[] inputs, int inputsLength){
        try {
            if (inputsLength == 1){
                System.out.println("Editing a player requires two string inputs separated by space");
            } else if (inputsLength == 2) {
                String parameters = inputs[1];
                String[] names = parameters.split(",");
                editPlayer(names[0], names[1], names[2]);
            }
        } catch (Exception e){
            System.out.println("Incorrect number of arguments supplied to command.");
        }
    }

    private void caseResetHandler(String[] inputs, int inputsLength){
        if (inputsLength == 1){
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            String confirm = scanner.nextLine().split(" ")[0];
            if (confirm.equals("y")){
                for(NimPlayer player: players){
                    resetStats(player.getUserName());
                }
            }
        } else if (inputsLength == 2) {
            String username = inputs[1];
            resetStats(username);
        }
    }

    private void caseDisplayHandler(String[] inputs, int inputsLength){
        Arrays.sort(players);
        if (inputsLength == 1){
            for(NimPlayer player: players){
                displayPlayer(player);
            }
        } else if (inputsLength == 2) {
            try{
                int playerIndex = getIndexOfUsername(inputs[1]);
                displayPlayer(players[playerIndex]);
            }catch (Exception e){
                System.out.println("The player does not exist.");
            }
        }
    }

    private void caseRankingsHandler(String[] inputs, int inputsLength){
        if (inputsLength == 1){
            Arrays.sort(players);
        } else {
            String parameters = inputs[1];
            if (parameters.equals("desc")){
                Arrays.sort(players);
            } else if (parameters.equals("asc")){
//                Arrays.sort(players,Comparator.reverseOrder());
                Arrays.sort(players, new Comparator<NimPlayer>() {
                    @Override
                    public int compare(NimPlayer o1, NimPlayer o2) {
                        double wp1 = o1.winningPercentage;
                        double wp2 = o2.winningPercentage;
                        return wp1 > wp2?
                                1 : (wp1 < wp2?
                                -1 : o1.getUserName().compareTo(o2.getUserName()));
                    }
                });
            }
        }

        for (NimPlayer player: players){
            DecimalFormat percFormat = new DecimalFormat("0%");
            DecimalFormat numGameFormat = new DecimalFormat("00");

            String numOfGame = numGameFormat.format(player.getNumOfGamesPlayed());
            String percentage = percFormat.format(player.getWinningPercentage());

            System.out.printf("%-5s| %s games | %s %s\n", percentage, numOfGame, player.getGivenName(), player.getFamilyName());

//            String newPercentage = percentage.length() == 4?
//                    percentage:(percentage.length() == 3? percentage + " ": percentage + "  ");
//            System.out.println(newPercentage + " | " + numOfGame
//                    + " games | " + player.getGivenName() + " " + player.getFamilyName());
        }

        System.out.println();
    }

    private void caseStartGameHandler(String[] inputs, int inputsLength, Scanner scanner){
        if (inputsLength == 1){
            System.out.println("Starting a game requires two string inputs separated by space. " +
                    "Please check your command!");
        } else if (inputsLength == 2) {
            String parameters = inputs[1];
            String[] names = parameters.split(",");

            int ind1 = getIndexOfUsername(names[2]);
            int ind2 = getIndexOfUsername(names[3]);
            if(ind1 != -1 && ind2 != -1){
                NimPlayer player1 = players[getIndexOfUsername(names[2])];
                NimPlayer player2 = players[getIndexOfUsername(names[3])];
                new NimGame(Integer.parseInt(names[0]), Integer.parseInt(names[1]), player1, player2, scanner);
            }else{
                System.out.println("One of the players does not exist.");
            }
        }
    }

    private void inputAnalyzer(String input){

        String[] inputs = input.split(" ");
        int inputsLength = inputs.length;
        String command = inputs[0];

        switch (command){
            case "addplayer":
                caseAddHandler(inputs);
                break;

            case "addaiplayer":
                caseAddAIHandler(inputs);
                break;

            case "removeplayer":
                caseRemoveHandler(inputs, inputsLength);
                break;

            case "editplayer":
                caseEditHandler(inputs, inputsLength);
                break;

            case "resetstats":
                caseResetHandler(inputs, inputsLength);
                break;

            case "displayplayer":
                caseDisplayHandler(inputs, inputsLength);
                break;

            case "rankings":
                caseRankingsHandler(inputs, inputsLength);
                break;

            case "startgame":
                caseStartGameHandler(inputs, inputsLength, scanner);
                break;

            case "exit":
                break;

            default:
                System.out.printf("'%s' is not a valid command.\n", command);
        }
    }

    private void readStats() throws IOException{
        statsFileExitenceCheck();
        FileReader fileReader = new FileReader(statsFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] info = line.split(",");
            NimPlayer player = createPlayer(Integer.valueOf(info[0]), info[1], info[2], info[3],
                    Integer.valueOf(info[4]), Integer.valueOf(info[5]));
            updatePlayersArray(player, "add", null);
        }
        fileReader.close();
    }

    private void writeStats() throws IOException{
        statsFileExitenceCheck();
        FileOutputStream fop = new FileOutputStream(statsFile);
        OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");

        for(NimPlayer player: players){
            Integer indicator = player instanceof NimHumanPlayer? 0: 1;
            String playerInfo = indicator.toString() + "," + player.getUserName() + "," + player.getGivenName() + ","
                    + player.getFamilyName() + "," + player.getNumOfGamesPlayed().toString() + ","
                    + player.getNumOfGamesWon().toString();
            writer.append(playerInfo);
            writer.append("\r\n");
        }

        writer.close();
        fop.close();
    }

    // Show the content of the statistics file. TODO: This is for test. Comment this out.
    private void showContentOfFile() throws IOException{
        statsFileExitenceCheck();
        FileReader fileReader = new FileReader(statsFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append("\n");
        }
        fileReader.close();
        System.out.println("Contents of file:");
        System.out.println(stringBuffer.toString());
    }

//    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Nim");
        Nimsys sys = new Nimsys();
        String input;

        sys.readStats();

        do {
            System.out.print("\n$");
            input = sys.scanner.nextLine();
            sys.inputAnalyzer(input);
        } while (!input.equals("exit"));

        sys.writeStats();

        System.out.println();
        System.exit(0);
    }
}