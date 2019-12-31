/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**The game itself. It will run the game.
 *
 * @author David
 */
public class Game 
{
    //the states the game can be in
    public static final int MENU=1, DEALING=2, DISCARDING=3, PEGGING=4, COUNTING=5, NEXTROUND=6,GAMEOVER=7, EXIT=8; 
    private int currentState=1;//the current state of the game
    
    private HumanInterface io;
    
    private Player winner;
    
    private PegPile pile = new PegPile();
    
    LinkedList<Player> players = new LinkedList();
    Deck cardDeck;
    
    CribHand crib = new CribHand();
    Card faceCard;
    
    public Game(HumanInterface io)
    {
        this.io = io;
        players.add(new HumanPlayer(this.io));
        players.getLast().reset();
        players.add(new ComputerPlayer(this.io));
        players.getLast().reset();
        
    }
    
    /**This will play the game until the game is over
     * 
     */
    public void play()
    {
        //keep playing according to the state of the game until the game is over
        while(currentState!=EXIT)
        {
            switch(currentState)
            {
                case MENU:
                    menu(); break;
                case DEALING:
                    deal(); break;
                case DISCARDING:
                    discard(); break;
                case PEGGING: 
                    peg(); break;
                case COUNTING:
                    count(); break;
                case NEXTROUND:
                    nextRound(); break;
                case GAMEOVER:
                    gameover(); break;
                case EXIT:
                    close(); break;
            }
        }
    }
    
    /**
     * The menu state will show the opening menu
     */
    public void menu()
    {
        io.startingMenu();
        cardDeck = Deck.generateDeck();
        currentState = NEXTROUND;
    }
    
    /**
    * The deal state deals the cards to the players
    */
    private void deal()
    {
        io.announce("Shuffling and Dealing...");
        cardDeck.shuffle();
        for(Player player: players)
        {
            ArrayList<Card> dealCards = new ArrayList(6);
            for(int i=0;i<6;i++)
            {
                
                dealCards.add(cardDeck.pop());
            }
            
            player.deal(dealCards);
        }
        
        currentState = DISCARDING;
    }
    
    /**
     * During the discard state, the cards are discarded until each player has 4. The crib is also made
     */
    public void discard()
    {
        
        io.announce("Preparing to discard...");
        faceCard = cardDeck.pop();
        for(Player player: players)
        {
            List<Card> addToCrib= player.throwOut();
            for(Card card: addToCrib)
            {
                crib.addCard(card);
            }
            
            player.getHand().storeScore(faceCard);
            if(player.getHand().hasCard(faceCard))
            {
                player.addToScore(2);
                if(checkForWinner(player)){return;}
            }
        }
        crib.storeScore(faceCard);
        io.displayFaceCard(faceCard);
        currentState = PEGGING;
    }
    
    /**
     * During the pegging state, the players peg until they have no cards left
     */
    public void peg()
    {
       io.announce("PEGGING");
       int leftToPlay=players.size()*4;
       
       LinkedList<Player> table = (LinkedList<Player>)players.clone();
       int count=0;
       
       do
       {
           if(count==table.size())
           {
               pile.reset();
               count=0;
               table.getFirst().addToScore(1);
               if(checkForWinner(players.getFirst())) return;
           }
           
           
           Player current =table.removeFirst();
           Card playedCard = current.peg(pile.getCards(), pile.getCount());
           if(playedCard!=null)
           {
               current.addToScore(pile.addCard(playedCard));
               
               if(pile.getCount()==0)
               {
                   count=0;
               }
               leftToPlay--;
           }
           else
           {
               count++;
           }
           table.offerLast(current);
           
       }
       while(leftToPlay!=0);
       
        currentState=COUNTING;
    }
    
    /**
     * During the counting stage, the hands are counted
     */
    public void count()
    {
        io.announce("COUNTING...");
        for(Player player: players)
        {
            player.count();
            io.displayHand(player.toString(), player.getPegged(), player.getHand().getScore());
            if(checkForWinner(player)){currentState=GAMEOVER; return;}
        }
        players.getLast().addToScore(crib.getScore());
        io.displayHand(players.getLast()+"'s crib", crib.getCards(), crib.getScore());
        if(checkForWinner(players.getLast())){return;}
        
        
        
        currentState = NEXTROUND;
    }
    
    /**
     * During the NEXTROUND state, the players and deck are reset and the crib owner changes
     */
    public void nextRound()
    {
        io.displayScore(players);
        for(Player player: players)
        {
            player.reset();
        }
        cardDeck.reset();
        crib.clear();
        
        players.offerLast(players.removeFirst());
        io.announce(players.getLast()+" has the crib!");
        pile.reset();
        
        currentState = DEALING;    
    }
    
    private void gameover()
    {
        io.endingScreen(winner.toString());
        
            currentState = EXIT;
    }
    
    private void close()
    {
        io.close();
    }
    

    
    private boolean checkForWinner(Player player)
    {
            if(player.getScore()>=121)
            {
                this.winner = player;
                currentState = GAMEOVER;
                return true;
                
            }
        return false;
    }

}
