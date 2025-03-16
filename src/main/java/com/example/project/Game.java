package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        int p1R = Utility.getHandRanking(p1Hand); // gets player ONE ranks
        int p2R = Utility.getHandRanking(p2Hand); // gets player TWO ranks 

        if (p1R > p2R) { // checks if player 1 wins over player 2
            return "Player 1 wins!";
        }

        if (p2R > p1R) { // checks if player 2 wins over player 1
            return "Player 2 wins!";
        }

        if (p1R == 5 && p2R == 5) { // checks if both players have 5 for rankings, which is the tie-breaking part
            int p1ThreeRank = getThreeHand(p1, communityCards); // calculates the ranking of threeOfAKind for player 1
            int p2ThreeRank = getThreeHand(p2, communityCards); // calculates the ranking of threeOfAKind for player 2

            if (p1ThreeRank > p2ThreeRank) { // compares both of the player rankings 
                return "Player 1 wins!";
            } else { 
                return "Player 2 wins!";
            }
        }

        int p1HighInHand = getHighCard(p1); // gets highest card for player 1  
        int p2HighInHand = getHighCard(p2); // gets highest card for player 2

        if (p1HighInHand > p2HighInHand) {  // checks if player 1 wins over player 2
            return "Player 1 wins!";
        }

        if (p2HighInHand > p1HighInHand) {  // checks if player 2 wins over player 1
            return "Player 2 wins!";
        }

        return "Tie!"; // returns tie if all else fails 
    }

    public static void play() {
        Deck deck = new Deck(); // creates new deck
        deck.shuffleDeck(); // shuffles deck
       
        // two new players
        Player p1 = new Player(); 
        Player p2 = new Player();

        for (int i = 0; i < 2; i++) { // makes the players draw/deal two times 
            p1.addCard(deck.drawCard());
            p2.addCard(deck.drawCard());
        }
       
        ArrayList<Card> communityCards = new ArrayList<>(); // community deck
        for (int i = 0; i < 3; i++) { // gives community deck three cards/draws
            communityCards.add(deck.drawCard());
        }

        String p1Hand = p1.playHand(communityCards); // Plays player 1's hand 
        String p2Hand = p2.playHand(communityCards); // Plays player 2's hand 
       
        String result = determineWinner(p1, p2, p1Hand, p2Hand, communityCards); // determines winner of the play
     
        // Prints results
        System.out.println("Player 1 Hand: " + p1Hand); 
        System.out.println("Player 2 Hand: " + p2Hand);
        System.out.println(result);
   
    }

     // HELPER METHODS FOR CLARITY 
    private static int getHighCard (Player player) {
        int highCard = -1; // beginning value 
        for (Card one : player.getHand()) { // iterates through all of the cards in players hand
            int cardVal = Utility.getRankValue(one.getRank());
            if (cardVal > highCard) { // checks if the rank of the card is greater than the current high card
                highCard = cardVal; // if its true, changes high card to current card
            }
        }
   
        return highCard; // returns high card 
    }

    private static int getThreeHand(Player player, ArrayList<Card> communityCards) {
        ArrayList<Card> allCards = new ArrayList<>(communityCards); // all cards variable, begins with community cards
        allCards.addAll(player.getHand()); // adds all of the players cards to the array list
        ArrayList<Integer> values = new ArrayList<>(); // new values array list

        for (Card one : allCards) { // iterates through all cards
            values.add(Utility.getRankValue(one.getRank())); // adds the rank of each card to values
        }
       
        for (Integer one : values) { // iterates through (unique) values
            int count = 0;
            for (int i : values) { // iterates through the int version of the values
                if (i == one) { // if the unique value matches the int value 
                    count++; // adding onto count 
                }  
            }

            if (count == 3) { // checks if the count is three (would be a three hand if so)
                return one; // returns the rank of the three of a kind 
            }
        }
        return -1; // returns -1 if there is no three of a kind found 
    }
}