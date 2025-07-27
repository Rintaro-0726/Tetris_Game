// GameManager.java (修正後)

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import processing.core.PApplet;

public class GameManager {
    private boolean isPlayingGame;
    private Block currentBlock;
    private Queue<Block> nextBlocks;
    private Playfield playfield;
    private Renderer renderer;
    private ScoreManager scoreManager;
    private InputHandler inputHandler;

    private long obstacleTimer;
    private long obstacleInterval;
    
    private Random random = new Random(); 
    
    public GameManager(PApplet pApplet, int cellSize) { 
        this.nextBlocks = new LinkedList<>();
        this.playfield = new Playfield(Playfield.WIDTH, Playfield.HEIGHT);
        this.renderer = new Renderer(pApplet, cellSize); 
        this.scoreManager = new ScoreManager(this);
        this.inputHandler = new InputHandler(this);
        
        this.isPlayingGame = false;
        this.obstacleInterval = 20000;
        this.obstacleTimer = 0;
    }

    public boolean isPlayingGame() { return isPlayingGame; }
    public Playfield getPlayfield() { return playfield; }
    public Renderer getRenderer() { return renderer; }
    public ScoreManager getScoreManager() { return scoreManager; }
    public InputHandler getInputHandler() { return inputHandler; }
    public Block getCurrentBlock() { return currentBlock; }
    public Queue<Block> getNextBlocks() { return nextBlocks; }

    public void startGame() {
        System.out.println("Game Started!");
        renderer.clearAll();
        scoreManager.resetScore();
        playfield.initializeGrid();

        isPlayingGame = true;
        obstacleTimer = 0;
        
        nextBlocks.clear();
        for(int i = 0; i < 4; i++) {
            nextBlocks.offer(generateRandomBlock());
        }
        createNextBlock();

        renderer.drawBackground(); 
        renderer.drawPlayfield(playfield);
        renderer.drawNextBlocks(nextBlocks);
        renderer.drawBlock(currentBlock);
    }

    public void endGame() {
        isPlayingGame = false;
        renderer.showGameOverMessage();
        
        
        System.out.println("Game Over! Final Score: " + scoreManager.getScore());
    }

    public void handleTimedEvents(long elapsedTime) {
        if (!isPlayingGame) {
            return;
        }
        
        obstacleTimer += elapsedTime;
        if (obstacleTimer >= obstacleInterval) {
            generateObstacleBlock();
            obstacleTimer = 0; 
        }
    }

    private void createNextBlock() {
        currentBlock = nextBlocks.poll();
        if (currentBlock == null) {
            currentBlock = generateRandomBlock();
        }
        currentBlock.setX(4);
        currentBlock.setY(0);
        currentBlock.setDirection(0);

        nextBlocks.offer(generateRandomBlock());
        renderer.drawNextBlocks(nextBlocks);

        if (!checkCollision(currentBlock, 0, 0, 0)) {
            endGame();
        }
    }

    private Block generateRandomBlock() {
        int type = random.nextInt(7);
        if (type == 0) { return new ShapeT(0, 0); }
        else if (type == 1) { return new ShapeL(0, 0); }
        else if (type == 2) { return new ShapeZ(0, 0); }
        else if (type == 3) { return new ShapeS(0, 0); }
        else if (type == 4) { return new ShapeJ(0, 0); }
        else if (type == 5) { return new ShapeI(0, 0); }
        else if (type == 6) { return new ShapeO(0, 0); }
        else { return new ShapeT(0, 0); }
    }

    public boolean checkCollision(Block block, int deltaX, int deltaY, int deltaDirection) {
        Block testBlock = block.cloneBlock();
        testBlock.setX(block.getX() + deltaX);
        testBlock.setY(block.getY() + deltaY);
        if (deltaDirection != 0) { 
            testBlock.rotate();
        }

        int[][] shape = testBlock.getShape();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] == 1) {
                    int targetX = testBlock.getX() + j;
                    int targetY = testBlock.getY() + i;
                    
                    if (targetY < 0) {
                        continue;
                    }

                    if (!playfield.isWithinBounds(targetX, targetY)) {
                        return false;
                    }
                    if (playfield.getCellState(targetX, targetY) != Playfield.EMPTY_CELL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveCurrentBlock(String direction) {
        if (!isPlayingGame) return;
        int formerX = currentBlock.getX();
        renderer.clearBlock(currentBlock);
        if (direction.equals("LEFT")) {
            currentBlock.move(-1, 0);
        } else if (direction.equals("RIGHT")) {
            currentBlock.move(1, 0);
        }
        if (!checkCollision(currentBlock, 0, 0, 0)) {
            currentBlock.setX(formerX);
        }
        renderer.drawBlock(currentBlock);
    }
    
    public void rotateCurrentBlock() {
        if (!isPlayingGame) return;

        if (checkCollision(currentBlock, 0, 0, 1)) {
            renderer.clearBlock(currentBlock);
            currentBlock.rotate();
            renderer.drawBlock(currentBlock);
        }
    }

    public void dropBlock() {
        if (!isPlayingGame) return;
        renderer.clearBlock(currentBlock);

        if (checkCollision(currentBlock, 0, 1, 0)) {
            currentBlock.move(0, 1);
            renderer.drawBlock(currentBlock);
        } else {
            renderer.drawBlock(currentBlock);
            fixBlock();
            clearLines();
            createNextBlock();
        }
    }

    public void hardDrop() {
        if (!isPlayingGame) return;
        renderer.clearBlock(currentBlock);
        while (checkCollision(currentBlock, 0, 1, 0)) {
            currentBlock.move(0, 1);
        }
        renderer.drawBlock(currentBlock);
        fixBlock();
        clearLines();
        createNextBlock();
    }

    public void fixBlock() {
        playfield.addBlockToGrid(currentBlock);
    }

    public void clearLines() {
        ArrayList<Integer> completedLines = playfield.checkLineCompletion();
        if (!completedLines.isEmpty()) {
            playfield.clearLines(completedLines);
            scoreManager.addScore(completedLines.size());
        }
    }

    public void generateObstacleBlock() {
        if (!isPlayingGame) return;
        int numBlocksToPlace = random.nextInt(2) + 1;
        ArrayList<int[]> currentBlockCells = new ArrayList<>();
        if (currentBlock != null) {
            int[][] shape = currentBlock.getShape();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shape[i][j] == 1) {
                        currentBlockCells.add(new int[]{currentBlock.getX() + j, currentBlock.getY() + i});
                    }
                }
            }
        }
        boolean placed = playfield.placeObstacleBlock(numBlocksToPlace, currentBlockCells);
        if (placed) {
            renderer.playObstacleEffect();
            System.out.println("Generated " + numBlocksToPlace + " obstacle block(s).");
        } else {
            System.out.println("Could not place obstacle block(s).");
        }
    }

    public void updateObstacleInterval(int currentScore) {
        if (currentScore < 1000) { obstacleInterval = 20000; }
        else if (currentScore < 3000) { obstacleInterval = 15000; }
        else if (currentScore < 5000) { obstacleInterval = 10000; }
        else { obstacleInterval = 5000; }
        System.out.println("Obstacle interval updated to: " + obstacleInterval + " ms based on score " + currentScore);
    }
}
