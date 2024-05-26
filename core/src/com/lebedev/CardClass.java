package com.lebedev;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class CardClass extends Image {
    private float startX, startY;
    private Verg targetActor;
    private enemyTest enemy;
    private GameScreen gameScreen;
    private int effect;
    private int price;
    private String type;

    public CardClass(Texture texture, Verg targetActor, enemyTest enemy, GameScreen gameScreen, int effect, int price, String type) {
        super(texture);
        this.enemy = enemy;
        this.targetActor = targetActor;
        this.effect = effect;
        this.price = price;
        this.type = type;
        this.gameScreen = gameScreen;

        this.addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                startX = getX();
                startY = getY();
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                moveBy(x - getWidth() / 2, y - getHeight() / 2);
                checkOverlap();
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (isOverlapping(targetActor) && type.equals("skill") && Verg.ENERGY != 0 && price <= Verg.ENERGY) {
                    Verg.ENERGY -= price;
                    applyEffect(targetActor);
                    gameScreen.addToDiscardPile(CardClass.this);
                    remove();
                } else if (isOverlapping(enemy) && type.equals("atck") && Verg.ENERGY != 0 && price <= Verg.ENERGY) {
                    Verg.ENERGY -= price;
                    applyEffectEnemy(enemy,targetActor);
                    gameScreen.addToDiscardPile(CardClass.this);
                    remove();
                } else {
                        setPosition(startX, startY);
                    }
                setColor(Color.WHITE);
                }
        });
    }

    private boolean isOverlapping(Verg actor) {
        Rectangle cardBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle actorBounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        return cardBounds.overlaps(actorBounds);
    }

    private boolean isOverlapping(enemyTest enemy) {
        Rectangle cardBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle enemyBounds = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        return cardBounds.overlaps(enemyBounds);
    }

    private void checkOverlap() {
        if (isOverlapping(targetActor) && type.equals("skill")) {
            setColor(Color.GREEN);
        } else if (isOverlapping(enemy) && type.equals("atck")) {
            setColor(Color.GREEN);
        } else {
            setColor(Color.WHITE);
        }
    }

    private void applyEffect(Verg actor) {
        actor.SHIELDS += effect;
    }

    private void applyEffectEnemy(enemyTest enemy,Verg actor ) {
        actor.perform_attack();
        enemy.manage_HP(effect);
    }

    public void setTargetActor(Verg targetActor) {
        this.targetActor = targetActor;
    }
}
