// （例）TetrisGame.pde

GameManager gameManager;
long lastDropTime = 0;
long dropInterval = 1000; // 1秒

void setup() {
    size(400, 500); // ウィンドウサイズ
    gameManager = new GameManager(this, 20);
    //gameManager.startGame(); 
}

void draw() {
    // ゲームがプレイ中の時だけ更新処理を行う
    if (gameManager.isPlayingGame()) {
        
        // 1. 時間経過でブロックを落下させる
        if (millis() - lastDropTime > dropInterval) {
            gameManager.dropBlock();
            lastDropTime = millis(); // 時間をリセット
        }

        // 2. 毎フレーム描画を行う
        // GameManagerに全体の描画を指示するメソッドを作ると、ここがスッキリします
        gameManager.getRenderer().drawPlayfield(gameManager.getPlayfield());
        if(gameManager.getCurrentBlock() != null){
             gameManager.getRenderer().drawBlock(gameManager.getCurrentBlock());
        }
       
    } else {
        // ゲーム開始前やゲームオーバー時の画面
        gameManager.getRenderer().clearAll();
        //gameManager.getRenderer().showGameOverMessage(); 
    }
}

void keyPressed() {
    gameManager.getInputHandler().handleKeyPress(keyCode, key);
}

void mousePressed() {
    gameManager.getInputHandler().handleMouseClick(mouseX, mouseY);
}
