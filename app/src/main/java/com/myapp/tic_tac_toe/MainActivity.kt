package com.myapp.tic_tac_toe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var turn = P1
    private var board = mutableListOf<MutableList<Button>>()
    private lateinit var gridView: GridLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridLayout)
        createNewBoard()
    }

    private fun createNewBoard() {
        board = mutableListOf()
        gridView.removeAllViews()
        turn = P1
        (1..3).forEach { row ->
            var currentRow = mutableListOf<Button>()
            (1..3).forEach { column ->
                var button = Button(this)
                button.setOnClickListener {
                    (it as Button).text = setD()
                    checkForAWin()
                }
                button.tag = "bt$row#$column"
                currentRow.add(button)
                gridView.addView(button)
            }

            board.add(currentRow)
        }
    }

    private fun checkForAWin() {
        (0 until 3).forEach {
            var result = checkRowWin(it)
            if (result.first) {
                showWinDialog(result)
            }
            result = checkColumnWin(it)
            if (result.first) {
                showWinDialog(result)
            }
            result = checkDiagonalWin()
            if (result.first) {
                showWinDialog(result)
            }
        }
    }

    private fun showWinDialog(result: Pair<Boolean, String>) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("${result.second} Won")
        alertDialogBuilder.setCancelable(true)

        alertDialogBuilder.setPositiveButton(
            getString(android.R.string.ok)
        ) { dialog, _ ->
            dialog.cancel()
            createNewBoard()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun checkColumnWin(column: Int): Pair<Boolean, String> {
        var playerDraw = board[0][column].text.toString()
        (0 until 3).forEach { row ->
            if (board[row][column].text == "" || board[row][column].text != playerDraw) {
                return Pair(false, "")
            }
        }
        return Pair(true, playerDraw)
    }

    private fun checkRowWin(row: Int): Pair<Boolean, String> {
        var playerDraw = board[row][0].text.toString()
        (0 until 3).forEach { column ->
            if (board[row][column].text == "" || board[row][column].text != playerDraw) {
                return Pair(false, "")
            }
        }

        return Pair(true, playerDraw)
    }

    private fun checkDiagonalWin(): Pair<Boolean, String> {
        var playerDraw = board[0][0].text.toString()

        if ((board[0][0].text.toString()
                .isNotEmpty() && board[0][0].text.toString() == playerDraw) &&
            (board[1][1].text.toString()
                .isNotEmpty() && board[1][1].text.toString() == playerDraw) &&
            (board[2][2].text.toString().isNotEmpty() && board[2][2].text.toString() == playerDraw)
        ) {
            return Pair(true, playerDraw)
        }

        playerDraw = board[0][2].text.toString()
        if ((board[0][2].text.toString()
                .isNotEmpty() && board[0][2].text.toString() == playerDraw) &&
            (board[1][1].text.toString()
                .isNotEmpty() && board[1][1].text.toString() == playerDraw) &&
            (board[2][0].text.toString().isNotEmpty() && board[2][0].text.toString() == playerDraw)
        ) {
            return Pair(true, playerDraw)
        }
        return Pair(false, "")
    }

    private fun setD(): String {
        return if (turn == P1) {
            turn = P2
            "X"

        } else {
            turn = P1
            "O"
        }

    }

    companion object {
        const val P1 = "PLAYER1"
        const val P2 = "PLAYER2"
    }

    override fun onClick(p0: View?) {

    }
}