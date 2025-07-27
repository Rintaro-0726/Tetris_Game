// Playfield.java
import java.util.ArrayList;
import java.util.Random;

class Playfield {
    private int[][] grid;
    private int width;
    private int height;
    public static final int EMPTY_CELL = 100; 
    public static final int WALL = 99;       
    public static final int OBSTACLE_CELL = -1; 
    public static final int WIDTH = 12; 
    public static final int HEIGHT = 22; 

    public Playfield(int width, int height) {
        this.width = width;
        this.height = height;
        initializeGrid();
    }

    public void initializeGrid() {
        grid = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0 || j == width - 1 || i == height - 1) { 
                    grid[i][j] = WALL;
                } else {
                    grid[i][j] = EMPTY_CELL;
                }
            }
        }
    }

    public int getCellState(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return WALL; 
        }
        return grid[y][x];
    }

    public void setCellState(int x, int y, int type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = type;
        }
    }

    public void addBlockToGrid(Block block) {
        int[][] shape = block.getShape();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] == 1) {
                    int typeValue = EMPTY_CELL;
                    if (block.isObstacle()) {
                        typeValue = OBSTACLE_CELL;
                    } else if (block instanceof ShapeT) {
                        typeValue = 0;
                    } else if (block instanceof ShapeL) {
                        typeValue = 1;
                    } else if (block instanceof ShapeZ) {
                        typeValue = 2;
                    } else if (block instanceof ShapeS) {
                        typeValue = 3;
                    } else if (block instanceof ShapeJ) {
                        typeValue = 4;
                    } else if (block instanceof ShapeI) {
                        typeValue = 5;
                    } else if (block instanceof ShapeO) {
                        typeValue = 6;
                    }
                    setCellState(block.getX() + j, block.getY() + i, typeValue);
                }
            }
        }
    }

    public ArrayList<Integer> checkLineCompletion() {
        ArrayList<Integer> completedLines = new ArrayList<>();
        for (int y = 0; y < height - 1; y++) { 
            boolean lineFull = true;
            for (int x = 1; x < width - 1; x++) { 
                if (grid[y][x] == EMPTY_CELL) {
                    lineFull = false;
                }
            }
            if (lineFull) {
                completedLines.add(y);
            }
        }
        return completedLines;
    }

    public void clearLines(ArrayList<Integer> lineIndices) {
        for (int clearedY : lineIndices) {
            for (int y = clearedY; y > 0; y--) { 
                for (int x = 1; x < width - 1; x++) { 
                    grid[y][x] = grid[y - 1][x];
                }
            }
            for (int x = 1; x < width - 1; x++) {
                grid[0][x] = EMPTY_CELL;
            }
        }
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isOccupied(int x, int y) {
        return getCellState(x, y) != EMPTY_CELL;
    }

    public boolean placeObstacleBlock(int numBlocks, ArrayList<int[]> currentBlockCells) {
        Random random = new Random();
        ArrayList<int[]> emptyCells = new ArrayList<>();
        for (int y = 1; y < height - 1; y++) { 
            for (int x = 1; x < width - 1; x++) { 
                if (grid[y][x] == EMPTY_CELL) {
                    boolean isCurrentBlockCell = false;
                    for (int[] cell : currentBlockCells) {
                        if (cell[0] == x && cell[1] == y) {
                            isCurrentBlockCell = true;
                        }
                    }
                    if (!isCurrentBlockCell) {
                        emptyCells.add(new int[]{x, y});
                    }
                }
            }
        }

        if (emptyCells.size() < numBlocks) {
            return false;
        }

        for (int k = 0; k < numBlocks; k++) {
            if (emptyCells.isEmpty()) {
                return false; 
            }
            int randomIndex = random.nextInt(emptyCells.size());
            int[] chosenCell = emptyCells.get(randomIndex);
            grid[chosenCell[1]][chosenCell[0]] = OBSTACLE_CELL; 
            emptyCells.remove(randomIndex); 
        }
        return true;
    }

    public void printGrid() {
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int[][] getGrid() { return grid; } 
}
