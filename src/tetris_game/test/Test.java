package tetris_game.test;

import tetris_game.tetris.TetrisBoard;
import tetris_game.tetris.TetrisShape;

public class Test {
    public static void main(String[] args) {
        TetrisBoard tb = new TetrisBoard();
        tb.init(5 ,5);
        System.out.println(tb.toString());
        // will print out
        /*
        2000002
        2000002
        2000002
        2000002
        2000002
        2222222
        */
        tb.addShape(TetrisShape.BOX);
        tb.moveDown();
        System.out.println(tb.toString());
        /* Box appeared
        2011002
        2011002
        2000002
        2000002
        2000002
        2222222
        */
        tb.moveDownToEnd();
        System.out.println(tb.toString());
        /* box dropped
        2000002
        2000002
        2000002
        2011002
        2011002
        2222222
        */
        tb.addShape(TetrisShape.T);
        tb.moveDown();
        tb.addShape(TetrisShape.L); // this must be ignored until T dropped
        tb.moveDown();
        System.out.println(tb.toString());
        /* T shape stacked
        2000002
        2010002
        2111002
        2011002
        2011002
        2222222
        */
        tb.moveDown(); // should be ignored because it is stacked already.
        System.out.println(tb.toString());
    }
}