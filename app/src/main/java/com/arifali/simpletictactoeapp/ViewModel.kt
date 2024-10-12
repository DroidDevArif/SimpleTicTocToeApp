package com.arifali.simpletictactoeapp

data class GameState(
    val board: Array<IntArray> = Array(3) { IntArray(3) { 0 } },
    var currentPlayer: Int = 1,
    var player1Name: String = "Player 1",
    var player2Name: String = "Player 2"
) {
    fun checkWinner(): Int? {
        // Check rows, columns, and diagonals for a winner
        for (i in 0..2) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0]
            }
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i]
            }
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0]
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2]
        }
        return null // No winner
    }


}
