import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class Nimsys {

    private NimPlayer[] players = new NimPlayer[0];
    private Scanner scanner = new Scanner(System.in);

    private Integer getIndexOfUsername(String username){
        for(int i = 0; i < players.length; i++){
            NimPlayer player = players[i];
            if(player.getUserName().equals(username)){
                return i;
            }
        }
        return -1;
    }

    private NimPlayer createPlayer(String username, String givenName, String familyName){
        NimPlayer player = new NimPlayer();
        player.setUserName(username);
        player.setGivenName(givenName);
        player.setFamilyName(familyName);
        return player;
    }

    private void addPlayer(String username, String familyName, String givenName){
        int index = getIndexOfUsername(username);
        if(index == -1){
            players = addPlayerHelper(username, givenName, familyName);
        }else{
            System.out.println("The player already exists.");
        }
    }

    private NimPlayer[] addPlayerHelper(String username, String givenName, String familyName){
        NimPlayer player = createPlayer(username, givenName, familyName);
        NimPlayer[] newPlayers = new NimPlayer[players.length + 1];
        System.arraycopy(players, 0, newPlayers, 0, players.length);
        newPlayers[players.length] = player;
        return newPlayers;
    }

    private void removePlayer(String username){
        int index = getIndexOfUsername(username);
        if(index == -1){
            System.out.println("The player does not exist.");
        }else {
            players = removePlayerHelper(players, index);
        }
    }

    private NimPlayer[] removePlayerHelper(NimPlayer[] arr, int index){
        NimPlayer[] newArr = new NimPlayer[arr.length - 1];
        for(int i = index; i < arr.length - 1; i++){
            arr[i] = arr[i+1];
        }
        System.arraycopy(arr, 0, newArr, 0, arr.length - 1);
        return newArr;
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

    private void caseAddHandler(String[] inputs, int inputsLength) {
        if (inputsLength == 1) {
            System.out.println("Adding a player requires two string inputs separated by space");
        } else if (inputsLength == 2) {
            String parameters = inputs[1];
            String[] names = parameters.split(",");
            addPlayer(names[0], names[1], names[2]);
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
        if (inputsLength == 1){
            System.out.println("Editing a player requires two string inputs separated by space");
        } else if (inputsLength == 2) {
            String parameters = inputs[1];
            String[] names = parameters.split(",");
            editPlayer(names[0], names[1], names[2]);
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
        } else if (inputsLength == 2){
            String parameters = inputs[1];
            if (parameters.equals("desc")){
                Arrays.sort(players);
            } else if (parameters.equals("asc")){
                Arrays.sort(players, Collections.reverseOrder());
            }
        }
        for (NimPlayer player: players){
            DecimalFormat percFormat = new DecimalFormat("0%");
            DecimalFormat numGameFormat = new DecimalFormat("00");

            String numOfGame = numGameFormat.format(player.getNumOfGamesPlayed());
            String percentage = percFormat.format(player.getWinningPercentage());

//            String newPercentage = percentage.length() == 4?
//                    percentage:(percentage.length() == 3? percentage + " ": percentage + "  ");
//            System.out.println(newPercentage + " | " + numOfGame
//                    + " games | " + player.getGivenName() + " " + player.getFamilyName());

            System.out.printf("\n%-5s| %s games | %s %s", percentage, numOfGame, player.getGivenName(), player.getFamilyName());
        }
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
                NimGame game = new NimGame(Integer.parseInt(names[0]), Integer.parseInt(names[1]), player1, player2, scanner);
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
                caseAddHandler(inputs, inputsLength);
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
                System.out.println();
                System.exit(0);
                break;

            default:
                System.out.println("Please check your command!");
        }
    }

    public static void main(String[] args){
        System.out.println("Welcome to Nim");
        Nimsys sys = new Nimsys();

        while (true){
            System.out.println();
            System.out.print("$");
            String input = sys.scanner.nextLine();
            sys.inputAnalyzer(input);
        }
    }
}