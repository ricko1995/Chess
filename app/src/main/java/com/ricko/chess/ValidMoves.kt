package com.ricko.chess

import android.view.View
import com.ricko.chess.PieceManipulationHelper.allChessPieces
import com.ricko.chess.PieceManipulationHelper.captureBlackPawnEnPassantOnLeft
import com.ricko.chess.PieceManipulationHelper.captureBlackPawnEnPassantOnRight
import com.ricko.chess.PieceManipulationHelper.captureWhitePawnEnPassantOnLeft
import com.ricko.chess.PieceManipulationHelper.captureWhitePawnEnPassantOnRight
import com.ricko.chess.PieceManipulationHelper.isSomePieceAlreadyOnCoordinates
import kotlin.math.abs

object ValidMoves {

    private var twoSquareMovePawn: ChessPiece? = null

    fun isValidMoveForPawn(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        val pastCoordinates = chessPiece.chessCoordinates
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureCoordinates)
        val lastMovedPeace = allChessPieces.find { it.wasLastMoved }
        lastMovedPeace?.let {
            if (it.pieceColor == chessPiece.pieceColor) return false
        }
        if (chessPiece.pieceType != PieceType.PAWN) return false

        when (chessPiece.pieceColor) {
            PieceColor.WHITE -> {
                if (futureCoordinates[0] == pastCoordinates[0] &&   //pawn move one square forward
                    futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    anyPieceOnFutureCoordinates == null
                ) return true

                if (
                    futureCoordinates[0] == pastCoordinates[0] &&   //pawn move two square forward
                    futureCoordinates[1] == 4 &&
                    pastCoordinates[1] == 6 &&
                    anyPieceOnFutureCoordinates == null
                ) {
                    twoSquareMovePawn = chessPiece
                    return true
                }

                if (
                    futureCoordinates[0] == pastCoordinates[0] + 1 &&  //pawn captures on right
                    futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    anyPieceOnFutureCoordinates != null &&
                    anyPieceOnFutureCoordinates.pieceColor == PieceColor.BLACK
                ) return true

                if (
                    futureCoordinates[0] == pastCoordinates[0] - 1 &&  //pawn captures on left
                    futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    anyPieceOnFutureCoordinates != null &&
                    anyPieceOnFutureCoordinates.pieceColor == PieceColor.BLACK
                ) return true

                if (
                    futureCoordinates[0] == pastCoordinates[0] + 1 &&  //pawn captures En passant on right
                    futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    anyPieceOnFutureCoordinates == null &&
                    twoSquareMovePawn != null &&
                    allChessPieces.find { it.wasLastMoved }?.id == twoSquareMovePawn?.id &&
                    twoSquareMovePawn!!.chessCoordinates[0] == futureCoordinates[0] &&
                    twoSquareMovePawn!!.chessCoordinates[1] == 3 &&
                    twoSquareMovePawn!!.pieceColor == PieceColor.BLACK
                ) {
                    captureBlackPawnEnPassantOnRight = true
                    return true
                }

                if (
                    futureCoordinates[0] == pastCoordinates[0] - 1 &&  //pawn captures En passant on left
                    futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    anyPieceOnFutureCoordinates == null &&
                    twoSquareMovePawn != null &&
                    allChessPieces.find { it.wasLastMoved }?.id == twoSquareMovePawn?.id &&
                    twoSquareMovePawn!!.chessCoordinates[0] == futureCoordinates[0] &&
                    twoSquareMovePawn!!.chessCoordinates[1] == 3 &&
                    twoSquareMovePawn!!.pieceColor == PieceColor.BLACK
                ) {
                    captureBlackPawnEnPassantOnLeft = true
                    return true
                }

            }
            PieceColor.BLACK -> {
                if (futureCoordinates[0] == pastCoordinates[0] &&   //pawn move one square forward
                    futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    anyPieceOnFutureCoordinates == null
                ) return true

                if (
                    futureCoordinates[0] == pastCoordinates[0] &&   //pawn move two square forward
                    futureCoordinates[1] == 3 &&
                    pastCoordinates[1] == 1 &&
                    anyPieceOnFutureCoordinates == null
                ) {
                    twoSquareMovePawn = chessPiece
                    return true
                }

                if (
                    futureCoordinates[0] == pastCoordinates[0] + 1 &&  //pawn captures on right
                    futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    anyPieceOnFutureCoordinates != null &&
                    anyPieceOnFutureCoordinates.pieceColor == PieceColor.WHITE
                ) return true

                if (
                    futureCoordinates[0] == pastCoordinates[0] - 1 &&  //pawn captures on left
                    futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    anyPieceOnFutureCoordinates != null &&
                    anyPieceOnFutureCoordinates.pieceColor == PieceColor.WHITE
                ) return true

                if (
                    futureCoordinates[0] == pastCoordinates[0] + 1 &&  //pawn captures En passant on right
                    futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    anyPieceOnFutureCoordinates == null &&
                    twoSquareMovePawn != null &&
                    allChessPieces.find { it.wasLastMoved }?.id == twoSquareMovePawn?.id &&
                    twoSquareMovePawn!!.chessCoordinates[0] == futureCoordinates[0] &&
                    twoSquareMovePawn!!.chessCoordinates[1] == 4 &&
                    twoSquareMovePawn!!.pieceColor == PieceColor.WHITE
                ) {
                    captureWhitePawnEnPassantOnRight = true
                    return true
                }

                if (
                    futureCoordinates[0] == pastCoordinates[0] - 1 &&  //pawn captures En passant on left
                    futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    anyPieceOnFutureCoordinates == null &&
                    twoSquareMovePawn != null &&
                    allChessPieces.find { it.wasLastMoved }?.id == twoSquareMovePawn?.id &&
                    twoSquareMovePawn!!.chessCoordinates[0] == futureCoordinates[0] &&
                    twoSquareMovePawn!!.chessCoordinates[1] == 4 &&
                    twoSquareMovePawn!!.pieceColor == PieceColor.WHITE
                ) {
                    captureWhitePawnEnPassantOnLeft = true
                    return true
                }

            }
        }
        return false
    }

    fun isValidMoveForRookAndQueen(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        val pastCoordinates = chessPiece.chessCoordinates
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureCoordinates)
        val lastMovedPeace = allChessPieces.find { it.wasLastMoved }
        if (chessPiece.pieceType != PieceType.ROOK && chessPiece.pieceType != PieceType.QUEEN) return false
        lastMovedPeace?.let {
            if (it.pieceColor == chessPiece.pieceColor) return false
        }
        if (anyPieceOnFutureCoordinates?.pieceColor == chessPiece.pieceColor) return false
        if (pastCoordinates[0] != futureCoordinates[0] && pastCoordinates[1] != futureCoordinates[1]) return false

        if (pastCoordinates[0] == futureCoordinates[0]) {
            if (pastCoordinates[1] < futureCoordinates[1]) {
                for (x in pastCoordinates[1] until futureCoordinates[1]) {
                    isSomePieceAlreadyOnCoordinates(arrayListOf(futureCoordinates[0], x))?.let {
                        if (it != chessPiece) return false
                    }
                }
            } else if (pastCoordinates[1] > futureCoordinates[1]) {
                for (x in pastCoordinates[1] downTo futureCoordinates[1] + 1) {
                    isSomePieceAlreadyOnCoordinates(arrayListOf(futureCoordinates[0], x))?.let {
                        if (it != chessPiece) return false
                    }
                }
            }
        }

        if (pastCoordinates[1] == futureCoordinates[1]) {
            if (pastCoordinates[0] < futureCoordinates[0]) {
                for (x in pastCoordinates[0] until futureCoordinates[0]) {
                    isSomePieceAlreadyOnCoordinates(arrayListOf(x, futureCoordinates[1]))?.let {
                        if (it != chessPiece) return false
                    }
                }
            } else if (pastCoordinates[0] > futureCoordinates[0]) {
                for (x in pastCoordinates[0] downTo futureCoordinates[0] + 1) {
                    isSomePieceAlreadyOnCoordinates(arrayListOf(x, futureCoordinates[1]))?.let {
                        if (it != chessPiece) return false
                    }

                }
            }
        }
        return true
    }

    fun isValidMoveForKnight(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureCoordinates)
        val lastMovedPeace = allChessPieces.find { it.wasLastMoved }
        if (chessPiece.pieceType != PieceType.KNIGHT) return false
        lastMovedPeace?.let {
            if (it.pieceColor == chessPiece.pieceColor) return false
        }
        if (anyPieceOnFutureCoordinates?.pieceColor == chessPiece.pieceColor) return false

        val currentCoordinatesX = chessPiece.chessCoordinates[0]
        val currentCoordinatesY = chessPiece.chessCoordinates[1]
        val validKnightCoordinates: ArrayList<ArrayList<Int>> =
            arrayListOf(
                arrayListOf(currentCoordinatesX + 1, currentCoordinatesY + 2),
                arrayListOf(currentCoordinatesX + 2, currentCoordinatesY + 1),
                arrayListOf(currentCoordinatesX - 1, currentCoordinatesY - 2),
                arrayListOf(currentCoordinatesX - 2, currentCoordinatesY - 1),
                arrayListOf(currentCoordinatesX - 1, currentCoordinatesY + 2),
                arrayListOf(currentCoordinatesX - 2, currentCoordinatesY + 1),
                arrayListOf(currentCoordinatesX + 1, currentCoordinatesY - 2),
                arrayListOf(currentCoordinatesX + 2, currentCoordinatesY - 1),
            )
        if (validKnightCoordinates.contains(futureCoordinates)) return true
        return false
    }

    fun isValidMoveForBishopAndQueen(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        val pastCoordinates = chessPiece.chessCoordinates
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureCoordinates)
        val lastMovedPeace = allChessPieces.find { it.wasLastMoved }

        lastMovedPeace?.let {
            if (it.pieceColor == chessPiece.pieceColor) return false
        }

        if (chessPiece.pieceType != PieceType.BISHOP && chessPiece.pieceType != PieceType.QUEEN) return false

        if (abs(futureCoordinates[0] - pastCoordinates[0]) != abs(futureCoordinates[1] - pastCoordinates[1])) return false

        if (pastCoordinates[0] < futureCoordinates[0] && pastCoordinates[1] < futureCoordinates[1]) {
            for (i in 0 until abs(futureCoordinates[0] - pastCoordinates[0])) {
                isSomePieceAlreadyOnCoordinates(arrayListOf(pastCoordinates[0] + i, pastCoordinates[1] + i))?.let {
                    if (it != chessPiece) return false
                }
            }
        } else if (pastCoordinates[0] > futureCoordinates[0] && pastCoordinates[1] < futureCoordinates[1]) {
            for (i in 0 until abs(futureCoordinates[0] - pastCoordinates[0])) {
                isSomePieceAlreadyOnCoordinates(arrayListOf(pastCoordinates[0] - i, pastCoordinates[1] + i))?.let {
                    if (it != chessPiece) return false
                }
            }
        } else if (pastCoordinates[0] < futureCoordinates[0] && pastCoordinates[1] > futureCoordinates[1]) {
            for (i in 0 until abs(futureCoordinates[0] - pastCoordinates[0])) {
                isSomePieceAlreadyOnCoordinates(arrayListOf(pastCoordinates[0] + i, pastCoordinates[1] - i))?.let {
                    if (it != chessPiece) return false
                }
            }
        } else if (pastCoordinates[0] > futureCoordinates[0] && pastCoordinates[1] > futureCoordinates[1]) {
            for (i in 0 until abs(futureCoordinates[0] - pastCoordinates[0])) {
                isSomePieceAlreadyOnCoordinates(arrayListOf(pastCoordinates[0] - i, pastCoordinates[1] - i))?.let {
                    if (it != chessPiece) return false
                }
            }
        }
        return true
    }

    fun isValidMoveForKing(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureCoordinates)
        val lastMovedPeace = allChessPieces.find { it.wasLastMoved }
        lastMovedPeace?.let {
            if (it.pieceColor == chessPiece.pieceColor) return false
        }
        if (chessPiece.pieceType != PieceType.KING) return false
        if (anyPieceOnFutureCoordinates?.pieceColor == chessPiece.pieceColor) return false

        val currentCoordinatesX = chessPiece.chessCoordinates[0]
        val currentCoordinatesY = chessPiece.chessCoordinates[1]
        val validKingCoordinates: ArrayList<ArrayList<Int>> =
            arrayListOf(
                arrayListOf(currentCoordinatesX, currentCoordinatesY + 1),
                arrayListOf(currentCoordinatesX, currentCoordinatesY - 1),
                arrayListOf(currentCoordinatesX + 1, currentCoordinatesY),
                arrayListOf(currentCoordinatesX - 1, currentCoordinatesY),
                arrayListOf(currentCoordinatesX + 1, currentCoordinatesY + 1),
                arrayListOf(currentCoordinatesX - 1, currentCoordinatesY - 1),
                arrayListOf(currentCoordinatesX + 1, currentCoordinatesY - 1),
                arrayListOf(currentCoordinatesX - 1, currentCoordinatesY + 1),
            )
        if (validKingCoordinates.contains(futureCoordinates)) return true
        return false
    }

//    private fun kingInCheck(): Boolean {
//        val lastMovedPeaceColor = allChessPieces.find { it.wasLastMoved }?.pieceColor
//        lastMovedPeaceColor?.let {
//            val whiteOrBlackPieces = allChessPieces.filter { it.pieceColor != lastMovedPeaceColor }
//            val blackOrWhiteKing = allChessPieces.filter { it.pieceType == PieceType.KING }.find { it.pieceColor == lastMovedPeaceColor }
//            for (piece in whiteOrBlackPieces) {
//                if (piece.anyValidMove(blackOrWhiteKing!!.chessCoordinates)) {
//                    return true
//                }
//            }
//        }
//        return false
//    }
}