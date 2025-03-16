package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        int p1R = Utility.getHandRanking(p1Hand); // gets player ONE ranks
        int p2R = Utility.getHandRanking(p2Hand); // gets player TWO ranks 

        if (p1R > p2R) {
            return "Player 1 wins!";
        }

        if (p2R > p1R) {
            return "Player 2 wins!";
        }

        if (p1R == 5 && p2R == 5) {
            int p1ThreeRank = getThreeHand(p1, communityCards);
            int p2ThreeRank = getThreeHand(p2, communityCards);

            if (p1ThreeRank > p2ThreeRank) {
                return "Player 1 wins!";
            } else if (p2ThreeRank > p1ThreeRank) {
                return "Player 2 wins!";
            }
        }

        int p1High = getHighCard(p1, communityCards);
        int p2High = getHighCard(p2, communityCards);

        if (p1High > p2High) {
            return "Player 1 wins!";
        }

        if (p2High > p1High) {
            return "Player 2 wins!";
        }

        int p1HighInHand = getHighCardInHand(p1);
        int p2HighInHand = getHighCardInHand(p2);

        if (p1HighInHand > p2HighInHand) {
            return "Player 1 wins!";
        }

        if (p2HighInHand > p1HighInHand) {
            return "Player 2 wins!";
        }

        return "Tie!"; 
    }

    public static void play(){ // simulate card playing
        Deck deck = new Deck();
        deck.shuffleDeck();
       
        Player p1 = new Player();
        Player p2 = new Player();

        for (int i = 0; i < 2; i++) {
            p1.addCard(deck.drawCard());
            p2.addCard(deck.drawCard());
        }
       
        ArrayList<Card> communityCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.drawCard());
        }

        String p1Hand = p1.playHand(communityCards);
        String p2Hand = p2.playHand(communityCards);
       
        String result = determineWinner(p1, p2, p1Hand, p2Hand, communityCards);
     
        System.out.println("Player 1 Hand: " + p1Hand);
        System.out.println("Player 2 Hand: " + p2Hand);
        System.out.println(result);
   
    }

    private static int getHighCard(Player player, ArrayList<Card> communityCards)
    {
        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.addAll(player.getHand());
        int highCardValue = -1;
        for (Card card : allCards)
        {
            int cardValue = Utility.getRankValue(card.getRank());
            if (cardValue > highCardValue)
            {
                highCardValue = cardValue;
            }
        }
   
        return highCardValue;
    }

    private static int getThreeHand(Player player, ArrayList<Card> communityCards)
    {
        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.addAll(player.getHand());
        ArrayList<Integer> values = new ArrayList<>();


        for (Card card : allCards)
        {
            values.add(Utility.getRankValue(card.getRank()));
        }
   
       
        for (Integer value : values)
        {
            int count = 0;
            for (int v : values)
            {
            if (v == value)
            {
                count++;
                }  
            }
            if (count == 3)
            {
                return value;
            }
        }
   
        return -1;
    }

    private static int getHighCardInHand(Player player)
    {
        int highCardValue = -1;  
        for (Card card : player.getHand())
        {
            int cardValue = Utility.getRankValue(card.getRank());
            if (cardValue > highCardValue)
            {
                highCardValue = cardValue;
            }
        }
   
        return highCardValue;  
    }
}