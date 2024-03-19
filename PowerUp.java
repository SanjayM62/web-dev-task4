public class PowerUp {
    private double x;
    private double y;
    private PowerUpType type;

    public PowerUp(double x, double y, PowerUpType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(GraphicsContext gc) {
        switch (type) {
            case BIG_PADDLE:
                gc.setFill(Color.BLUE);
                break;
            case MULTIBALL:
                gc.setFill(Color.GREEN);
                break;
            case FAST_BALL:
                gc.setFill(Color.YELLOW);
                break;
        }
        gc.fillOval(x, y, 20, 20); // Draw a circle for power-up
    }

    public Bounds getBounds() {
        return new Bounds(x, y, 20, 20);
    }

}
