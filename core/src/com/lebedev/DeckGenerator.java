package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Collections;

public class DeckGenerator {
    private static final ArrayList<CardClass> global_deck = LeviathanGame.global_deck;
    private ArrayList<CardClass> deck;
    public ArrayList<CardClass> discardPile;
    private static Texture defendTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites/Cards/Defend.png"));
    private static Texture strikeTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/Strike.png"));
    private static Texture bashTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/Bash.png"));
    private static Texture verticalSlashTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/PlaceHolder.png"));
    private static Verg targetActor;
    private static enemyTest targetEnemy;
    private static GameScreen gameScreen;
    private static EventScreen eventScreen;

    public DeckGenerator(GameScreen gameScreen, Verg targetActor, enemyTest targetEnemy) {
        this.targetEnemy = targetEnemy;
        this.targetActor = targetActor;
        this.deck = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.gameScreen = gameScreen;

        generateDeck();
        shuffleDeck();
    }
    public DeckGenerator(EventScreen eventScreen, Verg targetActor, enemyTest targetEnemy) {
        this.targetEnemy = targetEnemy;
        this.targetActor = targetActor;
        this.deck = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.eventScreen = eventScreen;
    }

    private void generateDeck() {
        for (int i = 0; i < 4; i++) {
            deck.add(new CardClass(defendTexture, targetActor,targetEnemy,gameScreen,
                    5,1,"skill","click"));
        }
        for (int i = 0; i < 5; i++) {
            deck.add(new CardClass(strikeTexture, targetActor,targetEnemy, gameScreen,
                    -226-Verg.strength,1,"atck","swordSnd"));

        }
        deck.add(new CardClass(bashTexture, targetActor,targetEnemy,gameScreen,
                -124-Verg.strength,2,"atck","swordSnd"));
        deck.addAll(global_deck);
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

    public static void addCard(String cardName){
        switch (cardName){
            case "defend":
                global_deck.add(new CardClass(defendTexture, targetActor, targetEnemy,
                        gameScreen, 5, 1, "skill","click"));
                System.out.println("added def card");
                break;
            case "strike":
                global_deck.add(new CardClass(strikeTexture, targetActor, targetEnemy,
                        gameScreen, -6-Verg.strength, 1, "atck","swordSnd"));
                System.out.println("added strike card");
                break;
            case "bash":
                global_deck.add(new CardClass(bashTexture, targetActor,targetEnemy,
                        gameScreen ,-14-Verg.strength,2,"atck","swordSnd"));
                break;
            case "verticalSlash":
                global_deck.add(new CardClass(verticalSlashTexture, targetActor,targetEnemy,
                        gameScreen ,-30-Verg.strength,3,"atck","swordSnd"));
                break;
            case "curse":
                global_deck.add(new CardClass(verticalSlashTexture, targetActor,targetEnemy,
                        gameScreen ,0,999,"skill","swordSnd"));
                break;
        }
    }

    public void discardedBackToDeck(ArrayList<CardClass> discardPile) {
        deck.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(deck);
    }
}
