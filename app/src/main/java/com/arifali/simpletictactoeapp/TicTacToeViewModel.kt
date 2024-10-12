package com.arifali.simpletictactoeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class GameViewModel : ViewModel() {
    private val _gameState = MutableLiveData(GameState())
    val gameState: LiveData<GameState> get() = _gameState

    fun setPlayerNames(player1: String, player2: String) {
        _gameState.value?.let {
            _gameState.value = it.copy(player1Name = player1, player2Name = player2)
        }
    }

    fun makeMove(row: Int, col: Int): Boolean {
        _gameState.value?.let { state ->
            if (state.board[row][col] == 0) { // Check if the cell is empty
                val newBoard = state.board.map { it.clone() }.toTypedArray() // Copy the board
                newBoard[row][col] = state.currentPlayer
                val nextPlayer = if (state.currentPlayer == 1) 2 else 1
                _gameState.value = state.copy(board = newBoard, currentPlayer = nextPlayer)
                return true
            }
        }
        return false
    }

    fun resetGame() {
        _gameState.value = GameState() // Reset to initial state
    }
}
