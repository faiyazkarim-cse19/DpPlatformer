package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("org.example.Main");
    }

    final static float MOVE_SPEED = 4;
    final static float GRAVITY = 0.6f;
    final static float JUMP_SPEED = 14;
    final static float SPRITE_SCALE = 50.0f / 128;
    final static float SPRITE_SIZE = 50;

    final static float RIGHT_MARGIN = 400;
    final static float LEFT_MARGIN = 60;
    final static float VERTICAL_MARGIN = 40;
    final static int NEUTRAL_FACING = 0;
    final static int RIGHT_FACING = 1;
    final static int LEFT_FACING = 2;


    Sprite player;
    PImage snow, crate, red_brick, brown_brick, gold;
    ArrayList<Sprite> platforms;
    ArrayList<Sprite> coins;
    float view_x = 0;
    float view_y = 0;

    public void settings(){
        size(800, 600);
    }

    public void setup() {
        imageMode(CENTER);
        player = new Sprite(this, "images/player.png", 0.8f);
        player.center_x = 200;
        player.center_y = 50;
        platforms = new ArrayList<>();
        coins = new ArrayList<>();

        gold = loadImage("images/gold1.png");
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
                    case "5" -> {
                        Coin c = new Coin(this, gold, SPRITE_SCALE);
                        c.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        c.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        coins.add(c);
                    }
                }
            }
        }
    }

    public void resolvePlatformCollisions(Sprite s, ArrayList<Sprite> walls){
        s.change_y += GRAVITY;
        s.center_y += s.change_y;
        ArrayList<Sprite> col_list = checkCollisionList(s, walls);

        if(!col_list.isEmpty()){
            Sprite collided = col_list.getFirst();

            if(s.change_y > 0){
                s.setBottom(collided.getTop());
            }
            else if(s.change_y < 0){
                s.setTop(collided.getBottom());
            }
            s.change_y = 0;
        }


        s.center_x += s.change_x;
        col_list = checkCollisionList(s, walls);

        if(!col_list.isEmpty()){
            Sprite collided = col_list.getFirst();

            if(s.change_x > 0){
                s.setRight(collided.getLeft());
            }
            else if(s.change_x < 0){
                s.setLeft(collided.getRight());
            }
            s.change_x = 0;
        }


    }
    
    public boolean isOnPlatforms(Sprite s, ArrayList<Sprite> walls){
        s.center_y += 5;
        ArrayList<Sprite> col_list = checkCollisionList(s, walls);
        s.center_y -= 5;
        return  !col_list.isEmpty();
    }

    public boolean checkCollision(Sprite s1, Sprite s2){
        boolean noXOverlap = s1.getRight() <= s2.getLeft() || s1.getLeft() >= s2.getRight();
        boolean noYOverlap = s1.getBottom() <= s2.getTop() || s1.getTop() >= s2.getBottom();
        return !noXOverlap && !noYOverlap;
    }

    public ArrayList<Sprite> checkCollisionList(Sprite s, ArrayList<Sprite> list){
        ArrayList<Sprite> collision_list = new ArrayList<>();

        for(Sprite p: list){
            if(checkCollision(s, p)){
                collision_list.add(p);
            }
        }
        return collision_list;
    }

    public void draw() {
        background(255);
        scroll();
        player.display();
        resolvePlatformCollisions(player, platforms);
        for(Sprite s: platforms)
            s.display();

        for(Sprite c: coins){
            c.display();
            ((AnimatedSprite)c).updateAnimation();
        }
    }

    public void scroll(){
        float right_boundary = view_x + width - RIGHT_MARGIN;
        if(player.getRight() > right_boundary){
            view_x += player.getRight() - right_boundary;
        }

        float left_boundary = view_x + LEFT_MARGIN;
        if(player.getLeft() < left_boundary){
            view_x -= left_boundary - player.getLeft();
        }

        float top_boundary = view_y + VERTICAL_MARGIN;
        if(player.getTop() < top_boundary){
            view_y -= top_boundary - player.getTop();
        }

        float bottom_boundary = view_y + height - VERTICAL_MARGIN;
        if(player.getBottom() > bottom_boundary){
            view_y += player.getBottom() - bottom_boundary;
        }

        translate(-view_x, -view_y);
    }

    public void keyPressed(){
        if(keyCode == RIGHT){
            player.change_x = MOVE_SPEED;
        }
        else if(keyCode == LEFT){
            player.change_x = -MOVE_SPEED;
        }
        else if(keyCode == UP){
            player.change_y = -MOVE_SPEED;
        }
        else if(keyCode == DOWN){
            player.change_y = MOVE_SPEED;
        }
        else if(key == 'a' && isOnPlatforms(player, platforms)){
            player.change_y = -JUMP_SPEED;
        }
    }

    public void keyReleased(){
        if(keyCode == RIGHT){
            player.change_x = 0;
        }
        else if(keyCode == LEFT){
            player.change_x = 0;
        }
        else if(keyCode == UP){
            player.change_y = 0;
        }
        else if(keyCode == DOWN){
            player.change_y = 0;
        }
    }

}
