package org.example;
import static org.example.Main.*;
import processing.core.PApplet;


public class OriginalBackground implements Background{
    public String path;
    public OriginalBackground(String pth){
        this.path = pth;
    }
    public void changeBackground(PApplet p){
        bg_toggle = 1;
        background_image = p.loadImage(path);
        background_image.resize(800, 600);
    }
}
