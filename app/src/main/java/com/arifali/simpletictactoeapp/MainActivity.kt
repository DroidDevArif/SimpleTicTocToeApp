package com.arifali.simpletictactoeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arifali.simpletictactoeapp.ui.theme.SimpleTicTacToeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleTicTacToeAppTheme {

                val viewModel: GameViewModel by viewModels()
                TicTacToeScreen(viewModel)

            }
        }
    }
}


    @Composable
    fun TicTacToeScreen(viewModel: GameViewModel) {
        val gameState by viewModel.gameState.observeAsState(GameState())
        var player1Name by remember { mutableStateOf(gameState.player1Name) }
        var player2Name by remember { mutableStateOf(gameState.player2Name) }
        var gameStarted by remember { mutableStateOf(false) }
        val context = LocalContext.current

        if (!gameStarted) {

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(80.dp)) {
                TextField(
                    value = player1Name,
                    onValueChange = { player1Name = it },
                    label = { Text("Player 1 Name") }
                )
                TextField(
                    value = player2Name,
                    onValueChange = { player2Name = it },
                    label = { Text("Player 2 Name") }
                )
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Button(onClick = {
                    viewModel.setPlayerNames(player1Name, player2Name)
                    gameStarted = true
                }) {
                    Text("Start Game")
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 70.dp, start = 20.dp)) {
                Text("Current Player: ${if (gameState.currentPlayer == 1) gameState.player1Name else gameState.player2Name}", fontSize = 18.sp)

                LazyColumn {
                    items(3) { row ->

                        Row(modifier = Modifier.padding(top = 5.dp, start = 30.dp)

                           // .border(2.dp, Color.Black) // Add border to the row
                        )
                        {
                            for (col in 0..2) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                                        .clickable {
                                            if (viewModel.makeMove(row, col)) {
                                                val winner = gameState.checkWinner()
                                                if (winner != null) {
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "${if (winner == 1) gameState.player1Name else gameState.player2Name} Congratulation ! You are Winner",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = when (gameState.board[row][col]) {
                                        1 -> "X"
                                        2 -> "O"
                                        else -> ""
                                    }, fontSize = 36.sp)
                                    //color = Color.Black


                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { viewModel.resetGame() }) {
                    Text("Reset Game")
                }
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        SimpleTicTacToeAppTheme {
            val viewModel = GameViewModel() // Create a dummy ViewModel for preview
            viewModel.setPlayerNames("Arif", "Rahul") // Set player names for the preview
            TicTacToeScreen(viewModel)

        }
    }
