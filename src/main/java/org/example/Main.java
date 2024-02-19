package org.example;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("org.example.Main");
    }

    static float MOVE_SPEED = 4;
    final static float GRAVITY = 0.6f;
    final static float JUMP_SPEED = 14;
    final static float SPRITE_SCALE = 50.0f / 128;
    final static float SPRITE_SIZE = 50;

    final static float RIGHT_MARGIN = 400;
    final static float LEFT_MARGIN = 60;
    final static float VERTICAL_MARGIN = 40;
    final static int NEUTRAL_FACING = 0;
    final static int RIGHT_FACING = 1;
    final static int LEFT_FACING = 2;
    final static float WIDTH = SPRITE_SIZE * 16;
    final static float HEIGHT = SPRITE_SIZE * 12;
    final static float GROUND_LEVEL = HEIGHT - SPRITE_SIZE;


    Player player;
    static PImage grass, crate, red_brick, brown_brick, gold, kunai, player_image, background_image;
    static ArrayList<Sprite> platforms;
    static ArrayList<Sprite> coins;
    static Enemy enemy;
    int num_coins;
    static int toggle, bg_toggle;
    float view_x;
    float view_y;
    boolean isGameOver;

    public void settings(){
        size(800, 600);
        background_image = loadImage("images/bg.png");
        background_image.resize(800, 600);
    }

    public void setup() {
        imageMode(CENTER);
        player_image = loadImage("images/player.png");
        player = new Player(this, player_image, 0.8f);
        player.setBottom(GROUND_LEVEL);
        player.center_x = 100;
        num_coins = 0;
        toggle = 1;
        bg_toggle = 1;
        view_x = 0.0f;
        view_y = 0.0f;
        isGameOver = false;
        platforms = new ArrayList<>();
        coins = new ArrayList<>();

        kunai = loadImage("images/kunai_right1.png");
        gold = loadImage("images/gold1.png");
        red_brick = loadImage("images/red_brick.png");
        brown_brick = loadImage("images/brown_brick.png");
        crate = loadImage("images/crate.png");
        grass = loadImage("images/grass.png");
        createPlatforms();
    }

    public void draw() {
        background(background_image);
        scroll();
        player.display();
        player.updateAnimation();
        resolvePlatformCollisions(player, platforms);
        for(Sprite s: platforms)
            s.display();

        for(Sprite c: coins){
            c.display();
            ((AnimatedSprite)c).updateAnimation();
        }

        enemy.display();
        enemy.update();
        enemy.updateAnimation();

        fill(0, 0, 0);
        textSize(32);
        text("Coin: " + num_coins, view_x + 50, view_y + 50);
        text("Lives: " + player.lives, view_x + 50, view_y + 100);

        collectCoins();
        checkDeath();

        if(isGameOver){
            fill(0, 0, 0);
            text("Game over!", view_x + (float)width/2 - 100, view_y + (float)height/2);
            if(player.lives == 0)
                text("You are dead!", view_x + (float)width/2 - 100, view_y + (float)height/2 + 50);
            else
                text("You win!", view_x + (float)width/2 - 100, view_y + (float)height/2 + 50);
            text("Press Enter to restart!", view_x + (float)width/2 - 100, view_y + (float)height/2 + 100);
        }
    }

    void createPlatforms(){
        String [] lines = loadStrings("map/map.csv");
        for(int row = 0; row < lines.length; row++){
            String[] values = split(lines[row], ",");
            for(int col = 0; col < values.length; col++){
                switch (values[col]) {
                    case "1" -> {
                        Sprite s = new Sprite(this, red_brick, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                    case "2" -> {
                        Sprite s = new Sprite(this, grass, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                    case "3" -> {
                        Sprite s = new Sprite(this, brown_brick, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                    case "4" -> {
                        Sprite s = new Sprite(this, crate, SPRITE_SCALE);
                        s.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        s.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        platforms.add(s);
                    }
                    case "5" -> {
                        Coin c = new Coin(this, gold, SPRITE_SCALE);
                        c.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        c.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                        coins.add(c);
                    }
                    case "6" -> {
                        float bLeft = col * SPRITE_SIZE;
                        float bRight = bLeft + 4 * SPRITE_SIZE;
                        enemy = new Enemy(this, kunai, 50/72.0f, bLeft, bRight);
                        enemy.center_x = SPRITE_SIZE / 2 + col * SPRITE_SIZE;
                        enemy.center_y = SPRITE_SIZE / 2 + row * SPRITE_SIZE;
                    }
                }
            }
        }
    }

    public void resolvePlatformCollisions(Sprite s, ArrayList<Sprite> walls){
        s.change_y += GRAVITY;
        s.center_y += s.change_y;
        ArrayList<Sprite> col_list = checkCollisionList(s, walls);

        if(!col_list.isEmpty()){
            Sprite collided = col_list.getFirst();

            if(s.change_y > 0){
                s.setBottom(collided.getTop());
            }
            else if(s.change_y < 0){
                s.setTop(collided.getBottom());
            }
            s.change_y = 0;
        }


        s.center_x += s.change_x;
        col_list = checkCollisionList(s, walls);

        if(!col_list.isEmpty()){
            Sprite collided = col_list.getFirst();

            if(s.change_x > 0){
                s.setRight(collided.getLeft());
            }
            else if(s.change_x < 0){
                s.setLeft(collided.getRight());
            }
            s.change_x = 0;
        }

    }
    
    public static boolean isOnPlatforms(Sprite s, ArrayList<Sprite> walls){
        s.center_y += 5;
        ArrayList<Sprite> col_list = checkCollisionList(s, walls);
        s.center_y -= 5;
        return  !col_list.isEmpty();
    }

    public static boolean checkCollision(Sprite s1, Sprite s2){
        boolean noXOverlap = s1.getRight() <= s2.getLeft() || s1.getLeft() >= s2.getRight();
        boolean noYOverlap = s1.getBottom() <= s2.getTop() || s1.getTop() >= s2.getBottom();
        return !noXOverlap && !noYOverlap;
    }

    public static ArrayList<Sprite> checkCollisionList(Sprite s, ArrayList<Sprite> list){
        ArrayList<Sprite> collision_list = new ArrayList<>();

        for(Sprite p: list){
            if(checkCollision(s, p)){
                collision_list.add(p);
            }
        }
        return collision_list;
    }

    public void collectCoins(){
        ArrayList<Sprite> coin_list = checkCollisionList(player, coins);
        if(!coin_list.isEmpty()){
            for(Sprite coin: coin_list){
                num_coins++;
                coins.remove(coin);
            }
        }

        if(coins.isEmpty()){
            isGameOver = true;
        }
    }

    public void checkDeath() {
        boolean collision_death = checkCollision(player, enemy);
        boolean cliff_death = player.getBottom() > GROUND_LEVEL;

        if (collision_death || cliff_death) {
            player.lives -= 1;
            if (player.lives <= 0) {
                player.lives = 0;
                isGameOver = true;
            }
            else
            {
                player.center_x = 100;
                player.setBottom(GROUND_LEVEL);
            }
        }
    }


    public void scroll(){
        float right_boundary = view_x + width - RIGHT_MARGIN;
        if(player.getRight() > right_boundary){
            view_x += player.getRight() - right_boundary;
        }

        float left_boundary = view_x + LEFT_MARGIN;
        if(player.getLeft() < left_boundary){
            view_x -= left_boundary - player.getLeft();
        }

        float top_boundary = view_y + VERTICAL_MARGIN;
        if(player.getTop() < top_boundary){
            view_y -= top_boundary - player.getTop();
        }

        float bottom_boundary = view_y + height - VERTICAL_MARGIN;
        if(player.getBottom() > bottom_boundary){
            view_y += player.getBottom() - bottom_boundary;
        }

        translate(-view_x, -view_y);
    }

    public void keyPressed(){
        if(!isGameOver){
            if(keyCode == RIGHT){
                player.change_x = MOVE_SPEED;
            }
            else if(keyCode == LEFT){
                player.change_x = -MOVE_SPEED;
            }
            else if(keyCode == UP){
                player.change_y = -MOVE_SPEED;
            }
            else if(keyCode == DOWN){
                player.change_y = MOVE_SPEED;
            }
            else if(key == 'a' && isOnPlatforms(player, platforms)){
                player.change_y = -JUMP_SPEED;
            }
            else if(key == 't'){
                if(toggle == 1){
                    Context context = new Context(new NormalMovementStrategy());
                    context.changeSpeed();
                }
                else if(toggle == 2){
                    Context context = new Context(new SlowMovementStrategy());
                    context.changeSpeed();
                    toggle = 3;
                }
                else if(toggle == 3){
                    Context context = new Context(new FastMovementStrategy());
                    context.changeSpeed();
                    toggle = 1;
                }
            }
            else if(key == 'b'){
                BackgroundFactory bg_factory = new BackgroundFactory();
                if(bg_toggle == 1){
                    Background bg = bg_factory.chooseBackground("images/bg1.png");
                    bg.changeBackground(this);
                }
               else if(bg_toggle == 2){
                    Background bg = bg_factory.chooseBackground("images/bg.png");
                    bg.changeBackground(this);
                    System.out.println(bg_toggle);
                }
            }
        }

        if(isGameOver && keyCode == ENTER){
            settings();
            setup();
        }
    }

    public void keyReleased(){
        if(keyCode == RIGHT){
            player.change_x = 0;
        }
        else if(keyCode == LEFT){
            player.change_x = 0;
        }
        else if(keyCode == UP){
            player.change_y = 0;
        }
        else if(keyCode == DOWN){
            player.change_y = 0;
        }
    }

}
