package com.example.slideimagepuzzlegame

class PuzzleGame {
    var mBoard: Array<IntArray>
    fun setEmptySpaceRow(num: Int) {
        emptySpaceRow = num
    }

    fun setEmptySpaceCol(num: Int) {
        emptySpaceCol = num
    }

    fun setMove(moveRow: Int, moveCol: Int) {
        var moveMade = false
        if (moveRow - 1 >= 0) {
            //check if empty space is above
            if (moveRow - 1 == emptySpaceRow && moveCol == emptySpaceCol) {
                //Exchange values
                mBoard[emptySpaceRow][emptySpaceCol] = mBoard[moveRow][moveCol]
                mBoard[moveRow][moveCol] = 0
                moveMade = true
            }
        }
        if (moveRow + 1 <= BOARD_ROW - 1 && moveMade == false) {
            //check if empty space is below
            if (moveRow + 1 == emptySpaceRow && moveCol == emptySpaceCol) {
                //Exchange values
                mBoard[emptySpaceRow][emptySpaceCol] = mBoard[moveRow][moveCol]
                mBoard[moveRow][moveCol] = 0
                moveMade = true
            }
        }
        if (moveCol + 1 <= BOARD_COL - 1 && moveMade == false) {
            //check if empty space is to the right
            if (moveRow == emptySpaceRow && moveCol + 1 == emptySpaceCol) {
                //Exchange values
                mBoard[emptySpaceRow][emptySpaceCol] = mBoard[moveRow][moveCol]
                mBoard[moveRow][moveCol] = 0
                moveMade = true
            }
        }
        if (moveCol - 1 >= 0 && moveMade == false) {
            //check if empty space is to the left
            if (moveRow == emptySpaceRow && moveCol - 1 == emptySpaceCol) {
                //Exchange values
                mBoard[emptySpaceRow][emptySpaceCol] = mBoard[moveRow][moveCol]
                mBoard[moveRow][moveCol] = 0
                moveMade = true
            }
        }
    }

    fun isAdjacent(row: Int, col: Int): Boolean {
        if (row - 1 >= 0) {
            //check if empty space is above
            if (row - 1 == emptySpaceRow && col == emptySpaceCol) {
                return true
            }
        }
        if (row + 1 <= BOARD_ROW - 1) {
            //check if empty space is below
            if (row + 1 == emptySpaceRow && col == emptySpaceCol) {
                return true
            }
        }
        if (col + 1 <= BOARD_COL - 1) {
            //check if empty space is to the right
            if (row == emptySpaceRow && col + 1 == emptySpaceCol) {
                return true
            }
        }
        if (col - 1 >= 0) {
            //check if empty space is to the left
            if (row == emptySpaceRow && col - 1 == emptySpaceCol) {
                return true
            }
        }
        return false
    }

    val isComplete: Boolean
        get() {
            var counter = 0
            for (i in 0 until BOARD_ROW) {
                for (j in 0 until BOARD_COL) {
                    if (mBoard[i][j] != counter) return false
                    counter++
                }
            }
            return true
        }

    companion object {
        const val BOARD_ROW = 3
        const val BOARD_COL = 3
        const val BOARD_SIZE = BOARD_ROW * BOARD_COL
        var emptySpaceRow = 0
        var emptySpaceCol = 0
    }

    init {
        var counter = 0
        mBoard = Array(BOARD_ROW) {
            IntArray(
                BOARD_COL
            )
        }
        for (i in 0 until BOARD_ROW) {
            for (j in 0 until BOARD_COL) {
                mBoard[i][j] = counter
                counter++
            }
        }
    }
}