package org.example;
import processing.core.PImage;
import processing.core.PApplet;

public class Sprite {
    PApplet parent;
    PImage image;
    float center_x, center_y;
    float change_x, change_y;
    float w, h;

    public Sprite(PApplet p, String file_name, float scale, float x, float y){
        parent = p;
        image = parent.loadImage(file_name);
        w = image.width * scale;
        h = image.height * scale;
        center_x = x;
        center_y = y;
        change_x = 0;
        change_y = 0;
    }

    public Sprite(PApplet p, String file_name, float scale){
        this(p, file_name, scale, 0, 0);
    }

    public Sprite(PApplet p, PImage img, float scale){
        parent = p;
        image = img;
        w = image.width * scale;
        h = image.height * scale;
        center_x = 0;
        center_y = 0;
        change_x = 0;
        change_y = 0;
    }

    public void display(){
        parent.image(image, center_x, center_y, w, h);
    }

    public void update(){
        center_x += change_x;
        center_y += change_y;
    }

    public void setLeft(float left){
        center_x = left + w/2;
    }
    public float getLeft(){
        return center_x - w/2;
    }
    public void setRight(float right){
        center_x = right - w/2;
    }
    public float getRight(){
        return center_x + w/2;
    }
    public void setTop(float top){
        center_y = top + h/2;
    }
    public float getTop(){
        return center_y - h/2;
    }
    public void setBottom(float bottom){
        center_y = bottom - h/2;
    }
    public float getBottom(){
        return center_y + h/2;
    }
}
