package com.learning.hello.controller;

import java.io.IOException;
import java.util.*;
public class MangathaController {
	public List<Card> cards = new ArrayList<>();
	private List<Player> players = new ArrayList<>();
	public MangathaController() {
	for(int suit = 1 ; suit <= Card.MAX_SUIT ; suit++) {
		for(int rank = 1 ; rank <= Card.MAX_RANK; rank++) {
			cards.add(new Card(rank,suit));
			}
		}
	}
	
	private Card inCard;
	private Card outCard;
	public void shuffle()
	{
		Collections.shuffle(cards);
	}
	public Card drawRandom() {
		Random randomIndex = new Random();	
		return cards.remove(randomIndex.nextInt(0,cards.size()));
	}
	
	public Card removeTop() {
		if(cards.size() == 0) {
			return new Card(0,0);
		}
		return cards.remove(0);
	}
	public void addCards() {
		inCard = removeTop();
		outCard = removeTop();
		
	}
	
	public Card getinCard() {
		return inCard;
	}
	public Card getOutCard() {
		return outCard;
	}
	
	public Card removeBottom() {
		if(cards.size() == 0) {
			return new Card(0,0);
		}
			return cards.remove(cards.size() - 1);
	}
	
	public String toString() {
		return cards.toString();
		}

    public void addPlayer(String playerName, Card choosenCard, boolean isIn) {
        Player player = new Player(playerName, choosenCard, isIn);
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
    
	public String evaluatePlayers(Card choosenCard) {
        InOut inAndOut = new InOut(choosenCard);
        StringBuilder res = new StringBuilder();

        for (Player player : players) {
            boolean isChoosenCardIn = player.isIn();
            boolean isIn = inAndOut.isIn(player.getChoosenCard());

            if (isChoosenCardIn == isIn) {
                player.setResult("Winner!!!");
                res.append(player.getPlayerName()).append("Winner!!!");
            } else {
                player.setResult("Loss###");
                res.append(player.getPlayerName()).append("Loss###");
            }
        }
        return res.toString();
    
    }
	
	public void reset() {
		this.players = null ;
	}
    
}