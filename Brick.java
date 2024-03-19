public class Brick {
    private double x;
    private double y;
    private int width;
    private int height;
    private Color color;
    private boolean destroyed = false;

    public Brick(double x, double y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(GraphicsContext gc) {
        if (!destroyed) {
            gc.setFill(color);
            gc.fillRect(x, y, width, height);
        }
    }

    public Bounds getBounds() {
        return new Bounds(x, y, width, height);
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

}
