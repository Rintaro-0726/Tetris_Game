// ShapeJ.java

class ShapeJ extends Block {
    public ShapeJ(int x, int y) {
        super(x, y, ColorConstants.BLOCK_COLOR_J);
        this.shapePatterns = new int[][][]{
            {{0, 0, 0, 0}, {1, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}},
            {{1, 1, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {1, 0, 0, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}},
            {{0, 1, 0, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}}
        };
    }
    @Override
    public int[][] getShape() { return shapePatterns[direction]; }
    @Override
    public Block cloneBlock() {
        ShapeJ cloned = new ShapeJ(this.x, this.y);
        cloned.setDirection(this.direction);
        return cloned;
    }
}
