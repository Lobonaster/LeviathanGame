package com.lebedev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @ For Pictures ONLY
 * @ 14.05.2024
 */

public class PictureClass extends Actor {
    private int x_cord, y_cord;
    private  int pic_width, pic_height;
    private Texture texture;
    public void get_assets(String texture_path,int x, int y, int width, int height){
        texture = new Texture(Gdx.files.internal("assets/Pictures/"+texture_path));
        x_cord = x;
        y_cord = y;
        pic_width = width;
        pic_height = height;
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(texture,x_cord,y_cord,pic_width,pic_height);
    }
}
