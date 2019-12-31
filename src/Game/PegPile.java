/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;

/**
 *
 * This will deal with the scoring and previous cards played cards during pegging.
 * 
 * @author David
 */
public class PegPile 
{
    private int count;
    
    ArrayList<Card> cards;
    
    /**
    * Used to make the peg pile
    */
    public PegPile()
    {
        cards = new ArrayList(14);
    }
    
    
    /**
     * This will add a card to the pile, get the points gained from the played card and look to see if the pile should be reset
     * @param card the card pegged with
     * @return the number of points gained by playing the card
     */
    public int addCard(Card card)
    {
        cards.add(card);
        count+=card.getNumberValue();
        
        int score= checkFor15and31()+checkForFlush()+checkForPairs()+checkForRuns();
        
        if(count==31){reset();}
        
        return score;
    }
    
    /**
     * This will clear the previous cards and reset the count to 0
     */
    public void reset()
    {
        cards.clear();
        count=0;
    }
    
    /**This will return the cards that have been played. The more recently they've been played the higher they are in the List
     * 
     * @return the previous played cards
     */
    public ArrayList<Card> getCards()
    {
        return cards;
    }
    
    /**
     * returns the count of the pegging round
     * @return the count of the pegging round
     */
    public int getCount()
    {
        return count;
    }
    
    /**
     * returns 2 points if the count is a 15 or 31
     * @return 2 points if the count is a 15 or 31
     */
    private int checkFor15and31()
    {
        return 2*(count==15||count==31?1:0);
    }
    
    /**
     * this will return the number of points gained for playing a run with the most recent card
     * @return the number of points from the run played by the most recent card
     */
    private int checkForRuns()
    {
        boolean[] numberPlayed = new boolean[14];//13 cards but position 0 is ignored
        if(cards.isEmpty()){return 0;}
        //these runs are different than the hand runs
        
        int low=13,high=1,sum=0;
        int runSize=0;
        //a set of numbers is a part of the sequence y=x if the sum of the numbers follows s=n(n-1). This can be used to figure out if there is any uniterrupted runs
        
        for(int i=cards.size()-1;i>=0;i--)
        {
            
            int number = cards.get(i).getNumber();
            
            //in a run, there should not be double cards
            if(numberPlayed[number]){break;}
            numberPlayed[number]=true;
            
            if(low>number-1)
            {
                low=number-1;
            }
            if(number>high)
            {
                high=number;
            }
            sum+=number;

            if(high*(high+1)-low*(low+1)==2*sum)
            {
                
                runSize=high-low;
                
            }
        }
        
        if(runSize<3)
        {
            return 0;
        }
        return runSize;
    }
    
    /**
     * this will return the number of points  gained for playing the most recent card from the flush
     * @return the number of points  gained for playing the most recent card from the flushs
     */
    private int checkForFlush()
    {
        if(cards.isEmpty()){return 0;}
        int topIndex = cards.size()-1;
        String suit = cards.get(topIndex).getSuit();
        
        int total=0;
        for(int i=topIndex;i>=0;i--)
        {
            if(!suit.equals(cards.get(i).getSuit()))
            {
              break;
            }
            total++;
        }
        if(total<4)
        {
            return 0;
        }
        
        return total;
    }
    /**
     * This will check for pairs and return the number of points gained from them from the last card played 
     * @return the points gained from pairs in the last card that was played
     */
    private int checkForPairs()
    {
        if(cards.isEmpty()){return 0;}
        int topIndex = cards.size()-1;
        int number =cards.get(topIndex).getNumber();
        int row=0;
        for(int i=topIndex;i==-1&&i>topIndex-6;i--)
        {
            if(cards.get(i).getNumber()==number)
            {
                row+=1;
            }
            else
            {
                break;
            } 
        }
        if(row<3)
        {
            return 0;
        }
        return row*(row-1);
    }
}
