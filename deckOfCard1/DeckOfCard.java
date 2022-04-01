package com.Bridgelabz.deckOfCard1;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.*;

public class DeckOfCard {

	private static final String[] SUIT = { "Spades", "Hearts", "Diamond", "Clubs" };
	private static final String[] RANK = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };
	private static final String[][] deckOfCards = { SUIT, RANK };
	private static final Card[] card_deck = new Card[52];
	private static final List<Player> list = new ArrayList<>();
	private static final Scanner sc = new Scanner(System.in);
	Map<String, HashMap<String, Integer>> playerCardInfo = new HashMap<>();

	public void setupDeckOfCards() {
		int i = 0;
		for (String s : deckOfCards[0]) {
			for (String value : deckOfCards[1]) {
				card_deck[i++] = new Card(s, value);
			}
		}
	}
	public void addPlayer(int numberOfPlayers) {
		System.out.println("Enter Players Details");
		
			for (int i = 0; i < numberOfPlayers; i++) {
				System.out.println("Enter first name");
				String first_name = sc.nextLine();
				System.out.println("Enter second name");
				String second_name = sc.nextLine();
				System.out.println("Enter player turn");
				int playerTurn = sc.nextInt();
				Player player = new Player(second_name, first_name, playerTurn);
				list.add(player);
				for (Player value : list) {
					System.out.println(value);
				}
			}
	}
	public void orderPlayerTurn(int numberOfPlayers) {
		System.out.println("Enter players order");
		for (int i = 0; i < numberOfPlayers; i++) {
			int turn = sc.nextInt();
			list.get(i).setPlayerTurn(turn);
		}
	}
	public void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < deckOfCards.length; i++) {
			String[] tempArray = deckOfCards[i];
			for (int j = 0; j < tempArray.length; j++) {
				int random = j + rand.nextInt(tempArray.length - j);
				String temp = tempArray[random];
				tempArray[random] = tempArray[j];
				tempArray[j] = temp;
			}
			deckOfCards[i] = tempArray;
		}
	}
	public void distributeCards() {
		list.stream().sorted(Comparator.comparingInt(Player::getPlayerTurn)).collect(Collectors.toList());
		for (int p = 0; p < list.size(); p++) {
			int count = 0;
			int cardIndex = p;
			Card[] cardSet = new Card[9];
			while (count < 9) {
				Card card = card_deck[cardIndex];
				cardSet[count] = card;
				cardIndex += 5;
				count++;
			}
			list.get(p).setCard(cardSet);
		}
	}
	public void displayCard() {
		for (Player player : list) {
			HashMap<String, Integer> cardInfo = new HashMap<>();
			Card[] cardArray = player.getCard();
			for (Card card : cardArray) {
				if (cardInfo.containsKey(card.getSuit())) {
					Integer value = cardInfo.get(card.getSuit());
					cardInfo.put(card.getSuit(), value + 1);
				} else {
					cardInfo.put(card.getSuit(), 1);
				}
			}
			playerCardInfo.put(player.getFirstName(), cardInfo);
		}
		for (Map.Entry<String, HashMap<String, Integer>> entry : playerCardInfo.entrySet())
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
	}
	public void displayCardSortByRank()   {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Player" + (i + 1) + " cards");
			Card[] cardArray = list.get(i).getCard();
			Arrays.sort(cardArray, new SortbyRank());
			for (Card card : cardArray) {
				System.out.println(card);
			}
			System.out.println();
		   }
		}
}
 	class SortbyRank implements Comparator<Card> {
		
		@Override
		public int compare(Card card1, Card card2) {
			if (Objects.equals(card1.getRank(), card2.getRank())) {
			return card1.getSuit().compareTo(card2.getSuit());
		 }
			return card1.getRank().compareTo(card2.getRank());
		}	
 }		
