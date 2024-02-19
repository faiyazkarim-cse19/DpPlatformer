package org.example;
import processing.core.PImage;
import processing.core.PApplet;

public class Sprite {
    PApplet parent;
    PImage img;
    float center_x, center_y;
    float change_x, change_y;
    float w, h;

    public Sprite(PApplet p, String file_name, float scale, float x, float y){
        parent = p;
        img = parent.loadImage(file_name);
        w = img.width * scale;
        h = img.height * scale;
        center_x = x;
        center_y = y;
        change_x = 0;
        change_y = 0;
    }

    public Sprite(PApplet p, String file_name, float scale){
        this(p, file_name, scale, 0, 0);
    }

    public void display(){
        parent.image(img, center_x, center_y);
    }

    public void update(){
        center_x += change_x;
        center_y += change_y;
    }


}
