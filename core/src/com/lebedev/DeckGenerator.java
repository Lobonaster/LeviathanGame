package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Collections;

public class DeckGenerator {
    public static ArrayList<CardClass> global_deck = LeviathanGame.global_deck;
    private static ArrayList<CardClass> deck = new ArrayList<>();
    public ArrayList<CardClass> discardPile;
    private static Texture defendTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites/Cards/Defend.png"));
    private static Texture strikeTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/Strike.png"));
    private static Texture bashTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/Bash.png"));
    private static Texture verticalSlashTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/vertical.png"));
    private static Texture greatDefTexture = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/greatDef.png"));
    private static Texture curse = new Texture(Gdx.files.internal("assets/Pictures/Sprites//Cards/Placeholder.png"));
    private static Verg targetActor;
    private static enemyTest targetEnemy;
    private static GameScreen gameScreen;
    private static EventScreen eventScreen;
    //Cards that need to be generated
    private static int defAmnt = 4 ;
    private static int atckAmnt = 5 ;
    private static int bashAmnt = 1 ;
    private static int verticalAmnt = 0 ;
    private static int greatDefAmnt = 0 ;
    private static int curseAmnt = 0;

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

    public static int getdecksize() {
        return deck.size();
    }

    private void generateDeck() {
        for (int i = 0; i < defAmnt; i++) {
            deck.add(new CardClass(defendTexture, targetActor,targetEnemy,gameScreen,
                    5,1,"skill","click"));
        }
        for (int i = 0; i < atckAmnt; i++) {
            deck.add(new CardClass(strikeTexture, targetActor,targetEnemy, gameScreen,
                    -6-Verg.strength,1,"atck","swordSnd"));

        }
        for (int i = 0; i < bashAmnt; i++) {
            deck.add(new CardClass(bashTexture, targetActor, targetEnemy, gameScreen,
                    -14 - Verg.strength, 2, "atck", "bash"));
        }
        for (int i = 0; i < verticalAmnt; i++) {
            deck.add(new CardClass(verticalSlashTexture, targetActor,targetEnemy,
                    gameScreen ,-30-Verg.strength,3,"atck","swordSnd"));
        }
        for (int i = 0; i < greatDefAmnt; i++) {
            deck.add(new CardClass(greatDefTexture, targetActor, targetEnemy,
                    gameScreen, 16, 2, "skill","click"));
        }
        for (int i = 0; i < curseAmnt; i++) {
            deck.add(new CardClass(curse, targetActor,targetEnemy,
                    gameScreen ,0,999,"skill","swordSnd"));
        }
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

    public static void defaultDeck(){
        defAmnt = 4;
        atckAmnt = 5;
        bashAmnt = 1;
        verticalAmnt = 0;
        greatDefAmnt = 0;
        curseAmnt = 0;
    }

    public static void addCard(String cardName, int amount){
        switch (cardName){
            case "defend+":
                greatDefAmnt += amount;
                break;
            case "bash":
                bashAmnt += amount;
                break;
            case "verticalSlash":
                verticalAmnt += amount;
                break;
            case "curse":
                curseAmnt += amount;
                break;
        }
    }

    public void discardedBackToDeck(ArrayList<CardClass> discardPile) {
        Collections.shuffle(discardPile);
        deck.addAll(discardPile);
        discardPile.clear();
    }
}
