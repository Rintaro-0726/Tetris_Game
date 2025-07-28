//TetrisGame.pde

GameManager gameManager;
long lastDropTime = 0;
long dropInterval = 1000;

void setup() {
    size(400, 500);
    gameManager = new GameManager(this, 20);
    gameManager.startGame(); 
}

void draw() {
    if (gameManager.isPlayingGame()) {
        
        if (millis() - lastDropTime > dropInterval) {
            gameManager.dropBlock();
            lastDropTime = millis();
        }

        gameManager.getRenderer().drawPlayfield(gameManager.getPlayfield());
        if(gameManager.getCurrentBlock() != null){
             gameManager.getRenderer().drawBlock(gameManager.getCurrentBlock());
        }
       
    } else {
        gameManager.getRenderer().clearAll();
        gameManager.getRenderer().showGameOverMessage(); 
    }
}

void keyPressed() {
    gameManager.getInputHandler().handleKeyPress(keyCode, key);
}

void mousePressed() {
    gameManager.getInputHandler().handleMouseClick(mouseX, mouseY);
}
