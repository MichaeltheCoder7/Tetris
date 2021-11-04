package tetris_game.tetris;

import static java.util.Arrays.fill;

public class TetrisBoard extends AbstractTetrisBoard {
    private int boardHeight;
    private int boardWidth;
    private int[][] board; // board
    private int[][] shapeHolder = new int[4][4]; // 4x4 array to store the shape
    private int[][] shapeIndices = new int[4][2]; // 4x2 array to save indices of the shape
    private int arrayX; // save the upper left indices of the 4x4 array for rotation
    private int arrayY;
    private boolean shapeWaiting = false; // if this shape is waiting to be added to the board
    private boolean shapeHanging = false; // if this shape is on the board and not stacked
    private TetrisShape shape;
    private TetrisObserver observer = null;
    private int linesCleared = 0;

    // initialize the game board
    public void init(int height, int width) {
        boardHeight = height + 1;
        boardWidth = width + 2;
        board = new int[boardHeight][boardWidth];

        for (int i = 0; i < boardHeight; i++) {
            fill(board[i], 0); // reset board
        }

        // fill the the borders with 2 to indicate out of board
        for (int i = 0; i < boardHeight; i++) {
            board[i][0] = 2;
            board[i][boardWidth - 1] = 2;
        }
        for (int i = 0; i < boardWidth; i++) {
            board[height][i] = 2;
        }
    }

    // add the shape to 4x4 array
    public void addShape(TetrisShape s) {
        if (shapeHanging) {
            return; // ignore if there is a shape on the board
        }

        int[][] shapeArray = s.getShapeArray(); // get the 4x2 shape array

        // clear the shape holder array
        for (int i = 0; i < 4; i++) {
            fill(shapeHolder[i], 0);
        }

        // copy the array to 4x4
        for (int x = 2; x < 4; x++) {
            System.arraycopy(shapeArray[x - 2], 0, shapeHolder[x], 0, 4);
        }

        shapeWaiting = true; // indicate there is a shape waiting
        shape = s; // save the shape
        for (int i = 0; i < 4; i++) {
            fill(shapeIndices[i], 0); // reset indices
        }
        arrayX = arrayY = -1;
    }

    // remove a shape out of board
    public void removeShapeBoard() {
        for (int i = 0; i < 4; i++) {
            board[shapeIndices[i][0]][shapeIndices[i][1]] = 0;
        }
    }

    // add a shape to the board
    public void addShapeBoard() {
        for (int i = 0; i < 4; i++) {
            board[shapeIndices[i][0]][shapeIndices[i][1]] = 1;
        }
    }

    public synchronized void moveLeft() {
        if (shapeHanging) {
            // remove the shape
            // for collision check
            removeShapeBoard();

            // check collision
            for (int i = 0; i < 4; i++) {
                if (board[shapeIndices[i][0]][shapeIndices[i][1] - 1] != 0) {
                    // collision found
                    // add the shape back
                    addShapeBoard();
                    return;
                }
            }

            // no collision found, move left
            for (int i = 0; i < 4; i++) {
                shapeIndices[i][1]--;
            }
            arrayY--;
            addShapeBoard();
        }
    }

    public synchronized void moveRight() {
        if (shapeHanging) {
            // remove the shape
            removeShapeBoard();

            // check collision
            for (int i = 0; i < 4; i++) {
                if (board[shapeIndices[i][0]][shapeIndices[i][1] + 1] != 0) {
                    // collision found
                    // add the shape back
                    addShapeBoard();
                    return;
                }
            }

            // no collision found, move right
            for (int i = 0; i < 4; i++) {
                shapeIndices[i][1]++;
            }
            arrayY++;
            addShapeBoard();
        }
    }

