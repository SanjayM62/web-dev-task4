import java.applet.AudioClip;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickBreakerGame extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_RADIUS = 10;
    private static final int BRICK_WIDTH = 80;
    private static final int BRICK_HEIGHT = 20;
    private static final int BRICK_ROWS = 6;
    private static final int BRICK_COLS = 10;
    private static final int BRICK_GAP = 2;
    private static final int LEVEL_UP_SCORES = 200;
    private static final int INITIAL_LIVES = 3;

    private Canvas canvas;
    private GraphicsContext gc;
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score = 0;
    private int lives = INITIAL_LIVES;
    private int currentLevel = 1;
    private boolean paused = false;

    private Timeline gameLoop;

    private AudioClip brickHitSound;
    private AudioClip paddleHitSound;
    private AudioClip gameOverSound;
    private AudioClip levelUpSound;

    public void start(Stage primaryStage) {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        initializeGame();
        StackPane root = new stackpane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                paddle.moveLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                paddle.moveRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                if (!paused) {
                    pauseGame();
                } else {
                    resumeGame();
                }
            }
        });
        primaryStage.setTitle("BrickBreakerGame");
        primaryStage.setScene(scene);
        primaryStage.show();
        startGameLoop();
        brickHitSound = new AudioClip(getClass().getResource("brick_hit.wav").toString());
        paddleHitSound = new AudioClip(getClass().getResource("paddle_hit.wav").toString());
        gameOverSound = new AudioClip(getClass().getResource("game_over.wav").toString());
        levelUpSound = new AudioClip(getClass().getResource("level_up.wav").toString());
    }

    private void initializeGame() {
        paddle = new Paddle(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT - 10, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(WIDTH / 2, HEIGHT / 2, BALL_RADIUS);
        bricks = createBricks();
        powerUps = new ArrayList<>();

    }

    private List<Brick>createBricks(){
        List<Brick> bricks=new ArrayList<>();
        int startY=50;
        color[] colors = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE};
        Random random= new Random;

 for (int row = 0; row < BRICK_ROWS; row++) {
            int startX = (WIDTH - (BRICK_COLS * (BRICK_WIDTH + BRICK_GAP))) / 2;
            for (int col = 0; col < BRICK_COLS; col++) {
                Brick brick = new Brick(startX, startY, BRICK_WIDTH, BRICK_HEIGHT, colors[row]);
                bricks.add(brick);
                startX += BRICK_WIDTH + BRICK_GAP;
    }       
    startY+= BRICK_HEIGHT+BRICK_GAP;
}
return bricks;
    }

    private void startGameLoop(){
        gameLoop=new Timeline(new KeyFrame(Duration.ofMillis(10),e<-{
            update();
            render();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();


    }

    private void update() {
        if (!paused) {
            ball.move();
            checkPaddleCollision();
            checkBrickCollision();
            checkPowerUpCollision();
            checkGameOver();
        }
    }

    private void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        paddle.draw(gc);
        ball.draw(gc);
        for (Brick brick : bricks) {
            brick.draw(gc);
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(gc);
        }
        gc.setFillFill(Color.BLACK);
        gc.setFont(Font.font(20));
        gc.fillText("Score: " + score, 10, 20);
        gc.fillText("Lives: " + lives, WIDTH - 100, 20);
        gc.fillText("Level: " + currentLevel, 10, HEIGHT - 20);
    }

    private void checkPaddleCollision() {
        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.reverseY();
            paddleHitSound.play();
        }
    }

    private void checkBrickCollision() {
        for (Brick brick : bricks) {
            if (ball.getBounds().intersects(brick.getBounds()) && !brick.isDestroyed()) {
                brick.setDestroyed(true);
                ball.reverseY();
                score += 10;
                if (score % LEVEL_UP_SCORE == 0) {
                    currentLevel++;
                    levelUpSound.play();
                    bricks = createBricks();
                } else {
                    brickHitSound.play();
                }
                // Add chance to spawn power-ups
                if (random.nextInt(100) < 10) { // 10% chance
                    PowerUpType powerUpType = PowerUpType.values()[random.nextInt(PowerUpType.values().length)];
                    PowerUp powerUp = new PowerUp(brick.getX(), brick.getY(), powerUpType);
                    powerUps.add(powerUp);
                }
                break;
            }
        }
    }

    private void checkPowerUpCollision() {
        for (PowerUp powerUp : new ArrayList<>(powerUps)) {
            if (paddle.getBounds().intersects(powerUp.getBounds())) {
                applyPowerUp(powerUp.getType());
                powerUps.remove(powerUp);
            }
        }

    }

    private void applyPowerUp(PowerUpType type) {
        switch (type) {
            case BIG_PADDLE:
                paddle.setWidth(paddle.getWidth() + 20);
                break;
            case MULTIBALL:
                // Implement logic to spawn additional balls
                break;
            case FAST_BALL:
                ball.setSpeed(ball.getSpeed() + 1);
                break;
        }

    }

    private void checkGameOver(){
        if (ball.getY() >= HEIGHT) {
            lives--;
            if (lives <= 0) {
                gameOver();
            } else {
                ball.reset(WIDTH / 2, HEIGHT / 2);
                pauseGame();
            }

    }





}
