package org.example;
import static org.example.Main.*;

public class NormalMovementStrategy implements MovementStrategy{
    public void movementSpeedSetter(){
        MOVE_SPEED = 4;
        toggle = 2;
    }
}
