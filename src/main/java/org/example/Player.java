package org.example;
import processing.core.PApplet;
import processing.core.PImage;
import static org.example.Main.*;


public class Player extends AnimatedSprite{
    int lives;
    boolean onPlatform, inPlace;
    PImage[] standLeft;
    PImage[] standRight;
    PImage[] jumpRight;
    PImage[] jumpLeft;
    public Player(PApplet p, PImage img, float scale){
        super(p, img, scale);
        lives = 3;
        direction = RIGHT_FACING;
        onPlatform = false;
        inPlace = true;
        standLeft = new PImage[1];
        standLeft[0] = p.loadImage("images/player_stand_left.png");
        standRight = new PImage[1];
        standRight[0] = p.loadImage("images/player_stand_right.png");
        moveLeft = new PImage[2];
        moveLeft[0] = p.loadImage("images/player_walk_left1.png");
        moveLeft[1] = p.loadImage("images/player_walk_left2.png");
        moveRight = new PImage[2];
        moveRight[0] = p.loadImage("images/player_walk_right1.png");
        moveRight[1] = p.loadImage("images/player_walk_right2.png");
        jumpLeft = new PImage[1];
        jumpLeft[0] = p.loadImage("images/player_jump_left.png");
        jumpRight = new PImage[1];
        jumpRight[0] = p.loadImage("images/player_jump_right.png");
        currentImages = standRight;
    }
    @Override
    public void updateAnimation(){
        onPlatform = isOnPlatforms(this, platforms);
        inPlace = change_x == 0 && change_y == 0;
        super.updateAnimation();
    }
    @Override
    public void selectDirection(){
        if(change_x > 0)
            direction = RIGHT_FACING;
        else if(change_x < 0)
            direction = LEFT_FACING;
    }
    @Override
    public void selectCurrentImages(){
        if(direction == RIGHT_FACING){
            if(inPlace){
                currentImages = standRight;
            }
            else if(!onPlatform){
                currentImages = jumpRight;
            }
            else
                currentImages = moveRight;
        }

        if(direction == LEFT_FACING){
            if(inPlace){
                currentImages = standLeft;
            }
            else if(!onPlatform){
                currentImages = jumpLeft;
            }
            else
                currentImages = moveLeft;
        }
    }
}
