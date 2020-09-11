package com.ricko.chess

import android.view.View
import java.util.*
import kotlin.collections.ArrayList

data class ChessPiece(
    val pieceColor: PieceColor,
    var pixelCoordinates: ArrayList<Int> = arrayListOf(-1, -1),
    var pieceType: PieceType,
    val pieceView: View?,
    var chessCoordinates: ArrayList<Int> = arrayListOf(-1, -1),
    var wasLastMoved: Boolean = false,
    val id: String = UUID.randomUUID().toString()
) {
    val pieceValue: Int by lazy {
        when (pieceType) {
            PieceType.KING -> 4
            PieceType.QUEEN -> 9
            PieceType.ROOK -> 5
            PieceType.KNIGHT -> 3
            PieceType.BISHOP -> 3
            PieceType.PAWN -> 1
        }
    }
    var isKingInCheck = false

    fun anyValidMove(futureCoordinates: ArrayList<Int>): Boolean {
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