    // save the initial indices of the shape
    public void saveIndices(int starting){
        switch (shape) {
            case BAR:
                shapeIndices[0][0] = 1;
                shapeIndices[0][1] = starting;
                shapeIndices[1][0] = 1;
                shapeIndices[1][1] = starting + 1;
                shapeIndices[2][0] = 1;
                shapeIndices[2][1] = starting + 2;
                shapeIndices[3][0] = 1;
                shapeIndices[3][1] = starting + 3;
                break;
            case BOX:
                shapeIndices[0][0] = 0;
                shapeIndices[0][1] = starting + 1;
                shapeIndices[1][0] = 0;
                shapeIndices[1][1] = starting + 2;
                shapeIndices[2][0] = 1;
                shapeIndices[2][1] = starting + 1;
                shapeIndices[3][0] = 1;
                shapeIndices[3][1] = starting + 2;
                break;
            case T:
                shapeIndices[0][0] = 1;
                shapeIndices[0][1] = starting;
                shapeIndices[1][0] = 1;
                shapeIndices[1][1] = starting + 1;
                shapeIndices[2][0] = 1;
                shapeIndices[2][1] = starting + 2;
                shapeIndices[3][0] = 0;
                shapeIndices[3][1] = starting + 1;
                break;
            case S:
                shapeIndices[0][0] = 1;
                shapeIndices[0][1] = starting;
                shapeIndices[1][0] = 1;
                shapeIndices[1][1] = starting + 1;
                shapeIndices[2][0] = 0;
                shapeIndices[2][1] = starting + 1;
                shapeIndices[3][0] = 0;
                shapeIndices[3][1] = starting + 2;
                break;
            case SR:
                shapeIndices[0][0] = 0;
                shapeIndices[0][1] = starting;
                shapeIndices[1][0] = 0;
                shapeIndices[1][1] = starting + 1;
                shapeIndices[2][0] = 1;
                shapeIndices[2][1] = starting + 1;
                shapeIndices[3][0] = 1;
                shapeIndices[3][1] = starting + 2;
                break;
            case L:
                shapeIndices[0][0] = 1;
                shapeIndices[0][1] = starting;
                shapeIndices[1][0] = 1;
                shapeIndices[1][1] = starting + 1;
                shapeIndices[2][0] = 1;
                shapeIndices[2][1] = starting + 2;
                shapeIndices[3][0] = 0;
                shapeIndices[3][1] = starting + 2;
                break;
            case LR:
                shapeIndices[0][0] = 0;
                shapeIndices[0][1] = starting;
                shapeIndices[1][0] = 0;
                shapeIndices[1][1] = starting + 1;
                shapeIndices[2][0] = 0;
                shapeIndices[2][1] = starting + 2;
                shapeIndices[3][0] = 1;
                shapeIndices[3][1] = starting + 2;
                break;
        }
    }

    public synchronized void moveDown() {
        int starting;

        if (shapeWaiting) {
            starting = (boardWidth - 4) / 2; // set the starting index

            // save the indices
            saveIndices(starting);
            arrayX = -2;
            arrayY = starting;
            // notify and exit if game over
            if (checkGameOver() && observer != null) {
                observer.notifyGameOver(this);
                return;
            }

            // move the shape to the board
            for (int x = 0; x < 2; x++) {
                System.arraycopy(shapeHolder[x + 2], 0, board[x], starting, 4);
            }

            shapeWaiting = false;
            shapeHanging = true;

        } else if (shapeHanging) {
            // remove the shape
            removeShapeBoard();

            // check collision
            for (int x = 0; x < 4; x++) {
                if (board[shapeIndices[x][0] + 1][shapeIndices[x][1]] != 0) {
                    // collision found
                    // add the shape back
                    addShapeBoard();
                    shapeHanging = false;
                    checkCompleteLine(); // check lines
                    return;
                }
            }

            // no collision found, move down
            for (int x = 0; x < 4; x++) {
                shapeIndices[x][0]++;
            }
            arrayX++;
            addShapeBoard();

        } else if (observer != null) {
            observer.notifyNeedShape(this); // notify TUI to get new shape
        }
    }

    public synchronized void moveDownToEnd() {
        if (shapeHanging) {
            // remove the shape
            removeShapeBoard();

            while (true) {
                // check collision
                for (int x = 0; x < 4; x++) {
                    if (board[shapeIndices[x][0] + 1][shapeIndices[x][1]] != 0) {
                        // collision found
                        // add the shape back and move to the end
                        addShapeBoard();
                        shapeHanging = false;
                        checkCompleteLine(); // check lines
                        return;
                    }
                }

                // no collision found, increment x index by 1
                for (int x = 0; x < 4; x++) {
                    shapeIndices[x][0]++;
                }
            }
        }
    }

