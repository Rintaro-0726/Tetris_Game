import processing.core.PApplet;
import processing.core.PConstants;
import java.util.Queue;

class Renderer {
    private PApplet p;
    private int cellSize;

    private final int MAIN_AREA_OFFSET_X = 5;
    private final int MAIN_AREA_OFFSET_Y = 5;
    private final int MAIN_AREA_WIDTH = Playfield.WIDTH * 20;
    private final int MAIN_AREA_HEIGHT = Playfield.HEIGHT * 20;

    private final int SUB_AREA_OFFSET_X = 280;
    private final int SUB_AREA_OFFSET_Y = 5;
    private final int SUB_AREA_WIDTH = 120;
    private final int SUB_AREA_HEIGHT = 440;

    private final int NEXT_BLOCK_AREA_OFFSET_Y_IN_SUB = 36;
    private final int NEXT_BLOCK_AREA_WIDTH = 120;
    private final int NEXT_BLOCK_AREA_HEIGHT = 260;

    private final int SCORE_TEXT_OFFSET_Y_IN_SUB = 310;
    private final int SCORE_NUMBER_OFFSET_X_IN_SUB = 10;

    private final int START_BUTTON_OFFSET_Y_IN_SUB = 390;
    private final int START_BUTTON_WIDTH = 120;
    private final int START_BUTTON_HEIGHT = 50;

    public Renderer(PApplet pApplet, int cellSize) {
        this.p = pApplet;
        this.cellSize = cellSize;
    }

