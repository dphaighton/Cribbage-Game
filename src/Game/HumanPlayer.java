/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author David
 */
public class HumanPlayer extends Player
{

    public HumanPlayer(HumanInterface io) 
    {
        super("Player",io);
    }

    @Override
    public ArrayList<Card> throwOut() 
    {
        ArrayList<Card> throwingOut = new ArrayList(2);
        for(int i=0;i<2;i++)
        {
            throwingOut.add(io.askForCard(getHand().getCards()));
            getHand().removeCard(throwingOut.get(i));
        }
        
        return throwingOut;   
    }

    @Override
    public Card peg(ArrayList<Card> playedCards, int count) 
    {
        ArrayList<Card> playableCards = new ArrayList(4);
        boolean pegable=false;
        for(Card card: getHand().getCards())
        {
            if(card.getNumberValue()<=31-count)
            {
                playableCards.add(card);
                pegable=true;
            }
        }
        
        if(!pegable)
        {
            io.announce("Go!");
            return null;
        }
        
        
        Card selected = io.askForCard(playableCards);
        getHand().removeCard(selected);
        return selected;
    }
    
}
