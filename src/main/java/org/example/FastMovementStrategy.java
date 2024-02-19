package org.example;
import static org.example.Main.*;

public class FastMovementStrategy implements MovementStrategy{
    public void movementSpeedSetter(){
        MOVE_SPEED = 10;
        toggle = 1;
    }
}
