package com.ricko.chess

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ricko.chess.MainActivity.Companion.activityLayout
import com.ricko.chess.MainActivity.Companion.chessBoard
import com.ricko.chess.ValidMoves.isValidMoveForPawn
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object PieceManipulationHelper {

    private val whitePieces: ArrayList<ChessPiece> = arrayListOf()
    private val blackPieces: ArrayList<ChessPiece> = arrayListOf()

    private fun ConstraintLayout.positionPeaceOnBoardForNewGame(
        chessPiece: ChessPiece
    ) {
        addView(chessPiece.pieceView)
        val boardLayoutParams = ConstraintLayout.LayoutParams(
            chessBoard.width / 10,
            chessBoard.height / 10
        )
        chessPiece.pieceView.apply {
            layoutParams = boardLayoutParams
            x = chessPiece.pixelCoordinates[0].toFloat()
            y = chessPiece.pixelCoordinates[1].toFloat()
        }
//        if (chessPiece.pieceColor == PieceColor.BLACK) chessPiece.pieceView.rotationX = 180f
        invalidate()

    }

    fun ConstraintLayout.createPawns(context: Context) {
        val numberOfPieces = 16
        repeat(numberOfPieces) {
            val pawnView = View(context)
            pawnView.id = View.generateViewId()
            pawnView.background =
                if (it < numberOfPieces / 2) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_white_pawn
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_black_pawn)
            val chessPiece = ChessPiece(
                if (it < numberOfPieces / 2) PieceColor.WHITE else PieceColor.BLACK,
                fromChessCoordinatesToPixelCoordinates(
                    arrayListOf(
                        it % (numberOfPieces / 2),
                        if (it < numberOfPieces / 2) 6 else 1
                    )
                ),
                PieceType.PAWN,
                pawnView,
                arrayListOf(
                    it % (numberOfPieces / 2),
                    if (it < numberOfPieces / 2) 6 else 1
                )
            )
            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(blackPieces.last())
            }

        }
    }

    fun ConstraintLayout.createKings(context: Context) {
        val numberOfPieces = 2
        repeat(numberOfPieces) {
            val kingView = View(context)
            kingView.id = View.generateViewId()
            kingView.background =
                if (it < numberOfPieces / 2) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_white_king
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_black_king)
            val chessPiece = ChessPiece(
                if (it < numberOfPieces / 2) PieceColor.WHITE else PieceColor.BLACK,
                fromChessCoordinatesToPixelCoordinates(
                    arrayListOf(
                        if (it < numberOfPieces / 2) 4 else 3,
                        if (it < numberOfPieces / 2) 7 else 0
                    )
                ),
                PieceType.KING,
                kingView,
                arrayListOf(
                    if (it < numberOfPieces / 2) 4 else 3,
                    if (it < numberOfPieces / 2) 7 else 0
                )
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createQueens(context: Context) {
        val numberOfPieces = 2
        repeat(numberOfPieces) {
            val queenView = View(context)
            queenView.id = View.generateViewId()
            queenView.background =
                if (it < numberOfPieces / 2) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_white_queen
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_black_queen)
            val chessPiece = ChessPiece(
                if (it < numberOfPieces / 2) PieceColor.WHITE else PieceColor.BLACK,
                fromChessCoordinatesToPixelCoordinates(
                    arrayListOf(
                        if (it < numberOfPieces / 2) 3 else 4,
                        if (it < numberOfPieces / 2) 7 else 0
                    )
                ),
                PieceType.QUEEN,
                queenView,
                arrayListOf(
                    if (it < numberOfPieces / 2) 3 else 4,
                    if (it < numberOfPieces / 2) 7 else 0
                )
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createBishops(context: Context) {
        val numberOfPieces = 4
        val bishopsCoordinate: ArrayList<ArrayList<Int>> =
            arrayListOf(arrayListOf(2, 7), arrayListOf(5, 7), arrayListOf(2, 0), arrayListOf(5, 0))
        repeat(numberOfPieces) {
            val bishopView = View(context)
            bishopView.id = View.generateViewId()
            bishopView.background =
                if (it < numberOfPieces / 2) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_white_bishop
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_black_bishop)
            val chessPiece = ChessPiece(
                if (it < numberOfPieces / 2) PieceColor.WHITE else PieceColor.BLACK,
                fromChessCoordinatesToPixelCoordinates(
                    bishopsCoordinate[it]
                ),
                PieceType.BISHOP,
                bishopView,
                bishopsCoordinate[it]
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createKnights(context: Context) {
        val numberOfPieces = 4
        val knightsCoordinate: ArrayList<ArrayList<Int>> =
            arrayListOf(arrayListOf(1, 7), arrayListOf(6, 7), arrayListOf(1, 0), arrayListOf(6, 0))
        repeat(numberOfPieces) {
            val knightView = View(context)
            knightView.id = View.generateViewId()
            knightView.background =
                if (it < numberOfPieces / 2) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_white_knight
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_black_knight)
            val chessPiece = ChessPiece(
                if (it < numberOfPieces / 2) PieceColor.WHITE else PieceColor.BLACK,
                fromChessCoordinatesToPixelCoordinates(
                    knightsCoordinate[it]
                ),
                PieceType.KNIGHT,
                knightView,
                knightsCoordinate[it]
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createRooks(context: Context) {
        val numberOfPieces = 4
        val rooksCoordinate: ArrayList<ArrayList<Int>> =
            arrayListOf(arrayListOf(0, 7), arrayListOf(7, 7), arrayListOf(0, 0), arrayListOf(7, 0))
        repeat(numberOfPieces) {
            val rookView = View(context)
            rookView.id = View.generateViewId()
            rookView.background =
                if (it < numberOfPieces / 2) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_white_rook
                )
                else ContextCompat.getDrawable(context, R.drawable.ic_black_rook)
            val chessPiece = ChessPiece(
                if (it < numberOfPieces / 2) PieceColor.WHITE else PieceColor.BLACK,
                fromChessCoordinatesToPixelCoordinates(
                    rooksCoordinate[it]
                ),
                PieceType.ROOK,
                rookView,
                rooksCoordinate[it]
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.removeAllPiecesFromBoard() {
        for (piece in blackPieces) {
            removeView(piece.pieceView)
        }
        for (piece in whitePieces) {
            removeView(piece.pieceView)
        }
        blackPieces.clear()
        whitePieces.clear()
    }

    fun movePieceToCoordinates(
        coordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ) {
        val chessCoordinates = fromPixelCoordinatesToChessCoordinates(coordinates)
        val pixelCoordinates = fromChessCoordinatesToPixelCoordinates(chessCoordinates)
        if (chessPiece.pieceType == PieceType.PAWN &&
            !isValidMoveForPawn(chessCoordinates, chessPiece)
        ) {
            resetPeacePosition(chessPiece)
            return
        }

//        isSomePieceAlreadyOnCoordinates(chessCoordinates)?.let {
//            if (it == chessPiece.pieceColor) {
//                resetPeacePosition(chessPiece)
//                return
//            }
//        }
        activityLayout.capturePieceOnCoordinates(chessCoordinates)
        chessPiece.pieceView.x = pixelCoordinates[0].toFloat()
        chessPiece.pieceView.y = pixelCoordinates[1].toFloat()
        chessPiece.pixelCoordinates = pixelCoordinates
        chessPiece.chessCoordinates = chessCoordinates
    }

    private fun ConstraintLayout.capturePieceOnCoordinates(chessCoordinates: ArrayList<Int>) {
        if (isSomePieceAlreadyOnCoordinates(chessCoordinates) == null) return
        var toRemove: ChessPiece? = null
        for (whitePiece in whitePieces) {
            if (whitePiece.chessCoordinates == chessCoordinates) {
                this.removeView(whitePiece.pieceView)
                toRemove = whitePiece
            }
        }
        toRemove?.let {
            whitePieces.remove(it)
        }
        toRemove = null

        for (blackPiece in blackPieces) {
            if (blackPiece.chessCoordinates == chessCoordinates) {
                this.removeView(blackPiece.pieceView)
                toRemove = blackPiece
            }
        }
        toRemove?.let {
            blackPieces.remove(it)
        }
    }

    fun isSomePieceAlreadyOnCoordinates(chessCoordinates: ArrayList<Int>): ChessPiece? {
        for (whitePiece in whitePieces) {
            if (whitePiece.chessCoordinates == chessCoordinates) return whitePiece
        }
        for (blackPiece in blackPieces) {
            if (blackPiece.chessCoordinates == chessCoordinates) return blackPiece
        }
        return null
    }

    fun getClosestPeace(coordinates: ArrayList<Int>): ChessPiece? {
        val chessCord = fromPixelCoordinatesToChessCoordinates(coordinates)
        for (whitePeace in whitePieces) {
            if (whitePeace.chessCoordinates == chessCord) return whitePeace
        }
        for (blackPeace in blackPieces) {
            if (blackPeace.chessCoordinates == chessCord) return blackPeace
        }
        return null
    }

    fun resetPeacePosition(chessPiece: ChessPiece) {
        chessPiece.pieceView.apply {
            x = chessPiece.pixelCoordinates[0].toFloat()
            y = chessPiece.pixelCoordinates[1].toFloat()
        }
    }

    private fun fromChessCoordinatesToPixelCoordinates(
        coordinates: ArrayList<Int>
    ): ArrayList<Int> {
        val pieceDimension = chessBoard.height / 10
        if (coordinates[0] !in 0..7 && coordinates[1] !in 0..7) return arrayListOf(-1, -1)
        val x = chessBoard.width / 16 + coordinates[0] * chessBoard.width / 8 - pieceDimension / 2
        val y =
            chessBoard.height / 16 + coordinates[1] * chessBoard.height / 8 + chessBoard.y.toInt() - pieceDimension / 2
        return arrayListOf(x, y)
    }

    fun fromPixelCoordinatesToChessCoordinates(
        coordinates: ArrayList<Int>
    ): ArrayList<Int> {
        val x: Int
        val y: Int
        when (coordinates[0]) {
            in 0..(chessBoard.width / 8) -> {
                x = 0
            }
            in (chessBoard.width / 8)..(chessBoard.width / 8) * 2 -> {
                x = 1
            }
            in (chessBoard.width / 8) * 2..(chessBoard.width / 8) * 3 -> {
                x = 2
            }
            in (chessBoard.width / 8) * 3..(chessBoard.width / 8) * 4 -> {
                x = 3
            }
            in (chessBoard.width / 8) * 4..(chessBoard.width / 8) * 5 -> {
                x = 4
            }
            in (chessBoard.width / 8) * 5..(chessBoard.width / 8) * 6 -> {
                x = 5
            }
            in (chessBoard.width / 8) * 6..(chessBoard.width / 8) * 7 -> {
                x = 6
            }
            in (chessBoard.width / 8) * 7..(chessBoard.width / 8) * 8 -> {
                x = 7
            }
            else -> x = -1
        }

        when (coordinates[1]) {
            in (0)..(chessBoard.height / 8) -> {
                y = 0
            }
            in (chessBoard.height / 8)..(chessBoard.height / 8 * 2) -> {
                y = 1
            }
            in (chessBoard.height / 8 * 2)..(chessBoard.height / 8 * 3) -> {
                y = 2
            }
            in (chessBoard.height / 8 * 3)..(chessBoard.height / 8 * 4) -> {
                y = 3
            }
            in (chessBoard.height / 8 * 4)..(chessBoard.height / 8 * 5) -> {
                y = 4
            }
            in (chessBoard.height / 8 * 5)..(chessBoard.height / 8 * 6) -> {
                y = 5
            }
            in (chessBoard.height / 8 * 6)..(chessBoard.height / 8 * 7) -> {
                y = 6
            }
            in (chessBoard.height / 8 * 7)..(chessBoard.height / 8 * 8) -> {
                y = 7
            }
            else -> y = -1

        }

        return arrayListOf(x, y)
    }
}