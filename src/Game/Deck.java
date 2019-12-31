/*
 * This is a deck. The deck should either start empty, or full and sorted
 */
package Game;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author David
 */
public class Deck 
{
    private ArrayList<Card> cards;
    private Random random = new Random();
    
    
    
    /**This will put the sorted cards into the deck
     * 
     * @param cards the cards
     */
    private Deck(ArrayList<Card> cards)
    {
        this.cards=cards;
    }
    
    
    /**
     * This will reset the deck to it's original state
     */
    public void reset()
    {
        this.cards = generateSortedCards();

    }
    
    /**
     * This will shuffle the cards
     */
    public void shuffle()
    {
        ArrayList<Card> oldCards=this.cards;
        cards = new ArrayList(52);
        for(int i=0;i<oldCards.size();)
        {
            cards.add(oldCards.remove(random.nextInt(oldCards.size())));
        }
        
    }
    
    /** 
     * This will pop the first card in the deck
     * @return the first card in the deck
     */
    public Card pop()
    {
        return cards.remove(cards.size()-1);
    }
    
    /**
     * This will return the cards in stack form
     * @return the stack of cards
     */
    public ArrayList<Card> getCards()
    {
        return cards;
    }
    
    /**This factory method is used to set the deck up
     * 
     * @throws IllegalArgumentException if the type is not correct
     * @return the Deck
     */
    public static Deck generateDeck()
    {

            return new Deck(generateSortedCards());
    }
    
    /**This returns a stack of sorted cards
     * 
     * @return the sorted deck
     */
    private static ArrayList<Card> generateSortedCards()
    {
        ArrayList<Card> cards = new ArrayList();
        for(int number=1;number<=13;number++)
        {
            for(String suit: Card.SUITS)
            {
                cards.add(new Card(suit,number));
            }
        }
        return cards;        
    }
    
    /**This will return the cards in the deck with a \n between each one
     * 
     * @return the deck in string form
     */
    @Override
    public String toString()
    {
        String out="";
        
        for(Card card: cards)
        {
            out+=card+"\n";
        }
        
        return out;
    }
    
}
