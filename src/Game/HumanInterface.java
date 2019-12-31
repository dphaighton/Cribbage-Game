/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.List;

/**
 *This is the interface used to get input and output to the screen.
 * 
 * @author David
 */
public interface HumanInterface 
{
    public static final int HIDEPOINTS=-1;
    
    /**This will display the starting menu of the game
     * 
     */
    public void startingMenu();
    /**
     * This will ask the player for a card out of a selection and return the card that is selected
     * @param options the options that the player can choose from
     * @return the selected card
     */
    public Card askForCard(List<Card> options);
    
    
    /**
     * This will display the picked card and who picked it
     * @param name the player who picked the card
     * @param card the selected card
     */
    public void displayPickedCard(String name, Card card);
    
    /**
     * This will display a players name, their score and their points
     * @param name the name of the player
     * @param cards the players cards
     * @param points the points the player has. Use HIDEPOINTS to not display the points
     */
    public void displayHand(String name, List<Card> cards,int points);
    
    /**This will display the face card of the game
     * 
     * @param card the face card
     */
    public void displayFaceCard(Card card);
    
    /**
     * This will announce something to the player of the game
     * @param gameState the announcement 
     */
    public void announce(String gameState);
    
    /** This will display a the players in the game and their scores
     * 
     * @param players the players in the game
     */
    public void displayScore(List<Player> players);
    
    /**This will display an error message
     * 
     * @param errorMessage the error message to display
     */
    public void displayErrorMessage(String errorMessage);
    
    /**This will display the ending screen with a given message
     * 
     * @param message the message to be displayed
     */
    public void endingScreen(String message);
    
    /**This will close any input and output used in the game
     * 
     */
    public void close();
}
