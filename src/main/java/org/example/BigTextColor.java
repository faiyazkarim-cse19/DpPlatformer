package org.example;
import processing.core.PApplet;

public class BigTextColor extends ColorDecorator{
    public BigTextColor(Color decorated_color){
        super(decorated_color);
    }

    @Override
    public void changeColor(PApplet p) {
        decorated_color.changeColor(p);
        bigText(p);
    }

    public void bigText(PApplet p){
        p.textSize(42);
    }
}
