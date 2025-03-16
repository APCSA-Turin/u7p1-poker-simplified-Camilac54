package com.example.project;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // // three of a kind problem 
        // // Test code
        // Player player = new Player();
        // player.addCard(new Card("5", "♠"));
        // player.addCard(new Card("8", "♦"));
    
        // ArrayList<Card> communityCards = new ArrayList<>();
        // communityCards.add(new Card("A", "♣"));
        // communityCards.add(new Card("8", "♠"));
        // communityCards.add(new Card("8", "♣"));
        
        // player.playHand(communityCards);
        // String handResult = player.playHand(communityCards);
        // System.out.println("-----");
        // System.out.println(handResult); // Supposed to be three of a kind 

        Player player = new Player();
        player.addCard(new Card("A", "♠"));
        player.addCard(new Card("6", "♦"));
        
        // Community Cards
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("5", "♣"));
        communityCards.add(new Card("2", "♠"));
        communityCards.add(new Card("3", "♠"));
        
        player.playHand(communityCards);
        String handResult = player.playHand(communityCards);
        
        System.out.println(handResult); // highest card

    }
}