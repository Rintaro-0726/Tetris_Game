// ShapeS.java

class ShapeS extends Block {
    public ShapeS(int x, int y) {
        super(x, y, ColorConstants.BLOCK_COLOR_S, false);
        this.shapePatterns = new int[][][]{
            {{0, 0, 0, 0}, {0, 1, 1, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}},
            {{1, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0, 1, 1, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}},
            {{1, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}}
        };
    }
    @Override
    public int[][] getShape() { return shapePatterns[direction]; }
    @Override
    public Block cloneBlock() {
        ShapeS cloned = new ShapeS(this.x, this.y);
        cloned.setDirection(this.direction);
        return cloned;
    }
}
