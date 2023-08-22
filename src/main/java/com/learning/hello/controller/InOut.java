package com.learning.hello.controller;

public class InOut {

	private final Card choosenCard;

    public InOut(Card choosenCard) {
        this.choosenCard = choosenCard;
    }

    public boolean isIn(Card card) {
        return card.equals(choosenCard);
    }

    public boolean isOut(Card card) {
        return !card.equals(choosenCard);
    }
}
