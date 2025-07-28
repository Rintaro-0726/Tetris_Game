// ShapeL.java

class ShapeL extends Block {
    public ShapeL(int x, int y) {
        super(x, y, ColorConstants.BLOCK_COLOR_L);
        this.shapePatterns = new int[][][]{
            {{0, 0, 0, 0}, {1, 1, 1, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}},
            {{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 1, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {0, 0, 1, 0}, {1, 1, 1, 0}, {0, 0, 0, 0}},
            {{1, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}}
        };
    }
    @Override
    public int[][] getShape() { return shapePatterns[direction]; }
    @Override
    public Block cloneBlock() {
        ShapeL cloned = new ShapeL(this.x, this.y);
        cloned.setDirection(this.direction);
        return cloned;
    }
}
