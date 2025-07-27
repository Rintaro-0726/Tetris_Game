// ScoreManager.java

class ScoreManager {
    private int score;
    private GameManager gameManager; 

    public ScoreManager(GameManager gameManager) {
        this.score = 0;
        this.gameManager = gameManager;
    }

    public void initialize() {
        System.out.println("Score initialized: " + score);
        displayScore();
    }

    public void addScore(int linesCleared) {
        if (linesCleared == 1) {
            score += 10;
        } else if (linesCleared == 2) {
            score += 20;
        } else if (linesCleared == 3) {
            score += 100;
        } else if (linesCleared == 4) {
            score += 1000;
        }
        displayScore();
        gameManager.updateObstacleInterval(score); 
    }

    public void resetScore() {
        score = 0;
        displayScore();
    }

    public int getScore() {
        return score;
    }

    public void displayScore() {
        System.out.println("Current Score: " + score);
        if (gameManager != null && gameManager.getRenderer() != null) {
            gameManager.getRenderer().updateScoreDisplay(score);
        }
    }
}
