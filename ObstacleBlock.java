// ObstacleBlock.java

class ObstacleBlock extends Block {
    public ObstacleBlock(int x, int y) {
        super(x, y, ColorConstants.OBSTACLE_COLOR, true);
        this.shapePatterns = new int[][][]{
            {{0, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}
        };
    }
    @Override
    public int[][] getShape() { return shapePatterns[0]; } 
    @Override
    public Block cloneBlock() {
        return new ObstacleBlock(this.x, this.y);
    }
}
