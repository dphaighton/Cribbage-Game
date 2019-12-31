/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;

/**
 *
 * @author David
 */
public class ComputerPlayer extends Player
{

    public ComputerPlayer( HumanInterface io) {
        super("Computer", io);
    }

    @Override
    public ArrayList<Card> throwOut() 
    {
        ArrayList<Card> throwingOut = new ArrayList(2);
        for(int i =0;i<2;i++)
        {
            int top = getHand().getCards().size()-1;
            Card removed = getHand().getCards().get(top);
            throwingOut.add(removed);
            
            getHand().removeCard(removed);
            
        }
        return throwingOut;
        
    }

    @Override
    public Card peg(ArrayList<Card> playedCards, int count) 
    {
        for (Card card : getHand().getCards()) 
        {
            if(card.getNumberValue()<31-count)
            {
                Card selected = card;
                getHand().removeCard(card);
                io.displayPickedCard(toString(), selected);
                return selected;
            }
        }
        io.announce("Go!");
        return null;
    }
    
}
