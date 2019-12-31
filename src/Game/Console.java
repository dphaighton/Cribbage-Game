/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Console implements HumanInterface
{

    private final Scanner input = new Scanner(System.in);
    
    @Override
    public void startingMenu() 
    {
        System.out.println("Welcome to Cribbage!");
    }

    @Override
    public Card askForCard(List<Card> options) 
    {
        System.out.println("Select a card");
        for(Card option : options)
        {
            String num;
            if(option.getNumber()==1){num="A";}
            else if(option.getNumber()==11){num="J";}
            else if(option.getNumber()==12){num="Q";}
            else if(option.getNumber()==13){num="K";}
            else{num=""+option.getNumber();}
            
            System.out.println("\t-"+option+" ("+num+" "+option.getSuit().charAt(0)+")");
        }
        String cardName = input.nextLine();
        
        Card toReturn=parseCard(cardName);
        
        while(!options.contains(toReturn))
        {
            System.out.println("That card does not exist. Select another");
            cardName = input.nextLine();
            toReturn = parseCard(cardName);
        }
        
        return toReturn;
    }
    

    @Override
    public void displayPickedCard(String name, Card card) 
    {
        System.out.println(name+" picked "+card);
    }

    @Override
    public void displayHand(String name, List<Card> cards, int points) 
    {
        System.out.println(name+" has");
        for(Card card : cards)
        {
            System.out.println("\t"+card);
        }
        System.out.println("in their hand for "+points+" points");
        
    }

    @Override
    public void displayFaceCard(Card card) 
    {
        System.out.println("The face card is revealed to be a "+card);
        
    }

    @Override
    public void announce(String gameState) 
    {
        System.out.println("---ANNOUNCEMENT---\n"+gameState);
    }

    @Override
    public void displayScore(List<Player> players) 
    {
        System.out.println("SCORES----------");
        for(Player player : players)
        {
            System.out.println(player.toString()+"\t"+player.getScore());
        }
    }

    @Override
    public void displayErrorMessage(String errorMessage) 
    {
        System.err.println(errorMessage);
    }

    @Override
    public void endingScreen(String winner) 
    {
        System.out.println(winner+" won the game!");
    }

    @Override
    public void close() 
    {
        input.close();
    }
    
    private Card parseCard(String cardName)
    {
                
        String[] cardInfo =cardName.split(" ");
        String suitString = cardInfo[1];
        String numberString = cardInfo[0];
        
        String suit; int number;
        if(suitString.equals("D"))
        {
            suit = Card.DIAMONDS;
        }
        else if(suitString.equals("C"))
        {
            suit = Card.CLUBS;
        }
        else if(suitString.equals("S"))
        {
            suit = Card.SPADES;
        }
        else if(suitString.equals("H"))
        {
            suit = Card.HEARTS;
        }
        else
        {
            displayErrorMessage(suitString+" isn't a suit");
            return null;
        }
        
        switch (numberString) {
            case "A":
                number = 1;
                break;
            case "J":
                number=11;
                break;
            case "Q":
                number=12;
                break;
            case "K":
                number=13;
                break;
            default:
                try
                {
                    number=Integer.parseInt(numberString);
                }
                catch(Exception e)
                {
                    displayErrorMessage(numberString+" isn't a card number");
                    return null;
                }   break;
        }
        
        return new Card(suit,number);
        
    }
    
    
    private void pause(long amount)
    {
        try {
            Thread.sleep((long)(amount*0.5));
        } catch (InterruptedException ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
