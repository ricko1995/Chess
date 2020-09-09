package com.ricko.chess

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

object PieceManipulationHelper {

    val whitePieces: ArrayList<ChessPiece> = ArrayList()
    val blackPieces: ArrayList<ChessPiece> = ArrayList()

    private fun ConstraintLayout.positionPeaceOnBoardForNewGame(
        chessBoard: View,
        chessPiece: ChessPiece
    ) {
        addView(chessPiece.pieceView)
        val boardLayoutParams = ConstraintLayout.LayoutParams(
            chessBoard.width / 10,
            chessBoard.height / 10
        )
        val pieceDimension = chessBoard.height / 10

        chessPiece.pieceView.apply {
            layoutParams = boardLayoutParams
            x = chessPiece.coordinates[0] - pieceDimension / 2f
            y = chessPiece.coordinates[1] + chessBoard.y - pieceDimension / 2
        }
        if (chessPiece.pieceColor == PieceColor.BLACK) chessPiece.pieceView.rotationX = 180f
        invalidate()

    }

    fun ConstraintLayout.createPawns(context: Context, chessBoard: View) {
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
                    ),
                    chessBoard
                ),
                PieceType.PAWN,
                pawnView
            )
            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, blackPieces.last())
            }

        }
    }

    fun ConstraintLayout.createKings(context: Context, chessBoard: View) {
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
                    ), chessBoard
                ),
                PieceType.KING,
                kingView
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createQueens(context: Context, chessBoard: View) {
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
                    ), chessBoard
                ),
                PieceType.QUEEN,
                queenView
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createBishops(context: Context, chessBoard: View) {
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
                    bishopsCoordinate[it], chessBoard
                ),
                PieceType.BISHOP,
                bishopView
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createKnights(context: Context, chessBoard: View) {
        val numberOfPieces = 4
        val bishopsCoordinate: ArrayList<ArrayList<Int>> =
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
                    bishopsCoordinate[it], chessBoard
                ),
                PieceType.KNIGHT,
                knightView
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, blackPieces.last())
            }
        }
    }

    fun ConstraintLayout.createRooks(context: Context, chessBoard: View) {
        val numberOfPieces = 4
        val bishopsCoordinate: ArrayList<ArrayList<Int>> =
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
                    bishopsCoordinate[it], chessBoard
                ),
                PieceType.ROOK,
                rookView
            )

            if (it < numberOfPieces / 2) {
                whitePieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, whitePieces.last())
            } else {
                blackPieces.add(chessPiece)
                positionPeaceOnBoardForNewGame(chessBoard, blackPieces.last())
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
        chessBoard: View,
        chessPiece: ChessPiece
    ) {
        chessPiece.pieceView.x =
            fromChessCoordinatesToPixelCoordinates(coordinates, chessBoard)[0].toFloat()
        chessPiece.pieceView.y =
            fromChessCoordinatesToPixelCoordinates(coordinates, chessBoard)[1].toFloat()
        chessPiece.coordinates = coordinates

    }

    private fun fromChessCoordinatesToPixelCoordinates(
        coordinates: ArrayList<Int>,
        chessBoard: View
    ): ArrayList<Int> {
        if (coordinates[0] !in 0..7 && coordinates[1] !in 0..7) return arrayListOf(-1, -1)
        val x = chessBoard.width / 16 + coordinates[0] * chessBoard.width / 8
        val y = chessBoard.height / 16 + coordinates[1] * chessBoard.height / 8
        return arrayListOf(x, y)
    }

    private fun fromPixelCoordinatesToChessCoordinates(
        coordinates: ArrayList<Int>,
        chessBoard: View
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
            in (chessBoard.height / 8)..(+chessBoard.height / 8 * 2) -> {
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