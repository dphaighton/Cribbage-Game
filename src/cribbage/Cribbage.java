/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cribbage;

import Game.*;

/**
 *
 * @author David
 */
public class Cribbage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        HumanInterface display = new Console();
        
        Game game = new Game(display);
        game.play();
    }
    
}
