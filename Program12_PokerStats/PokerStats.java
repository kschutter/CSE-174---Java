/* Name: Kyle Schutter
 * Instructor: James Kiper
 * CSE 174, Section B
 * Date: 2015/11/9
 * Filename: PokerStats.java
 * Description: Simulates a specified number of poker hands
 *              and return a set of stats about them.*/

import java.util.Scanner;
import java.util.Arrays;

public class PokerStats{
  
  // Constants for representing the hand types
  public final static int LOSER = 0;
  public final static int ONEPAIR = 1;
  public final static int TWOPAIR = 2;
  public final static int THREEKIND = 3;
  public final static int STRAIGHT = 4;
  public final static int FLUSH = 5;
  public final static int FULLHOUSE = 6;
  public final static int FOURKIND = 7;
  public final static int STRAIGHTFLUSH = 8;
  public final static int ROYALFLUSH = 9;
  
  public static void main(String[] args) {
    
    Scanner input = new Scanner(System.in);
    int numOfHands;
    
    
    
    
    System.out.print("How many poker hands should I deal?");
    numOfHands = input.nextInt();
    
    // Create, deal, sort and categorize x hands from shuffled decks
    playAndShowStats(numOfHands);
  }
  
  public static void playAndShowStats(int n) {
    
    // Start timer
    final long startTime = System.currentTimeMillis();
    
    
    int numLoser=0, numOnePair=0, numTwoPair=0,
      numThreeKind=0, numStraight=0, numFlush=0, numFullHouse=0,
      numFourKind=0, numStrtFlush=0, numRoyalFlush=0;
    Double time;
    
    for (int i = 0; i < n; i++) {
      switch (evaluateOneHandOfPoker()) {
        case 0: numLoser++;
        break;
        case 1: numOnePair++;
        break;
        case 2: numTwoPair++;
        break;
        case 3: numThreeKind++;
        break;
        case 4: numStraight++;
        break;
        case 5: numFlush++;
        break;
        case 6: numFullHouse++;
        break;
        case 7: numFourKind++;
        break;
        case 8: numStrtFlush++;
        break;
        case 9: numRoyalFlush++;
        break;
      }
    }
    
    // End the timer and record
    time = (System.currentTimeMillis() - startTime) / 1000.0;
    
    // Print the results
    System.out.printf("%15s: %-3d%10.5f%%%n","Loser",numLoser,
                      100.0*numLoser/n);
    System.out.printf("%15s: %-3d%10.5f%%%n","One Pair",numOnePair,
                      100.0*numOnePair/n);
    System.out.printf("%15s: %-3d%10.5f%%%n","Two Pair",numTwoPair,
                      100.0*numTwoPair/n);
    System.out.printf("%9s: %-3d%10.5f%%%n","Three of a Kind",numThreeKind,
                      100.0*numThreeKind/n);
    System.out.printf("%15s: %-3d%11.5f%%%n","Straight",numStraight,
                      100.0*numStraight/n);
    System.out.printf("%15s: %-3d%11.5f%%%n","Flush",numFlush,
                      100.0*numFlush/n);
    System.out.printf("%15s: %-3d%11.5f%%%n","Full House",numFullHouse,
                      100.0*numFullHouse/n);
    System.out.printf("%13s: %-3d%12.5f%%%n"," Four of a Kind",numFourKind,
                      100.0*numFourKind/n);
    System.out.printf("%13s: %-1d%13.5f%%%n"," Straight Flush",numStrtFlush,
                      100.0*numStrtFlush/n);
    System.out.printf("%15s: %-1d%13.5f%%%n","RoyalFlush",numRoyalFlush,
                      100.0*numRoyalFlush/n);
    System.out.print("-------------------------------------------\n");
    System.out.printf("%d hands analyzed in %.3f seconds",n,time);
  }
  
