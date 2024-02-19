package org.example;
import processing.core.PApplet;

public class ColorDecorator implements Color{
    public Color decorated_color;

    public ColorDecorator(Color color){
        this.decorated_color = color;
    }

    public void changeColor(PApplet p){
        this.decorated_color.changeColor(p);
    }
}


