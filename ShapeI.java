// ShapeI.java

class ShapeI extends Block {
    public ShapeI(int x, int y) {
        super(x, y, ColorConstants.BLOCK_COLOR_I);
        this.shapePatterns = new int[][][]{
            {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}},
            {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 0, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}}
        };
    }
    @Override
    public int[][] getShape() { return shapePatterns[direction]; }
    @Override
    public Block cloneBlock() {
        ShapeI cloned = new ShapeI(this.x, this.y);
        cloned.setDirection(this.direction);
        return cloned;
    }
}
