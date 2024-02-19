package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class Coin extends AnimatedSprite{
    public Coin(PApplet p, PImage img, float scale){
        super(p, img, scale);
        standNeutral = new PImage[4];
        standNeutral[0] = p.loadImage("images/gold1.png");
        standNeutral[1] = p.loadImage("images/gold2.png");
        standNeutral[2] = p.loadImage("images/gold3.png");
        standNeutral[3] = p.loadImage("images/gold4.png");
        currentImages = standNeutral;
    }
}
