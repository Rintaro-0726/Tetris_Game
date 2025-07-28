// Block.java
import processing.core.PApplet;

abstract class Block {
    protected int x;
    protected int y;
    protected int direction;
    protected String color;
    protected int[][][] shapePatterns;

    public Block(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.direction = 0;
    }

    public abstract int[][] getShape();

    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    public void rotate() {
        this.direction = (this.direction + 1) % shapePatterns.length;
    }

    public abstract Block cloneBlock();

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDirection() { return direction; }
    public String getColor() { return color; }
    public void setDirection(int direction) { this.direction = direction; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
