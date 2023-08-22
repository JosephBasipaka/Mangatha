package com.learning.hello.controller;

public class Player {
    private String playerName;
    private Card choosenCard;
    private boolean isIn;
    private String result;

    public Player(String playerName, Card choosenCard, boolean isIn) {
        this.playerName = playerName;
        this.choosenCard = choosenCard;
        this.isIn = isIn;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Card getChoosenCard() {
        return choosenCard;
    }

    public boolean isIn() {
        return isIn;
    }
    public String getPos() {
    	return (isIn) ? "In": "Out";
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