    private int hexToPColor(String hexColor) {
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
        }
        return p.unhex("FF" + hexColor);
    }

    public void drawBackground() {
        p.background(0);

        p.fill(hexToPColor(ColorConstants.BLOCK_BORDER_COLOR));
        p.noStroke();
        p.rect(MAIN_AREA_OFFSET_X, MAIN_AREA_OFFSET_Y, MAIN_AREA_WIDTH, MAIN_AREA_HEIGHT);

        p.fill(hexToPColor(ColorConstants.BLOCK_BORDER_COLOR));
        p.noStroke();
        p.rect(SUB_AREA_OFFSET_X, SUB_AREA_OFFSET_Y + NEXT_BLOCK_AREA_OFFSET_Y_IN_SUB, NEXT_BLOCK_AREA_WIDTH, NEXT_BLOCK_AREA_HEIGHT);

        p.stroke(hexToPColor(ColorConstants.BRICK_STROKE_COLOR));
        p.strokeWeight(2);
        p.noFill();
        p.rect(MAIN_AREA_OFFSET_X, MAIN_AREA_OFFSET_Y, MAIN_AREA_WIDTH, MAIN_AREA_HEIGHT);
        p.rect(SUB_AREA_OFFSET_X, SUB_AREA_OFFSET_Y, SUB_AREA_WIDTH, SUB_AREA_HEIGHT);

        p.fill(255);
        p.textSize(30);
        p.textAlign(PConstants.LEFT, PConstants.TOP);
        p.text("IOS PUZZLE", MAIN_AREA_OFFSET_X, MAIN_AREA_OFFSET_Y - 5 - 40);
    }

    public void drawBlock(Block block) {
        p.rectMode(PConstants.CORNER);
        p.strokeWeight(0.5f);
        p.stroke(hexToPColor(ColorConstants.BLOCK_BORDER_COLOR));

        p.fill(hexToPColor(block.getColor()));

        int[][] shape = block.getShape();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] == 1) {
                    float xPos = (block.getX() + j) * cellSize + MAIN_AREA_OFFSET_X;
                    float yPos = (block.getY() + i) * cellSize + MAIN_AREA_OFFSET_Y;
                    p.rect(xPos, yPos, cellSize, cellSize);
                }
            }
        }
    }

    public void clearBlock(Block block) {
        p.fill(hexToPColor(ColorConstants.BLOCK_BORDER_COLOR));
        p.noStroke();

        int[][] shape = block.getShape();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] == 1) {
                    float xPos = (block.getX() + j) * cellSize + MAIN_AREA_OFFSET_X;
                    float yPos = (block.getY() + i) * cellSize + MAIN_AREA_OFFSET_Y;
                    p.rect(xPos, yPos, cellSize, cellSize);
                }
            }
        }
    }

    public void drawPlayfield(Playfield playfield) {
        p.rectMode(PConstants.CORNER);
        p.strokeWeight(0.5f);

        int[][] grid = playfield.getGrid();

        for (int y = 0; y < playfield.getHeight(); y++) {
            for (int x = 0; x < playfield.getWidth(); x++) {
                int cellType = grid[y][x];

                float xPos = x * cellSize + MAIN_AREA_OFFSET_X;
                float yPos = y * cellSize + MAIN_AREA_OFFSET_Y;

                if (cellType == Playfield.EMPTY_CELL) {
                    p.fill(hexToPColor(ColorConstants.BLOCK_BORDER_COLOR));
                    p.noStroke();
                    p.rect(xPos, yPos, cellSize, cellSize);
                } else if (cellType == Playfield.WALL) {
                    p.fill(hexToPColor(ColorConstants.BRICK_COLOR));
                    p.stroke(hexToPColor(ColorConstants.BRICK_STROKE_COLOR));
                    p.rect(xPos, yPos, cellSize, cellSize);
                } else {
                    // Obstacle判定を削除し、障害物色の描画はしない
                    p.fill(hexToPColor(ColorConstants.BLOCK_COLORS[cellType]));
                    p.stroke(hexToPColor(ColorConstants.BRICK_STROKE_COLOR));
                    p.rect(xPos, yPos, cellSize, cellSize);
                }
            }
        }
    }

    public void drawNextBlocks(Queue<Block> nextBlocksQueue) {
        p.fill(hexToPColor(ColorConstants.BLOCK_BORDER_COLOR));
        p.noStroke();
        p.rect(SUB_AREA_OFFSET_X, SUB_AREA_OFFSET_Y + NEXT_BLOCK_AREA_OFFSET_Y_IN_SUB, NEXT_BLOCK_AREA_WIDTH, NEXT_BLOCK_AREA_HEIGHT);

        int blockNum = 0;
        for (Block block : nextBlocksQueue) {
            p.rectMode(PConstants.CORNER);
            p.strokeWeight(0.5f);
            p.stroke(hexToPColor(ColorConstants.BLOCK_BORDER_COLOR));
            p.fill(hexToPColor(block.getColor()));

            int[][] shape = block.getShape();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shape[i][j] == 1) {
                        int baseBlockPointX = 1;
                        int baseBlockPointY;
                        if (blockNum == 0) baseBlockPointY = 0;
                        else if (blockNum == 1) baseBlockPointY = 3;
                        else if (blockNum == 2) baseBlockPointY = 6;
                        else baseBlockPointY = 9;

                        float xPos = SUB_AREA_OFFSET_X + (baseBlockPointX + j) * cellSize;
                        float yPos = SUB_AREA_OFFSET_Y + NEXT_BLOCK_AREA_OFFSET_Y_IN_SUB + (baseBlockPointY + i) * cellSize;
                        p.rect(xPos, yPos, cellSize, cellSize);
                    }
                }
            }
            blockNum++;
        }

        p.fill(255);
        p.textSize(20);
        p.textAlign(PConstants.LEFT, PConstants.TOP);
        p.text("【Next】", SUB_AREA_OFFSET_X + 16, SUB_AREA_OFFSET_Y + 5);
    }

    public void clearAll() {
        p.background(0);
        drawBackground();

        p.fill(255);
        p.textSize(30);
        p.textAlign(PConstants.LEFT, PConstants.TOP);
        p.text("IOS PUZZLE", MAIN_AREA_OFFSET_X, MAIN_AREA_OFFSET_Y - 5 - 40);

        p.textSize(20);
        p.textAlign(PConstants.LEFT, PConstants.TOP);
        p.text("【Score】", SUB_AREA_OFFSET_X + 10, SUB_AREA_OFFSET_Y + SCORE_TEXT_OFFSET_Y_IN_SUB);

        p.fill(hexToPColor("#FA8072"));
        p.rect(SUB_AREA_OFFSET_X, SUB_AREA_OFFSET_Y + START_BUTTON_OFFSET_Y_IN_SUB, START_BUTTON_WIDTH, START_BUTTON_HEIGHT, 10);
        p.fill(0);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.textSize(24);
        p.text("New Game", SUB_AREA_OFFSET_X + START_BUTTON_WIDTH / 2, SUB_AREA_OFFSET_Y + START_BUTTON_OFFSET_Y_IN_SUB + START_BUTTON_HEIGHT / 2);
        p.textAlign(PConstants.LEFT, PConstants.BASELINE);
    }

    public void playObstacleEffect() {
        // 障害物効果を削除したため空にしました
    }

    public void showGameOverMessage() {
        p.fill(255, 0, 0);
        p.textSize(48);
        p.textAlign(PConstants.CENTER, PConstants.CENTER);
        p.text("GAME OVER", p.width / 2, p.height / 2);
        p.textAlign(PConstants.LEFT, PConstants.BASELINE);
    }

    public void updateScoreDisplay(int score) {
        p.fill(hexToPColor("#2e4679"));
        p.noStroke();
        p.rect(SUB_AREA_OFFSET_X + 10, SUB_AREA_OFFSET_Y + SCORE_TEXT_OFFSET_Y_IN_SUB + 25, SUB_AREA_WIDTH - 20, 25);

        p.fill(255);
        p.textSize(20);
        p.textAlign(PConstants.RIGHT);
        p.text(String.valueOf(score), SUB_AREA_OFFSET_X + SUB_AREA_WIDTH - 10, SUB_AREA_OFFSET_Y + SCORE_TEXT_OFFSET_Y_IN_SUB + 20);
        p.textAlign(PConstants.LEFT, PConstants.BASELINE);
    }
}
