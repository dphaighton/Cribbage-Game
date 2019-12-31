/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;

/**
 * The hand will store cards 
 *
 * @author David
 */
public class Hand 
{
    private ArrayList<Card> cards = new ArrayList();
    protected boolean sorted=false;
    /**
     * This will add a card to the hand
     * @param card the card being added to the hand
     */
    public void addCard(Card card)
    {
        this.cards.add(card);
        sorted=false;
    }
    /**
     * This will remove a card from the hand
     * @param card the card to be removed
     */
    public void removeCard(Card card)
    {
        this.cards.remove(card);
    }
    /**
     * This will set the cards in the hand to be the ones specified by the parameter
     * @param cards the cards the hand will have
     */
    public void setCards(ArrayList<Card> cards)
    {
        this.cards = cards;
        sorted=false;
    }
    /**This will return the cards stored in the hand
     * 
     * @return the cards in the hand
     */
    public ArrayList<Card> getCards()
    {
        return cards;
    }
    
    /**This will state whether the hand has the card given by the parameter
     * 
     * @param card the card being looked for
     * @return true if the card exists. false otherwise
     */
    public boolean hasCard(Card card)
    {
        return cards.contains(card);
    }
    
    
    /**
     * This will clear the hand of cards
     */
    public void clear()
    {
        cards.clear();
        
    }
    
    /**
     * This will sort the cards in the hand
     */
    public void sort()
    {
        Card.sort(cards);
        sorted=true;
    }
    
    /**
     * This will return all the cards out with a \n between them
     * @return the string in the format specified
     */
    @Override
    public String toString()
    {
        String out="";
        out = cards.stream().map((card) -> card+"\n").reduce(out, String::concat);
        
        return out;
    }
    
}
