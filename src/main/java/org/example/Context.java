package org.example;

public class Context {
    public MovementStrategy movementStrategy;

    public Context(MovementStrategy strategy){
        this.movementStrategy = strategy;
    }

    public void changeSpeed(){
        movementStrategy.movementSpeedSetter();
    }
}
