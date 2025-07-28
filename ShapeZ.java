class ShapeZ extends Block {
    public ShapeZ(int x, int y) {
        super(x, y, ColorConstants.BLOCK_COLOR_Z);
        this.shapePatterns = new int[][][]{
            {{0, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}},
            {{0, 1, 0, 0}, {1, 1, 0, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}},
            {{0, 0, 0, 0}, {1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}},
            {{0, 1, 0, 0}, {1, 1, 0, 0}, {1, 0, 0, 0}, {0, 0, 0, 0}}
        };
    }
    @Override
    public int[][] getShape() { return shapePatterns[direction]; }
    @Override
    public Block cloneBlock() {
        ShapeZ cloned = new ShapeZ(this.x, this.y);
        cloned.setDirection(this.direction);
        return cloned;
    }
}
