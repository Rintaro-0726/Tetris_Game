// InputHandler.java
import processing.core.PApplet;

class InputHandler {
    private GameManager gameManager;

    public InputHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void handleKeyPress(int keyCode, char keyChar) {
        if (!gameManager.isPlayingGame()) {
            if (keyChar == ' ') { 
                gameManager.startGame(); 
            }
            return;
        }

        if (keyCode == PApplet.LEFT) { 
            gameManager.moveCurrentBlock("LEFT");
        } else if (keyCode == PApplet.RIGHT) { 
            gameManager.moveCurrentBlock("RIGHT");
        } else if (keyCode == PApplet.UP) { 
            gameManager.hardDrop();
        } else if (keyCode == PApplet.DOWN) { 
            gameManager.dropBlock();
        } 
        else {
            if (keyChar == 'z' || keyChar == 'Z') {
                gameManager.rotateCurrentBlock();
            }
        }
    }
    
    public void handleMouseClick(int mouseX, int mouseY) {

        final int BTN_X = 280; 
        final int BTN_Y = 5 + 390; 
        final int BTN_WIDTH = 120;
        final int BTN_HEIGHT = 50;

        if (mouseX >= BTN_X && mouseX <= BTN_X + BTN_WIDTH &&
            mouseY >= BTN_Y && mouseY <= BTN_Y + BTN_HEIGHT) {
            if (!gameManager.isPlayingGame()) {
                gameManager.startGame(); 
            }
        }
    }
}