    // check and delete a line if completed
    // also notify
    public void checkCompleteLine() {
        int[][] copy = new int[boardHeight][boardWidth];
        boolean cleared;
        int num = 0;

        for (int x = boardHeight - 2; x >= 0; x--) {
            cleared = true;

            for (int y = 1; y < boardWidth - 1; y++) {
                if (board[x][y] == 0) {
                    cleared = false; // not cleared if found 0
                    break;
                }
            }

            if (cleared) {
                // delete lines
                // copy the board and delete that line in the new array
                for (int i = boardHeight - 1; i > x; i--) {
                    System.arraycopy(board[i], 0, copy[i], 0, boardWidth);
                }
                for (int i = x - 1; i >= 0; i--) {
                    System.arraycopy(board[i], 0, copy[i + 1], 0, boardWidth);
                }

                // clear the line on the top
                fill(copy[0], 0);
                copy[0][0] = copy[0][boardWidth - 1] = 2;

                // copy the array back
                for (int i = 0; i < boardHeight; i++) {
                    System.arraycopy(copy[i], 0, board[i], 0, boardWidth);
                }

                x++; // increment x since a line is deleted
                num++;
            }
        }

        linesCleared += num; // update the total lines cleared

        // notify
        if (num > 0 && observer != null) {
            observer.notifyLinesClear(linesCleared);
        }
    }

    // check game over
    public boolean checkGameOver() {
        // if the new shape has collision, it's game over
        for (int i = 0; i < 4; i++) {
            if (board[shapeIndices[i][0]][shapeIndices[i][1]] != 0) {
                return true;
            }
        }

        return false;
    }

    // return a array of the indices of the shape based on shape holder array
    public int[][] getIndices() {
        int[][] temp = new int[4][2];
        int i = 0;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (shapeHolder[x][y] == 1) {
                    temp[i][0] = arrayX + x;
                    temp[i][1] = arrayY + y;
                    i++;
                }
            }
        }

        return temp;
    }

    public synchronized void rotateLeft() {
        int[][] rotateIndices;
        int temp;

        if (shapeHanging && shape != TetrisShape.BOX) {
            removeShapeBoard(); // remove the shape on the board for collision check later

            // rotate the shape holder array first
            // use array rotation algorithm
            for (int x = 0; x < 2; x++) {
                for (int y = x; y < 3 - x; y++) {
                    // store current cell in temp variable
                    temp = shapeHolder[x][y];

                    // move values from right to top
                    shapeHolder[x][y] = shapeHolder[y][3 - x];

                    // move values from bottom to right
                    shapeHolder[y][3 - x] = shapeHolder[3 - x][3 - y];

                    // move values from left to bottom
                    shapeHolder[3 - x][3 - y] = shapeHolder[3 - y][x];

                    // assign temp to left
                    shapeHolder[3 - y][x] = temp;
                }
            }

            // check collision
            rotateIndices = getIndices();
            // make sure to not go out of bound
            for (int x = 0; x < 4; x++) {
                if (rotateIndices[x][0] < 0 || rotateIndices[x][0] >= boardHeight
                        || rotateIndices[x][1] < 0 || rotateIndices[x][1] >= boardWidth
                        || board[rotateIndices[x][0]][rotateIndices[x][1]] != 0) {
                    // collision found
                    // add the shape back
                    addShapeBoard();
                    return;
                }
            }

            // no collision found, rotate
            for (int x = 0; x < 4; x++) {
                System.arraycopy(rotateIndices[x], 0, shapeIndices[x], 0, 2);
            }
            addShapeBoard();
        }
    }

    public int[][] getBoardArray() {
        return board;
    }

    // add observer
    public void addObserver(TetrisObserver o) {
        observer = o;
    }

    // remove observer
    public void removeObserver(TetrisObserver o) {
        observer = null;
    }
}