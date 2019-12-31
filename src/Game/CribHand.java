/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The CribHand is the Hand class with more cribbage specific methods
 *
 * @author David
 */
public class CribHand extends Hand
{
    private int score=0;
    
    private ArrayList<Card> pegged = new ArrayList(4);
    
    /**This will reset the hand to have the cards given and reset the score to 0
     * 
     * @param cards the new cards in the deck
     */
    public void reset(ArrayList<Card> cards)
    {
        pegged.clear();
        setCards(cards);
        score=0;
    }
    
    /**
     * this will discard the cards specified
     * @param cards the cards to remove
     * @throws IllegalArgumentException if the card does not exist or if too many cards are being removed
     */
    public void discard(ArrayList<Card> cards) throws IllegalArgumentException
    {
        if(this.getCards().size()-cards.size()!=4)
        {
            throw new IllegalArgumentException("Not the right amount of cards being removed");
        }
        
        
        for(Card card : cards)
        {
            
            if(!this.getCards().remove(card))
            {
                throw new IllegalArgumentException("The card does not exist");
            }
            
            
        }
        
        
        
    }
    
    public void peg(Card card)
    {
        removeCard(card);
        pegged.add(card);
    }
    
    public List<Card> getPegged()
    {
        return pegged;
    }
    
    
    /**
     * returns the calculated score for the hand.
     * storeScore() has to be called first 
     * @return the calculated score for the hand
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * This will store the score calculated for the given hand
     * @param faceCard 
     * @throws IllegalStateException if the hands size is not 4
     */
    public void storeScore(Card faceCard) throws IllegalStateException
    {
        if(this.getCards().size()!=4)
        {
            throw new IllegalStateException("The hand does not have the right number of cards:\n"+this);
        }
        if(!sorted)
        {
            sort();
        }
        
        score=calculateHand(faceCard);
    }
    
    /**This will calculate the score from the flushes and the jack of trumps in the hand
     * 
     * @param cards the hands cards
     * @param face the face card
     * @return the number of points
     */
    private static int calculateFlush(ArrayList<Card> cards, Card face)
    {
        int score = 0;
        
        //if the a jack in the hand is the same suit as the face card, +1 point for the jack of trumps
        for(Card card: cards)
        {
            if(card.getNumber()==11&&card.getSuit().equals(face.getSuit()))
            {
                score+=1;
                break;
            }
        }

        //A flush is when the 4 cards in the hand are the same suit. If the facecard is that suit too, thats an extra point
        String suit = cards.get(0).getSuit();
        boolean same = true;

        

        
        //go through the hand and if all the cards are the same, +4, if the face is the same +=1
        for(int i=1;i<cards.size();i++)
        {
            if(!suit.equals(cards.get(i).getSuit()))
            {
                same=false;
                break;
            }
        }
        
        if(same)
        {
            score+=4;
            if(suit.equals(face.getSuit()))
            {
                score+=1;
            }
        }
        return score;
    }
    
    /**This will calculate the points from the runs in the hand
     * This should only be called if the cards are sorted
     * @param cards the sorted hand cards and face card to calculate from 
     * @return the number of points the runs are worth
     */
    private static int calculateRuns(ArrayList<Card> cards)
    {
        //Given that the cards are sorted, the algorithm goes through the cards and the current card is +1 in number than the previous,
        // then the length of that run is 1 point bigger.
        //if the card is the same as the previous, then the run multiplied by a larger number (1 bigger than what it was)
        //otherwise, if the run is less than 3 in length, the run is discarded, otherwise thats the cards only run (because 5-3 is 2 and 2 is not enough for a run
        
        int number = cards.get(0).getNumber();
        ArrayList<Integer> run = new ArrayList();
        run.add(1);
        
        for(int i=1;i<cards.size();i++)
        {
            if(cards.get(i).getNumber()==number)
            {
                int add=run.get(run.size()-1)+1;
                run.set(run.size()-1, add);
            }
            else if(cards.get(i).getNumber()==number+1)
            {
                run.add(1);
                number=cards.get(i).getNumber();
            }
            else
            {
                if(run.size()>2)
                {
                    break;
                }
                run.clear();
                run.add(1);
                number = cards.get(i).getNumber();
            }
        }
        //if there was no run of 3 or less, there was no runs
        if(run.size()<3)
        {
            return 0;
        }
        ////if multiply the length run by all the multipliers in the run (1 card in a row is 1, 2 cards in a row is 2...)
        int product = 1;
        for(int num: run)
        {
            product*=num;
        }
        //return it
       return run.size()*product; 
    }
    
    /**
     * This will calculate the score from the 15's in the hand
     * @param cards the hand cards and face card
     * @return the score from the 15's in the hand
     */
    private static int calculate15s(ArrayList<Card> cards)
    {
        //to add all the cards and find all the combinations of 15, a binary counter is used
        //every time the counter increments by 1, the cards are summed up by the following rule
        //each card has a binary digit. If that digit is 1, the card is added to the sum, if it is 0, the card is ignored.
        //if the sum of the cards is 15, then the score +=2
        
        int score=0;
        
        for(int counter=1;counter<(1<<cards.size());counter++)
        {
            int total =0;
            for(int place=0;place<cards.size();place++)
            {
                if(((counter>>place)&1)==1)
                {
                    total+=cards.get(place).getNumberValue();
                    if(total>15)
                    {
                        break;
                    }
                }
            }
            if(total==15)
            {
                score+=2;
            }
        }

        return score;
    }
    
    /**The points from the pairs in the hand and face card
     * This can only be called with sorted cards
     * @param cards the sorted cards to calculate the score with
     * @return the points from the pairs in the hand
     */
    private static int calculatePairs(ArrayList<Card> cards)
    {
        //to get all the points for the pairs, loop through the cards and count how many of the same there is in a row.
        //to find out how many points those pairs in a row is worth, use s=n(n-1) where s=the score, and n is the number of same numbered cards in a row
        //after a set of pairs, continue through the cards in search of another to do the same algorithm on
        int score=0;
        int cardNumber = cards.get(0).getNumber();
        int row = 1;
        
        for(int i=1;i<cards.size();i++)
        {
            if(cardNumber==cards.get(i).getNumber())
            {
                row+=1;
            }
            else
            {
                cardNumber = cards.get(i).getNumber();
                score+=row*(row-1);
                row=1;
            }
            
        }
        score+=row*(row-1);
        
        return score;
    }
    
    /**
     * This will calculate the points in the hand for the given face card
     * @param faceCard the face card
     * @return the points in the hand
     */ 
    private int calculateHand(Card faceCard)
    {
        int score=0;
        
        score+=CribHand.calculateFlush(this.getCards(), faceCard);
        ArrayList<Card> cards = (ArrayList<Card>)this.getCards().clone();
        
        if(faceCard!=null)
        {
            cards.add(faceCard);
            Collections.sort(cards);
        }
        
        score+=CribHand.calculatePairs(cards);
        score+=CribHand.calculate15s(cards);
        score+=CribHand.calculateRuns(cards);
        return score;
    }
    
}
