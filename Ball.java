public class Ball {
    private double x;
    private double y;
    private double dx = 3;
    private double dy = -3;
    private int radius;

    public Ball(double x, double y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void move() {
        x += dx;
        y += dy;
        if (x < 0 || x > BrickBreakerGame.WIDTH) {
            dx = -dx;
        }
        if (y < 0 || y > BrickBreakerGame.HEIGHT) {
            dy = -dy;
        }
    }

    public void reverseY() {
        dy = -dy;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public Bounds getBounds() {
        return new Bounds(x - radius, y - radius, 2 * radius, 2 * radius);
    }

}
