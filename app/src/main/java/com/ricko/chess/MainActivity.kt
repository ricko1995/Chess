package com.ricko.chess

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ricko.chess.PieceManipulationHelper.createBishops
import com.ricko.chess.PieceManipulationHelper.createKings
import com.ricko.chess.PieceManipulationHelper.createKnights
import com.ricko.chess.PieceManipulationHelper.createPawns
import com.ricko.chess.PieceManipulationHelper.createQueens
import com.ricko.chess.PieceManipulationHelper.createRooks
import com.ricko.chess.PieceManipulationHelper.removeAllPiecesFromBoard
import com.ricko.chess.PieceManipulationHelper.whitePieces
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    companion object {
        var pieceDimensions by Delegates.notNull<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pieceDimensions = chessBoard.height / 10

        var newGame = false
        btnNewGame.setOnClickListener {
            if (newGame) {
                activityLayout.removeAllPiecesFromBoard()
            } else {
                activityLayout.createPawns(this, chessBoard)
                activityLayout.createKings(this, chessBoard)
                activityLayout.createQueens(this, chessBoard)
                activityLayout.createBishops(this, chessBoard)
                activityLayout.createKnights(this, chessBoard)
                activityLayout.createRooks(this, chessBoard)
            }
            newGame = !newGame
        }

        chessBoard.setOnClickListener {
            Toast.makeText(this, whitePieces.size.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupPiecesOnTouchListeners() {
        for (whitePeace in whitePieces) {
            whitePeace.pieceView.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        view.scaleX = 1.5f
                        view.scaleY = 1.5f
                    }
                    MotionEvent.ACTION_UP -> {
                        view.scaleX = 1f
                        view.scaleY = 1f
                    }
                    MotionEvent.ACTION_MOVE -> {
                        view.x = motionEvent.x
                        view.y = motionEvent.y
                    }
                }

                view.performClick()
                return@setOnTouchListener true
            }
        }
    }


}


