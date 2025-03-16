package com.example.project;
import java.util.ArrayList;

public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; // the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){ // Initializing hand and allCards
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {return hand;}
    public ArrayList<Card> getAllCards() {return allCards;}

    public void addCard(Card c) {
        hand.add(c); // adding card "c" into hand
    }

    public String playHand(ArrayList<Card> communityCards){ 
        allCards = new ArrayList<>(hand); // creating a new list, starting with only hand list
        allCards.addAll(communityCards); // adding community cards to the new list
        sortAllCards(); // sorting cards with sort method 

        ArrayList<Integer> rFreq = findRankingFrequency(); // ranking frequency 
        ArrayList<Integer> sFreq = findSuitFrequency(); // suit frequency

        System.out.println("rFreq: " + rFreq); // testing code

        boolean isStraight = false; // boolean to check if straight
        boolean flush = false; // boolean to check if there is a flush

        // Checking for flush 
        for (int count : sFreq) { // iterating through sFreq
            if (count >= 5) { // checking if there is a flush
                flush = true;
                break;
            }
        }

        // Checking for a straight
        int consecutive = 0;
        for (int i = 0; i < 13; i ++) { 
            if (rFreq.get(i) > 0) { // checks the rank of current card
                consecutive ++;
                if (consecutive == 5) { // checks if we have a straight
                    isStraight = true;
                    break;
                }
            } else {
                consecutive = 0; // makes consecutive 0 if it is not the same card (a straight)
            }
        }

        if (!isStraight && rFreq.get(0) > 0 && rFreq.get(1) > 0 && rFreq.get(2) > 0 && rFreq.get(3) > 0 && rFreq.get(12) > 0) { // checking for ace-low straight
            isStraight = true;
        }

        // new variables to check fours, threes, and pairs!
        int fourOfAKind = 0;  
        int threeOfAKind = 0;
        int pairCount = 0;
        int threeRank = -1;
        int pairRank1 = -1;
        int pairRank2 = -1;
    

        // counting rank occurances
        for (int i = 0; i < rFreq.size(); i ++) { // iterates through every rank in rank Frequency
            int count = rFreq.get(i); 
            if (count == 4) { // if a four is found, add onto fourOfAKind variable
                fourOfAKind ++;
            }

            if (count == 3) { // if a three is found, add onto threeOfAKind variable
                threeOfAKind ++;
                threeRank = i;

            }

            if (count == 2) { // if a pair is found...
                if (pairRank1 == -1 ) { // check if the first pair is empty --> there is only ONE pair so far
                    pairRank1 = i;
                } else { // if there is already a pair ...
                    pairRank2 = i; // a second pair is add --> This is for Two Pair
                }
                pairCount ++; //adding onto general pair count
            }
        }
        // testing/debugging codes
        System.out.println("fourOfAKind: " + fourOfAKind);
        System.out.println("threeOfAKind: " + threeOfAKind);
        System.out.println("pairCount: " + pairCount);
        System.out.println("flush: " + flush);
        System.out.println("straight: " + isStraight);
    
        if (flush && isStraight && allCards.get(allCards.size() - 1).getRank().equals("A")) { // checking for a royal flush with flush + straight
            return "Royal Flush";
        }

        if (flush && isStraight) { // checking for just a Straight Flush with flush and isStraight
            return "Straight Flush";
        }

        if (fourOfAKind == 1) { // checking to see if fourOfAKind appeared once 
            return "Four of a Kind";
        }

        if (threeOfAKind == 1 && pairCount >= 1) { // checking to see if a pair and a three appeared -- Full House
            return "Full House";
        }

        if (threeOfAKind == 1) { // Checking to see if a three appeared
            return "Three of a Kind";
        }


        if (flush) { // checks for a flush
            return "Flush";
        }

        if (isStraight) { // checks for a straight
            return "Straight";
        }

        if (pairCount == 2) { // checks for a two pair
            return "Two Pair";
        }

        if (pairCount == 1) { // checks for a single pair
            return "A Pair";
        }

        int highestPlayerCardValue = Utility.getRankValue(allCards.get(allCards.size() - 1).getRank()); // getting highest possible card
        for (int i = communityCards.size() - 1; i >= 0; i--) { // iterates through community card
            if (Utility.getRankValue(communityCards.get(i).getRank()) == highestPlayerCardValue) { // checking if the high card is in the community cards
                return "Nothing"; // will return nothing if the highest card is in the community cards
            }
        }
        
        return "High Card"; // Returns High Card if all other cases fail
    }

    public void sortAllCards() {
        for (int i = 1; i < allCards.size(); i ++) { // iterating through all cards
            Card one = allCards.get(i); // isolating a single card for checking
            int oneVal = Utility.getRankValue(one.getRank()); // isolating the rank of the current card
            int j = i - 1;

            while (j >= 0 && Utility.getRankValue(allCards.get(j).getRank()) > oneVal) { // Shifting the cards to the right until they reach the correct pos.
                allCards.set(j + 1, allCards.get(j)); 
                j --;
            }
            allCards.set(j + 1, one); // insers the current card into the sorted pos.
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> frequency = new ArrayList<>(13); // creates a frequency array with 13 slots
        for (int i = 0; i < 13; i++) { // iterates through each element in the new array and initializes them to 0
            frequency.add(0);
        }
    
        for (Card one : allCards) { // iterates through all the cards
            int rank = Utility.getRankValue(one.getRank()) - 2; // gets the rank value and adjusts it to the array idx 
            if (rank >= 0 && rank < 13) { // cgecks if the rank is a valid rank in Utility
                frequency.set(rank, frequency.get(rank) + 1); // adds frequency for the current rank
            }
        }
        return frequency; // returns a frequency array
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> frequency = new ArrayList<Integer>(4); // makes a new frequency list
        for (int i = 0; i < 4; i ++) { // iterates through all values (4) in frequency list and initializes them to 0
            frequency.add(0);
        }

        ArrayList<Card> combined = new ArrayList<Card>(hand); // makes a new combined array list and fills it with hand cards
        for (Card one : allCards) {  // addes allCards onto combined list
            combined.add(one);
        }

        for (Card one : combined) { // iterates through all combined cards
            String suit = one.getSuit(); // isolates the current suit 
            for (int i = 0; i < Utility.getSuits().length; i ++) { //iterates through all possible suits from Utility
                if (Utility.getSuits()[i].equals(suit)) { // checks if the current suit equals one from Utlitity
                    frequency.set(i, frequency.get(i) + 1); // Updates the frequency of the found suit
                }
            }
        }
        return frequency; // returns a frequency array
    }


    @Override
    public String toString(){
        return hand.toString();
    }
}