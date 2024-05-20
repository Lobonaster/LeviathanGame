package com.lebedev;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Objects;

public class CardClass extends Image {
    private float startX, startY;
    private VergVisuals targetActor;
    private GameScreen gameScreen;
    private int effect;
    private int price;
    private String type;

    public CardClass(Texture texture,VergVisuals targetActor,GameScreen gameScreen,int effect,int price,String type) {
        super(texture);
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
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (isOverlapping(targetActor) && VergVisuals.ENERGY != 0 && price <= VergVisuals.ENERGY)  {
                    VergVisuals.ENERGY -= price;
                    applyEffect(targetActor);
                    gameScreen.addToDiscardPile(CardClass.this);
                    remove();
                } else {
                    setPosition(startX, startY);
                }
            }
        });
    }

    private boolean isOverlapping(VergVisuals actor) {
        Rectangle cardBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle actorBounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        return cardBounds.overlaps(actorBounds);
    }

    private void applyEffect(VergVisuals actor) {
        switch (type){
            case ("skill"):
                actor.SHIELDS += effect;
                break;
            case ("atck"):
                actor.manage_HP(effect);
                break;
        }
    }

    public void setTargetActor(VergVisuals targetActor) {
        this.targetActor = targetActor;
    }
}
