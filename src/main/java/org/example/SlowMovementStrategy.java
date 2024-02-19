package org.example;
import static org.example.Main.*;

public class SlowMovementStrategy implements MovementStrategy{
    public void movementSpeedSetter(){
        MOVE_SPEED = 1;
        toggle = 3;
    }
}
