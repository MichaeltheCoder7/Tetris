package tetris_game.tetris;

/*
00 01 02 03
10 11 12 13
20 21 22 23
30 31 32 33
 */

/*
     -22
     -12
00 01 02 03 04 05 06
10 11 12 13 14 15 16
20 21 22 23 24 25 26
30 31 32 33 34 35 36
40 41 42 43 44 45 46
50 51 52 53 54 55 56
 */

public enum TetrisShape {
    BAR(new int[][]{{ 0 , 0 , 0 , 0 }, { 1 , 1 , 1 , 1 }}),
    BOX(new int[][]{{ 0 , 1 , 1 , 0 }, { 0 , 1 , 1 , 0 }}),
    T(new int[][]{{ 0 , 1 , 0 , 0 }, { 1 , 1 , 1 , 0 }}),
    S(new int[][]{{ 0 , 1 , 1 , 0 }, { 1 , 1 , 0 , 0 }}), // S
    SR(new int[][]{{ 1 , 1 , 0 , 0 }, { 0 , 1 , 1 , 0 }}), // S-reverse
    L(new int[][]{{ 0 , 0 , 1 , 0 }, { 1 , 1 , 1 , 0 }}), // L
    LR(new int[][]{{ 1 , 1 , 1 , 0 }, { 0 , 0 , 1 , 0 }}); // L-reverse

    private int[][] shapeArray;

    TetrisShape(int[][] shapeArray) {
        this.shapeArray = shapeArray;
    }

    public int[][] getShapeArray() {
        return shapeArray;
    }

    public static TetrisShape getRandomShape() {
        return TetrisShape.values()[new java.util.Random().nextInt(TetrisShape.values().length)];
    }

    @Override
    public String toString() {
        String toStr = "" + this.name() + " " + System.lineSeparator();
        for (int i = 0; i < this.shapeArray.length; i++) {
            for (int j = 0; j < this.shapeArray[i].length; j++) {
                toStr += this.shapeArray[i][j];
            }
            toStr += System.lineSeparator();
        }
        return toStr;
    }
}