  // Evaluates and returns the best hand type by using the other methods
  public static int evaluateOneHandOfPoker() {
    Deck deck = new Deck();
    Card[] hand = new Card[5];
    int type;
    deck.reset();
    deck.shuffle();
    for (int j = 0; j < 5; j++) {
      hand[j] = deck.deal();
    }
    Arrays.sort(hand);
    if (hasRoyalFlush(hand))
      type = ROYALFLUSH;
    else if (hasStraightFlush(hand))
      type = STRAIGHTFLUSH;
    else if (has4OfAKind(hand))
      type = FOURKIND;
    else if (hasFullHouse(hand))
      type = FULLHOUSE;
    else if (hasFlush(hand))
      type = FLUSH;
    else if (hasStraight(hand))
      type = STRAIGHT;
    else if (has3OfAKind(hand))
      type = THREEKIND;
    else if (has2Pair(hand))
      type = TWOPAIR;
    else if (hasPair(hand))
      type = ONEPAIR;
    else
      type = LOSER;
    return type;
  }
  
  // Print the current hand
  public static void printHand(Card[] cards) {
    System.out.println();
    for (int i = 0; i < 5; i++) {
      System.out.print(cards[i].toString() + " ");
    }
  }
  
  // Returns true if the hand has a flush
  public static boolean hasFlush(Card[] hand) {
    Boolean flush = true;
    for (int i = 0; i < 4; i++) {
      if (hand[i].getSuit() != hand[i+1].getSuit())
        flush = false;
    }
    return flush;
  }
  
  // Returns true if the hand has a straight
  public static boolean hasStraight(Card[] hand) {
    Boolean straight = true;
    for (int i = 0; i < 4; i++) {
      if (hand[i].getValue() + 1 != hand[i+1].getValue())
        straight = false;
    }
    return straight;
  }
  
    // Returns true if the hand has a straight flush
  public static boolean hasStraightFlush(Card[] hand) {
    return hasStraight(hand) && hasFlush(hand);
  }
  
    // Returns true if the hand has a royal flush
  public static boolean hasRoyalFlush(Card[] hand) {
     Boolean royal = true;
     if (hand[0].getValue() != 1)
       royal = false;
     for (int i = 1; i < 4; i++) {
       if (hand[i].getValue() + 1 != hand[i+1].getValue())
         royal = false;
     }
     return royal && hasFlush(hand);
  }
  
    // Returns true if the hand has four of a kind
  public static boolean has4OfAKind(Card[] hand) {
    Boolean kind4 = false;
    for (int j = 0; j < 2; j++) {
      A: for (int i = j; i < 3 + j; i++) {
        if (hand[i].getValue() != hand[i+1].getValue())
          break A;
        if (i - j == 2)
          kind4 = true;
      }
    }
    return kind4;
  }
  
    // Returns true if the hand has three of a kind
  public static boolean has3OfAKind(Card[] hand) {
    Boolean kind3 = false;
    for (int j = 0; j < 3; j++) {
      A: for (int i = j; i < 2 + j; i++) {
        if (hand[i].getValue() != hand[i+1].getValue())
          break A;
        if (i - j == 1)
          kind3 = true;
      }
    }
    return kind3;
  }
  
    // Returns true if the hand has a pair
  public static boolean hasPair(Card[] hand) {
    Boolean pair = false;
    for (int j = 0; j < 4; j++) {
      A: for (int i = j; i < 1 + j; i++) {
        if (hand[i].getValue() != hand[i+1].getValue())
          break A;
        if (i - j == 0)
          pair = true;
      }
    }
    return pair;
  }
  
    // Returns true if the hand has a 2 pair
  public static boolean has2Pair(Card[] hand) {
    int count = 0;
    for (int j = 0; j < 4; j++) {
      A: for (int i = j; i < 1 + j; i++) {
        if (hand[i].getValue() != hand[i+1].getValue())
          break A;
        if (i - j == 0)
          count++;
      }
    }
    return (count > 1);
  }
  
    // Returns true if the hand has a full house
  public static boolean hasFullHouse(Card[] hand) {
    return (has3OfAKind(hand) && 
            ((hand[0].getValue() == hand[1].getValue()
              && hand[1].getValue() != hand[2].getValue()) ||
             (hand[3].getValue() == hand[4].getValue()
              && hand[2].getValue() != hand[3].getValue())));
  }
}