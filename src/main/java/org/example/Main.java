package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("org.example.Main");
    }

    final static float MOVE_SPEED = 5;
    final static float SPRITE_SCALE = 50.0f / 128;
    final static float SPRITE_SIZE = 50;

    Sprite p;
    PImage snow, crate, red_brick, brown_brick;
    ArrayList<Sprite> platforms;

    public void settings(){
        size(800, 600);
    }

    public void setup() {
        imageMode(CENTER);
        p = new Sprite(this, "images/player.png", 1.0f, 100, 300);
        p.change_x = 0;
        p.change_y = 0;
        platforms = new ArrayList<>();
        red_brick = loadImage("images/red_brick.png");
        brown_brick = loadImage("images/brown_brick.png");
        crate = loadImage("images/crate.png");
        snow = loadImage("images/snow.png");
        createPlatforms();
    }

    void createPlatforms(){
        String [] lines = loadStrings("map/map.csv");
        for(int row = 0; row < lines.length; row++){
            String[] values = split(lines[row], ",");
            for(int col = 0; col < values.length; col++){
                switch (values[col]) {
                    case "1" -> {
                        Sprite s = new Sprite(this, red_brick, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                    case "2" -> {
                        Sprite s = new Sprite(this, snow, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                    case "3" -> {
                        Sprite s = new Sprite(this, brown_brick, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                    case "4" -> {
                        Sprite s = new Sprite(this, crate, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                }
            }
        }
    }

    public void draw() {
        background(255);
        p.display();
        p.update();

        for(Sprite s: platforms){
            s.display();
        }
    }

    public void keyPressed(){
        if(keyCode == RIGHT){
            p.change_x = MOVE_SPEED;
        }
        else if(keyCode == LEFT){
            p.change_x = -MOVE_SPEED;
        }
        else if(keyCode == UP){
            p.change_y = -MOVE_SPEED;
        }
        else if(keyCode == DOWN){
            p.change_y = MOVE_SPEED;
        }
    }

    public void keyReleased(){
        if(keyCode == RIGHT){
            p.change_x = 0;
        }
        else if(keyCode == LEFT){
            p.change_x = 0;
        }
        else if(keyCode == UP){
            p.change_y = 0;
        }
        else if(keyCode == DOWN){
            p.change_y = 0;
        }
    }

}
