package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Collections;

public class DeckGenerator {
    private ArrayList<CardClass> deck;
    public ArrayList<CardClass> discardPile;
    private Texture defendTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites/Cards/Defend.png"));;
    private Texture strikeTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/Strike.png"));
    private Texture bashTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/Bash.png"));;
    private VergVisuals targetActor;
    private GameScreen gameScreen;

    public DeckGenerator(GameScreen gameScreen,VergVisuals targetActor) {

        this.targetActor = targetActor;
        this.deck = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.gameScreen = gameScreen;

        generateDeck();
        shuffleDeck();
    }

    private void generateDeck() {
        for (int i = 0; i < 4; i++) {
            deck.add(new CardClass(defendTexture, targetActor,gameScreen ,5,1,"skill"));
        }
        for (int i = 0; i < 5; i++) {
            deck.add(new CardClass(strikeTexture, targetActor, gameScreen,-6,1,"atck"));
        }
        deck.add(new CardClass(bashTexture, targetActor,gameScreen ,-14,2,"atck"));
    }

    public ArrayList<CardClass> drawCards(int count) {
        ArrayList<CardClass> hand = new ArrayList<>();
        for (int i = 0; i < count && !deck.isEmpty(); i++) {
            hand.add(deck.remove(0));
        }
        return hand;
    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void returnCards(ArrayList<CardClass> cards) {
        discardPile.addAll(cards);
    }

    public int getRemainingCards() {
        return deck.size();
    }

    public int getDiscardedCards() {
        return discardPile.size();
    }

    public void discardCard(CardClass card) {
        discardPile.add(card);
    }

    public void discardedBackToDeck(ArrayList<CardClass> discardPile) {
        deck.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(deck);
    }
}
