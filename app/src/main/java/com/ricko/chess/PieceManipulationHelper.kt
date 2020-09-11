package com.ricko.chess

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ricko.chess.MainActivity.Companion.activityLayout
import com.ricko.chess.MainActivity.Companion.chessBoard
import com.ricko.chess.ValidMoves.isValidMoveForPawn
import kotlin.collections.ArrayList

object PieceManipulationHelper {

    val allChessPieces: ArrayList<ChessPiece> = arrayListOf()
    var captureBlackPawnEnPassantOnLeft = false
    var captureBlackPawnEnPassantOnRight = false
    var captureWhitePawnEnPassantOnLeft = false
    var captureWhitePawnEnPassantOnRight = false

    private fun ConstraintLayout.positionPeaceOnBoardForNewGame(
        chessPiece: ChessPiece
    ) {
        addView(chessPiece.pieceView)
        val boardLayoutParams = ConstraintLayout.LayoutParams(
            chessBoard.width / 10,
            chessBoard.height / 10
        )
        chessPiece.pieceView?.let {
            it.apply {
                layoutParams = boardLayoutParams
                x = chessPiece.pixelCoordinates[0].toFloat()
                y = chessPiece.pixelCoordinates[1].toFloat()
            }
        }
        if (chessPiece.pieceColor == PieceColor.BLACK) chessPiece.pieceView?.rotationX = 180f

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
            allChessPieces.add(chessPiece)
            positionPeaceOnBoardForNewGame(allChessPieces.last())
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
                        4,
                        if (it < numberOfPieces / 2) 7 else 0
                    )
                ),
                PieceType.KING,
                kingView,
                arrayListOf(
                    4,
                    if (it < numberOfPieces / 2) 7 else 0
                )
            )
            allChessPieces.add(chessPiece)
            positionPeaceOnBoardForNewGame(allChessPieces.last())
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
                        3,
                        if (it < numberOfPieces / 2) 7 else 0
                    )
                ),
                PieceType.QUEEN,
                queenView,
                arrayListOf(
                    3,
                    if (it < numberOfPieces / 2) 7 else 0
                )
            )
            allChessPieces.add(chessPiece)
            positionPeaceOnBoardForNewGame(allChessPieces.last())
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
            allChessPieces.add(chessPiece)
            positionPeaceOnBoardForNewGame(allChessPieces.last())
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
            allChessPieces.add(chessPiece)
            positionPeaceOnBoardForNewGame(allChessPieces.last())
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
            allChessPieces.add(chessPiece)
            positionPeaceOnBoardForNewGame(allChessPieces.last())
        }
    }

    fun ConstraintLayout.removeAllPiecesFromBoard() {
        for (piece in allChessPieces) {
            removeView(piece.pieceView)
        }
        allChessPieces.clear()
    }

    private fun isKingInCheck(futureChessCoordinates: ArrayList<Int>, chessPieceToMove: ChessPiece): Boolean {
        val king = allChessPieces.filter { it.pieceType == PieceType.KING }.find { it.pieceColor == chessPieceToMove.pieceColor }
        val dummyPiece = ChessPiece(chessPieceToMove.pieceColor, futureChessCoordinates, chessPieceToMove.pieceType, null, futureChessCoordinates)
        allChessPieces.add(dummyPiece)
        var bool = false

        for (piece in allChessPieces.filter { it.pieceColor != chessPieceToMove.pieceColor }) {
            if (piece.anyValidMove(king!!.chessCoordinates)) {
                bool = true
            }
        }
        allChessPieces.remove(dummyPiece)
        return bool
    }

    private fun updateMovedPiece(
        chessPieceToMove: ChessPiece,
        futureChessCoordinates: ArrayList<Int>
    ) {
        if (isKingInCheck(futureChessCoordinates, chessPieceToMove)) {
            resetPeacePosition(chessPieceToMove)
            return
        }

        val normalizedFuturePixelCoordinates =
            fromChessCoordinatesToPixelCoordinates(futureChessCoordinates)
        activityLayout.capturePieceOnCoordinates(futureChessCoordinates)
        chessPieceToMove.pieceView?.x = normalizedFuturePixelCoordinates[0].toFloat()
        chessPieceToMove.pieceView?.y = normalizedFuturePixelCoordinates[1].toFloat()
        chessPieceToMove.pixelCoordinates = normalizedFuturePixelCoordinates
        chessPieceToMove.chessCoordinates = futureChessCoordinates


        for (piece in allChessPieces) {
            piece.wasLastMoved = false
        }
        chessPieceToMove.wasLastMoved = true
    }

    fun movePieceToCoordinates(
        futurePixelCoordinates: ArrayList<Int>,
        chessPieceToMove: ChessPiece
    ) {
        val futureChessCoordinates = fromPixelCoordinatesToChessCoordinates(futurePixelCoordinates)
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureChessCoordinates)
        val isValidMoveForPawn = isValidMoveForPawn(futureChessCoordinates, chessPieceToMove)
        val anyValidMove = chessPieceToMove.anyValidMove(futureChessCoordinates)

        anyPieceOnFutureCoordinates?.let {
            if (it.pieceColor != chessPieceToMove.pieceColor && anyValidMove) {
                updateMovedPiece(chessPieceToMove, futureChessCoordinates)
                return
            } else {
                resetPeacePosition(chessPieceToMove)
                return
            }
        }

        if (isValidMoveForPawn) {
            when (true) {
                captureBlackPawnEnPassantOnLeft -> {
                    activityLayout.capturePieceOnCoordinates(
                        arrayListOf(
                            futureChessCoordinates[0],
                            futureChessCoordinates[1] + 1
                        )
                    )
                    captureBlackPawnEnPassantOnLeft = false
                }
                captureBlackPawnEnPassantOnRight -> {
                    activityLayout.capturePieceOnCoordinates(
                        arrayListOf(
                            futureChessCoordinates[0],
                            futureChessCoordinates[1] + 1
                        )
                    )
                    captureBlackPawnEnPassantOnRight = false
                }
                captureWhitePawnEnPassantOnLeft -> {
                    activityLayout.capturePieceOnCoordinates(
                        arrayListOf(
                            futureChessCoordinates[0],
                            futureChessCoordinates[1] - 1
                        )
                    )
                    captureWhitePawnEnPassantOnLeft = false
                }
                captureWhitePawnEnPassantOnRight -> {
                    activityLayout.capturePieceOnCoordinates(
                        arrayListOf(
                            futureChessCoordinates[0],
                            futureChessCoordinates[1] - 1
                        )
                    )
                    captureWhitePawnEnPassantOnRight = false
                }
            }
        }
        if (anyValidMove) {
            updateMovedPiece(chessPieceToMove, futureChessCoordinates)
        } else resetPeacePosition(chessPieceToMove)

    }

    private fun ConstraintLayout.capturePieceOnCoordinates(chessCoordinates: ArrayList<Int>) {
        if (isSomePieceAlreadyOnCoordinates(chessCoordinates) == null) return
        var toRemove: ChessPiece? = null
        for (piece in allChessPieces) {
            if (piece.chessCoordinates == chessCoordinates) {
                toRemove = piece
            }
        }
        toRemove?.let {
            removeView(it.pieceView)
            allChessPieces.remove(it)
        }

    }

    fun isSomePieceAlreadyOnCoordinates(chessCoordinates: ArrayList<Int>): ChessPiece? {
        for (piece in allChessPieces) {
            if (piece.chessCoordinates == chessCoordinates) return piece
        }
        return null
    }

    fun getClosestPeace(pixelCoordinates: ArrayList<Int>): ChessPiece? {
        val chessCoordinates = fromPixelCoordinatesToChessCoordinates(pixelCoordinates)
        for (piece in allChessPieces) {
            if (piece.chessCoordinates == chessCoordinates) {
                return piece
            }
        }
        return null
    }

    fun resetPeacePosition(chessPiece: ChessPiece) {
        chessPiece.pieceView?.apply {
            x = chessPiece.pixelCoordinates[0].toFloat()
            y = chessPiece.pixelCoordinates[1].toFloat()
        }
    }

    private fun fromChessCoordinatesToPixelCoordinates(
        chessCoordinates: ArrayList<Int>
    ): ArrayList<Int> {
        val pieceDimension = chessBoard.height / 10
        if (chessCoordinates[0] !in 0..7 && chessCoordinates[1] !in 0..7) return arrayListOf(-1, -1)
        val x =
            chessBoard.width / 16 + chessCoordinates[0] * chessBoard.width / 8 - pieceDimension / 2
        val y =
            chessBoard.height / 16 + chessCoordinates[1] * chessBoard.height / 8 + chessBoard.y.toInt() - pieceDimension / 2
        return arrayListOf(x, y)
    }

    private fun fromPixelCoordinatesToChessCoordinates(
        pixelCoordinates: ArrayList<Int>
    ): ArrayList<Int> {
        val x: Int
        val y: Int
        when (pixelCoordinates[0]) {
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

        when (pixelCoordinates[1]) {
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