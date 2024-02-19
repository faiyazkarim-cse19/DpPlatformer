package org.example;
import processing.core.PApplet;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("org.example.Main");
    }

    final static float MOVE_SPEED = 5;
    Sprite p;

    public void settings() {
        size(800, 600);
        p = new Sprite(this, "images/ball.png", 1, 100, 300);
        p.change_x = 0;
        p.change_y = 0;
    }

    public void draw() {
        background(255);
        p.display();
        p.update();
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
