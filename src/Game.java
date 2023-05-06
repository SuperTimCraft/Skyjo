import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private int nbPlayers;
    ArrayList<Player> players = new ArrayList<>();
    private Round actualRound;
    Game(){
        this.nbPlayers = 2;
        this.actualRound = null;
    }
    Game(int nbPlayers){
        this.nbPlayers = nbPlayers;
        this.actualRound = null;
    }

    /**
     * Setter for the number of players that will play
     *
     * @param nb the number of players that will play
     */
    public void setNbPlayers(int nb){
        this.nbPlayers = nb;
    }

    /**
     * Getter that return the number of players
     *
     * @return the number of players
     */
    public int getNbPlayers(){
        return this.nbPlayers;
    }

    /**
     * Setter in order to know in which round we are
     *
     * @param actualRound the round that we play
     */
    public void setActualRound(Round actualRound) {
        this.actualRound = actualRound;
    }

    /**
     * Getter that return the round that we are playing
     *
     * @return the round that we play
     */
    public Round getActualRound(){
        return this.actualRound;
    }

    /**
     * Setter that will set the ArrayList players, which contains every player in a game.
     *
     * @param players the ArrayList of players
     */
    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    /**
     * Getter that will return the ArrayList containing all the players in a game.
     *
     * @return the ArrayList containing all the players in a game.
     */
    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    /**
     * This method will initialize the game.
     * When we want to start a new game, this method will be called and initialize all the things that we need.
     * So we will create a new draw pile, new players, new decks, etc.
     * At the end, we need to return a new round, so we can play a game.
     *
     * @return new round, so we can do the first round of the game
     */
    public void initializeGame() throws IOException {
        /*
        Hasn't been correctly tested yet.
         */
        howMuchPlayers();
        for(int i = 0 ; i < nbPlayers ; i++){
            Player player = new Player();
            player.askName();
            this.players.add(player);
        }
        this.actualRound = new Round(this);
    }

    /**
     * This method will simply ask how many people wants to play the game.
     * It should be called during the initialization of the game.
     *
     * @return int, the number of players
     */
    public int howMuchPlayers(){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("How many people will play?"); // Asks how many people will play
        String nbPlayers = myObj.nextLine();  // Read user input
        this.setNbPlayers(Integer.valueOf(nbPlayers)); // Set the correct amount of players
        return Integer.valueOf(nbPlayers);
    }

    /**
     * This method allow to easily pick a specific card of a deck
     * We take the round in progress, then the deck of the player in parameter
     * and finally the number of the card we want to pick.
     * @param player int, number of the player (place in the ArrayList of players,
     *               in the same order as the ArrayList of Decks)
     * @param nb int, position of the card in the deck
     * @return Card, the card we wanted to pick
     */
    public Card getCardFromDeck(int player, int nb){
        return this.getActualRound().getDecks().get(player).getCardPile().get(nb);
    }

    /**
     * This method check if a round is finished, that means if all cards of a deck are returned
     * For all players, we count the number of cards return, if this number is equal 12, then finish is true
     * @return boolean, false if the game must continue, true if the game must end
     */
    public boolean checkEndOfRound(){
        boolean finish=false;
        for(int j=0; j<this.getNbPlayers();j++){
            int nbReturn=0;
            for(int i=0; i<12; i++){
                if(getCardFromDeck(j,i).getIsReturned()){
                    nbReturn++;
                }
            }
            if(nbReturn==12){
                finish=true;
            }
        }
        return finish;
    }

    /**
     * This method should be called at the end of a round.
     * The goal is to see if a player has more than 100 points. If so, the game should end.
     * If there is no player with more than 100 points, we'll have to create a new round in this game.
     *
     * Variable isFinished : This boolean have to be defined to true if there is a winner.
     * Variable winner : Takes the username of the winner.
     * Variable draw : Takes the username of both players that are in a draw i guess.
     * Variable lowestScore : Save the score of the player that has the lowest score.
     *
     * @return true if the game is finished, or false if the game is not finished.
     */
    public boolean stateOfTheGame(){
        /*
        Il faut prendre en compte la situation où il y a égalité qui ne fonctionne pas.
         */
        boolean isFinished = false;
        String winner = new String();
        String draw = new String();
        int lowestScore = 1000;

        for(Player player : players){
            if(player.getScoreGame() >= 100){
                isFinished = true;
            }
            if(player.getScoreGame() <= lowestScore){
                winner = player.getPlayer();
                lowestScore = player.getScoreGame();
            }
            if(player.getScoreGame() == lowestScore){
                draw = player.getPlayer();
            }
        }

        if(isFinished == true){
            System.out.println("Game ended! The winner is " + winner + "! Congratz!");
        }
        else{
            //new Round();
        }
        return isFinished;
    }
}
