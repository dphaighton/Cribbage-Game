/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public abstract class Player 
{
    
    
    private final String name;
    private int score;
    private CribHand hand = new CribHand();
    protected HumanInterface io;
    
    public Player(String name, HumanInterface io)
    {
        this.name = name;
        this.io = io;
    }
    
    public void deal(ArrayList<Card> cards)
    {
        
        
        hand.setCards(cards);
        hand.sort();
    }
    
    public void reset()
    {
        hand.clear();
        
    }
    
    public int getScore()
    {
        return score;
    }
    
    
    public void addToScore(int amount)
    {
        score+=2;
    }
    
    public List<Card> getPegged()
    {
        return hand.getPegged();
    }
    
    
    public CribHand getHand()
    {
        return hand;
    }
    
    public abstract ArrayList<Card> throwOut();
    
    public abstract Card peg(ArrayList<Card> playedCards,int count);
    
    public void count()
    {
        score+=hand.getScore();
    }
    
    
    public String toString()
    {
        return name;
    }
    
}
