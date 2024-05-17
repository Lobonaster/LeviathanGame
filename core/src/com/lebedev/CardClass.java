package com.lebedev;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class CardClass extends Image {
    private float startX, startY;
    private int pic_width = 350 ;
    private int pic_height = 450 ;
    private VergVisuals targetActor;

    public CardClass(Texture texture, VergVisuals targetActor) {
        super(texture);
        setBounds(0,0,getWidth()/4,getHeight()/4);
        this.targetActor = targetActor;
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
                if (isOverlapping(targetActor)) {
                    applyEffect(targetActor);
                    setPosition(startX, startY);
                    remove();
                } else {
                    setPosition(startX, startY);
                }
            }
        });
    }

    private boolean isOverlapping(VergVisuals actor) {
        Rectangle cardBounds = new Rectangle(getX(), getY(),getWidth(), getHeight());
        Rectangle actorBounds = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        return cardBounds.overlaps(actorBounds);
    }

    private void applyEffect(VergVisuals actor) {
        actor.manage_HP(-6);
    }
}
