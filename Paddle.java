public class Paddle {
    private double x;
    private double y;
    private int width;
    private int height;

    public Paddle(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveLeft() {
        x -= 5;
        if (x < 0) {
            x = 0;
        }
    }

    public void moveRight() {
        x += 5;
        if (x + width > BrickBreakerGame.WIDTH) {
            x = BrickBreakerGame.WIDTH - width;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(x, y, width, height);
    }

    public Bounds getBounds() {
        return new Bounds(x, y, width, height);
    }
}
