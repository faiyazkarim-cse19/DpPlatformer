package org.example;

public class BackgroundFactory {
    public Background chooseBackground(String path){
        if(path.equals("images/bg1.png")){
            return new NightBackground(path);
        }
        else if(path.equals("images/bg.png")){
            return new OriginalBackground(path);
        }
        return null;
    }
}
