package tetris_game.tetris;

public interface TetrisObserver {
    public void notifyLinesClear(int num);
    public void notifyNeedShape(TetrisBoard b);
    public void notifyGameOver(TetrisBoard b);
}