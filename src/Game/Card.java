/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.Collections;
import java.util.List;

/**
 * 
 * The card class is immutable and will contain all things to do with Cards
 * 
 *
 * @author David
 */

public class Card implements Comparable<Card>
{
    public static final String DIAMONDS="Diamonds", HEARTS="Hearts", SPADES="Spades", CLUBS="Clubs";
    
    public static final String[] SUITS = {DIAMONDS,HEARTS,SPADES,CLUBS};
    
    private final int number, id;
    private final String suit;
    
    public static Card NOCARD=null;
    
    /**The constructor will initialize a card with the given suit and number
     * 
     * @throws IllegalArgumentException if the number is out of the range of 1-13 inclusive or if the suit specified is invalid
     * @param suit the suit of the card
     * @param number the number of the card
     */
    public Card(String suit, int number) throws IllegalArgumentException
    {
        if(number>13||number<1)
        {
            throw new IllegalArgumentException("Card Number out of range");
        }
        int suitId=-1;
        for(int i=0;i<SUITS.length;i++)
        {
            if(SUITS[i].equals(suit))
            {
                suitId=i;
                break;
            }
        }
        if(suitId==-1)
        {
            throw new IllegalArgumentException("Incorrect suit");
        }
        
        this.number=number;
        this.suit=suit;
        
        id = suitId+number*4;//calculates the cards unique ID to be used for comaparing and sorting
    }
    
    /**
     * A constructor only used for the clone method
     * @param suit the suit to copy from
     * @param number the number to copy
     * @param id the id to copy
     */
    private Card(String suit,int number, int id)
    {
        this.suit=suit;
        this.number=number;
        this.id=id;
    }
    
    /**
     * This will return the number of the card
     * @return the number of the card
     */
    public int getNumber()
    {
        return number;
    }

    /**This will return the value of the card. Which is the number of the card and all the royal cards are 10
     * 
     * @return the value of the card
     */
    public int getNumberValue()
    {
        return number>10?10:number;
    }
    
    
    /**This will return the suit of the card
     * 
     * @return the suit of the card
     */
    public String getSuit()
    {
        return suit;
    }
    
    /**This will compare the cards. The higher the number, the bigger the card.
     * Clubs>Spades>Hearts>Diamonds
     * A 3 of Clubs is smaller than a 4 of Spades
     * 
     * @param t the other card to be compared
     * @return 1 if this card is higher, 0 if they're equal and -1 if t is larger
     */
    @Override
    public int compareTo(Card t) 
    {
        if(id>t.id)
        {
            return 1;
        }
        if(id<t.id)
        {
            return -1;
        }
        return 0;
    }
    
    
    /**
     * The method will return a string representation of the card with the format
     * NUMBER of SUIT
     * where NUMBER is the number of the card, Ace, Jack, Queen or King
     * where SUIT is the suit of the card
     * 
     * @return a String representation of the card
     */
    @Override
    public String toString()
    {
        String numString;
        
        switch(number)
        {
            case 1:
                numString="Ace";
                break;
            case 11:
                numString="Jack";
                break;
            case 12:
                numString="Queen";
                break;
            case 13:
                numString="King";
                break;
            default:
                numString=String.valueOf(number);

        }
        
        return numString+" of "+suit;
    }
    
    /**
     * This will sort the cards by using the compare function
     * @param cards a sorted list of cards
     */
    public static void sort(List<Card> cards)
    {
       Collections.sort(cards);
    }
    
    /**
     * This will clone the card object
     *
     * @return a clone of the card object
     */
    @Override
    public Card clone() throws CloneNotSupportedException
    {
        return new Card(suit,number,id);
    }
    
    /**This will compare the cards ids to see if they are the same
     * 
     * @param o the other card
     * @return true if the cards are the same. false otherwise
     */
    public boolean equals(Object o)
    {
        if(o==null){return false;}
        if(o==this){return true;}
        if(o instanceof Card)
        {
            Card c=(Card)o;
            if(c.id==id)
            {
                return true;
            }
        }
        return false;
    }
    
    
}
