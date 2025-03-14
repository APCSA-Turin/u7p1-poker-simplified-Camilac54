package com.example.project;
import java.util.ArrayList;

public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
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
        allCards = new ArrayList<>(hand);
        allCards.addAll(communityCards);
        sortAllCards(); // Sorting the cards


        ArrayList<Integer> rFreq = findRankingFrequency(); // Finding rank frequency
        ArrayList<Integer> sFreq = findSuitFrequency(); // Finding suit frequency

        // Straight checking
        int consecutive = 0;
        int straightCount = 0;
        int prevRank = -1;
        boolean isStraight = false;


        for (Card one : allCards) { // Used to check through all the cards to find hand
            int currentRank = Utility.getRankValue(one.getRank());
            if (prevRank != -1 && currentRank == prevRank + 1) {
                consecutive ++; // Adding onto consecutive if statement is true
                straightCount ++; // Adding onto straight counter
                if (consecutive == 4) { // Checks if consecutive is 4, which means its a Straight
                    isStraight = true;
                    break;
                }
            }
            else if (prevRank != currentRank) {
                consecutive = 0;
            }
            prevRank = currentRank;
        }

        // Flush checking
        boolean flush = false;
        for (int count : sFreq) {
            if (count == 5) {
                flush = true;
                break;
            }
        }

        // Rank frequency checking
        int fourOfAKind = 0;
        int threeOfAKind = 0;
        // boolean fourOfAKind = false;
        // boolean threeOfAKind = false;
        // boolean pair = false;
        int pairCount = 0;

        for (int count : rFreq) {
            if (count == 4) {
                fourOfAKind ++;
            }
           
            if (count == 3) {
                threeOfAKind ++;
            }

            if (count == 2) {
                pairCount ++;
            }
        }

        // if (!isStraight && allCards.get(0).getRank().equals("2") && allCards.get(allCards.size() - 1).getRank().equals("A")) { 
        //     isStraight = true;
        // }

        if (flush && isStraight && allCards.get(allCards.size() - 1).getRank().equals("A")) {
            return "Royal Flush";
        }

        // if (flush && isStraight) {
        //     boolean royalFlush = true;
        //     String[] royalRanks = {"10", "J", "Q", "K", "A"};
        //     for (String rank : royalRanks) {
        //         boolean found = false;
        //         for (Card card : allCards) {
        //             if (card.getRank().equals(rank) && card.getSuit().equals(flushSuit)) {
        //                 found = true;
        //                 break;
        //             }
        //         }
        //         if (!found) {
        //             royalFlush = false;
        //             break;
        //         }
        //     }
        //     if (royalFlush) return "Royal Flush";
        // }



        // if (flush  && isStraight && allCards.get(allCards.size() - 1).getRank().equals("A")) {
        //     return "Royal Flush";
        // }


        if (flush && isStraight) {
            return "Straight Flush";
        }

        if (fourOfAKind == 1) {
            return "Four of a Kind";
        }

        if (threeOfAKind == 1 && pairCount >= 1) {
            return "Full House";
        }

        if (flush) {
            return "Flush";
        }

        if (isStraight) {
            return "Straight";
        }

        if (threeOfAKind == 1) {
            return "Three of a Kind";
        }

        if (pairCount == 2) {
            return "Two Pair";
        }

        if (pairCount == 1) {
            return "A Pair";
        }

        // boolean highCard = false;
        // for (int i = 0; i < allCards.size(); i ++) {
        //     if (hand.get(0) != allCards.get(i) && hand.get(1) != allCards.get(i)) {
        //         highCard = false;
        //         if (getRankNum(hand.get(0)) > getRankNum(allCards.get(i)) || getRankNum(hand.get(1)) > getRankNum(allCards.get(i))) {
        //             highCard = true;
        //         }
        //     }
        // }


        // if (highCard == true) {
        //     return "High Card";
        // }
        int highestHandCardRank = getRankNum(hand.get(0));
        if (getRankNum(hand.get(1)) > highestHandCardRank) {
            highestHandCardRank = getRankNum(hand.get(1));
        }
   
        int highestCombinedCardRank = 0;
        for (Card card : allCards) {
            int rank = getRankNum(card);
            if (rank > highestCombinedCardRank) {
                highestCombinedCardRank = rank;
            }
        }
   
        if (highestHandCardRank > highestCombinedCardRank) {
            return "High Card";
        }
   
        return "Nothing";        
    }

    public void sortAllCards() {
        for (int i = 1; i < allCards.size(); i ++) {
            Card one = allCards.get(i);
            int oneVal = Utility.getRankValue(one.getRank());
            int j = i - 1;

            while (j >= 0 && Utility.getRankValue(allCards.get(j).getRank()) > oneVal) {
                allCards.set(j + 1, allCards.get(j));
                j --;
            }
            allCards.set(j + 1, one);
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> frequency = new ArrayList<Integer>(13);
        for (int i = 0; i < 13; i ++) {
            frequency.add(0);
        }
        ArrayList<Card> combined = new ArrayList<Card>(hand);
        for (Card one : allCards) {
            combined.add(one);
        }

        for (Card one : combined) {
            int rank = Utility.getRankValue(one.getRank()) - 2;
            if (rank >= 0 && rank < 13) {
                frequency.set(rank, frequency.get(rank) + 1);
            }
        }
        return frequency; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        ArrayList<Integer> frequency = new ArrayList<Integer>(4);
        for (int i = 0; i < 4; i ++) {
            frequency.add(0);
        }

        ArrayList<Card> combined = new ArrayList<Card>(hand);
        for (Card one : allCards) {
            combined.add(one);
        }

        for (Card one : combined) {
            String suit = one.getSuit();
            for (int i = 0; i < Utility.getSuits().length; i ++) {
                if (Utility.getSuits()[i].equals(suit)) {
                    frequency.set(i, frequency.get(i) + 1);
                }
            }
        }
        return frequency; 
    }

    private int getRankNum(Card rank) {
        return Utility.getRankValue(rank.getRank());
    }

    @Override
    public String toString(){
        return hand.toString();
    }
}