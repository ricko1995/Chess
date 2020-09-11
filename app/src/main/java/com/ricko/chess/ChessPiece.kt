package com.ricko.chess

import android.widget.ImageView
import java.util.*

data class ChessPiece(
    val pieceColor: PieceColor,
    var pixelCoordinates: ArrayList<Int> = arrayListOf(-1, -1),
    var pieceType: PieceType,
    val pieceView: ImageView?,
    var chessCoordinates: ArrayList<Int> = arrayListOf(-1, -1),
    var wasLastMoved: Boolean = false,
    val id: String = UUID.randomUUID().toString(),
    var wasPieceMoved: Boolean = false
) {
    fun pieceValue(): Int {
        return when (this.pieceType) {
            PieceType.KING -> 4
            PieceType.QUEEN -> 9
            PieceType.ROOK -> 5
            PieceType.KNIGHT -> 3
            PieceType.BISHOP -> 3
            PieceType.PAWN -> 1
        }
    }

    fun isValidMove(futureCoordinates: ArrayList<Int>, testForColor: Boolean = true): Boolean {
        val anyPieceOnFutureCoordinates = PieceManipulationHelper.isSomePieceAlreadyOnCoordinates(futureCoordinates)
        if (anyPieceOnFutureCoordinates?.pieceColor == this.pieceColor) return false
        val lastMovedPeace = PieceManipulationHelper.allChessPieces.find { it.wasLastMoved }
        if (testForColor) {
            lastMovedPeace?.let {
                if (it.pieceColor == this.pieceColor) return false
            }
        }
        return ValidMoves.isValidMoveForPawn(futureCoordinates, this) ||
                ValidMoves.isValidMoveForRookAndQueen(futureCoordinates, this) ||
                ValidMoves.isValidMoveForKnight(futureCoordinates, this) ||
                ValidMoves.isValidMoveForBishopAndQueen(futureCoordinates, this) ||
                ValidMoves.isValidMoveForKing(futureCoordinates, this)
    }
}

enum class PieceType {
    KING, QUEEN, ROOK, KNIGHT, BISHOP, PAWN
}

enum class PieceColor {
    BLACK, WHITE
}