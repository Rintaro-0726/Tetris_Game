// ShapeO.java

class ShapeO extends Block {
    public ShapeO(int x, int y) {
        super(x, y, ColorConstants.BLOCK_COLOR_O);
        this.shapePatterns = new int[][][]{
            {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}}
        };
    }
    @Override
    public int[][] getShape() { return shapePatterns[direction]; }
    @Override
    public Block cloneBlock() {
        ShapeO cloned = new ShapeO(this.x, this.y);
        cloned.setDirection(this.direction);
        return cloned;
    }
}
