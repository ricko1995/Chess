package com.ricko.chess

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ricko.chess.PieceManipulationHelper.allChessPieces
import com.ricko.chess.PieceManipulationHelper.getClosestPeace
import com.ricko.chess.PieceManipulationHelper.isBlackKingInCheck
import com.ricko.chess.PieceManipulationHelper.isWhiteKingInCheck
import com.ricko.chess.PieceManipulationHelper.movePieceToCoordinates
import com.ricko.chess.PieceManipulationHelper.resetPeacePosition
import com.ricko.chess.PieceManipulationHelper.startNewGame
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var chessBoard: View
        lateinit var activityLayout: ConstraintLayout
        lateinit var context: Context
    }

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Are you sure you want to exit?")
        dialog.setPositiveButton("Yes") { _, _ ->
            super.onBackPressed()
        }
        dialog.setNegativeButton("No") { _, _ -> }
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chessBoard = findViewById(R.id.chess_board)
        activityLayout = findViewById(R.id.activity_layout)
        context = this

        btnNewGame.setOnClickListener {
            activityLayout.startNewGame()
        }
        var pieceToManipulate: ChessPiece? = null
        chessBoard.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    pieceToManipulate =
                        getClosestPeace(arrayListOf(motionEvent.x.toInt(), motionEvent.y.toInt()))
                    pieceToManipulate?.let {
                        it.pieceView?.apply {
                            scaleX = 1.5f
                            scaleY = 1.5f
                        }
                    }
                }

                MotionEvent.ACTION_MOVE -> {
                    pieceToManipulate?.let {
                        it.pieceView?.apply {
                            x = motionEvent.rawX - this.width / 2
                            y = motionEvent.rawY - chessBoard.y / 2 - this.height / 2
                        }
                    }
                }

                MotionEvent.ACTION_UP -> {
                    pieceToManipulate?.let {
                        it.pieceView?.apply {
                            scaleX = 1f
                            scaleY = 1f
                        }
                        if (motionEvent.x.toInt() in 0..view.width &&
                            motionEvent.y.toInt() in 0..view.height
                        ) {
                            movePieceToCoordinates(
                                arrayListOf(
                                    motionEvent.x.toInt(),
                                    motionEvent.y.toInt()
                                ),
                                it
                            )
                        } else {
                            resetPeacePosition(it)
                        }
                    }
                    allChessPieces.find { it.wasLastMoved }?.let {
                        if (it.pieceColor == PieceColor.WHITE) {
                            txtNextOnMove.text = "Black to move"
                        } else txtNextOnMove.text = "White to move"
                    }

                    isBlackKingInCheck()?.let { king ->
                        king.pieceView?.apply {
                            background = ContextCompat.getDrawable(context, R.drawable.circle_red)
                        }
                    }
                    isWhiteKingInCheck()?.let { king ->
                        king.pieceView?.apply {
                            background = ContextCompat.getDrawable(context, R.drawable.circle_red)
                        }
                    }
                }
            }

            view.performClick()
            return@setOnTouchListener true
        }

    }


}


