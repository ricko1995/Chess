package com.ricko.chess

import com.ricko.chess.PieceManipulationHelper.allChessPieces
import com.ricko.chess.PieceManipulationHelper.captureBlackPawnEnPassantOnLeft
import com.ricko.chess.PieceManipulationHelper.captureBlackPawnEnPassantOnRight
import com.ricko.chess.PieceManipulationHelper.captureWhitePawnEnPassantOnLeft
import com.ricko.chess.PieceManipulationHelper.captureWhitePawnEnPassantOnRight
import com.ricko.chess.PieceManipulationHelper.isBlackKingInCheck
import com.ricko.chess.PieceManipulationHelper.isValidMoveToBlockCheck
import com.ricko.chess.PieceManipulationHelper.isSomePieceAlreadyOnCoordinates
import com.ricko.chess.PieceManipulationHelper.isWhiteKingInCheck
import com.ricko.chess.PieceManipulationHelper.updateMovedPiece
import kotlin.math.abs

object ValidMoves {

    private var twoSquareMovePawn: ChessPiece? = null

    fun isValidMoveForPawn(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        if (chessPiece.pieceType != PieceType.PAWN) return false
        val pastCoordinates = chessPiece.chessCoordinates
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureCoordinates)

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
                    pastCoordinates[1] == 3 &&
                    futureCoordinates[1] == 2 &&
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
                    pastCoordinates[1] == 3 &&
                    futureCoordinates[1] == 2 &&
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
                    pastCoordinates[1] == 4 &&
                    futureCoordinates[1] == 5 &&
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
                    pastCoordinates[1] == 4 &&
                    futureCoordinates[1] == 5 &&
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
        if (chessPiece.pieceType != PieceType.ROOK && chessPiece.pieceType != PieceType.QUEEN) return false
        val pastCoordinates = chessPiece.chessCoordinates
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
        if (chessPiece.pieceType != PieceType.KNIGHT) return false

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
        if (chessPiece.pieceType != PieceType.BISHOP && chessPiece.pieceType != PieceType.QUEEN) return false

        val pastCoordinates = chessPiece.chessCoordinates
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
        if (chessPiece.pieceType != PieceType.KING) return false

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
        val kingSideCastleCoordinates = arrayListOf(currentCoordinatesX + 2, currentCoordinatesY)
        val queenSideCastleCoordinates = arrayListOf(currentCoordinatesX - 2, currentCoordinatesY)

        val kingSideRook = allChessPieces.find { it.chessCoordinates == arrayListOf(7, currentCoordinatesY) }
        val queenSideRook = allChessPieces.find { it.chessCoordinates == arrayListOf(0, currentCoordinatesY) }

        if (validKingCoordinates.contains(futureCoordinates)) return true

        if (futureCoordinates == kingSideCastleCoordinates) {
            if (if (chessPiece.pieceColor == PieceColor.WHITE) isWhiteKingInCheck() != null else isBlackKingInCheck() != null) return false
            kingSideRook?.let { rook ->
                val isRookValid = rook.pieceType == PieceType.ROOK && !rook.wasPieceMoved
                val isKingValid = !chessPiece.wasPieceMoved
                val isPathToCastleEmpty = isSomePieceAlreadyOnCoordinates(arrayListOf(5, currentCoordinatesY)) == null &&
                        isSomePieceAlreadyOnCoordinates(arrayListOf(6, currentCoordinatesY)) == null

                if (isKingValid && isRookValid && isPathToCastleEmpty) {
                    if (isValidMoveToBlockCheck(arrayListOf(5, currentCoordinatesY), chessPiece) &&
                        isValidMoveToBlockCheck(arrayListOf(6, currentCoordinatesY), chessPiece)
                    ) {
                        updateMovedPiece(arrayListOf(5, currentCoordinatesY), rook)
                        return true
                    }
                }

            }
        }
        if (futureCoordinates == queenSideCastleCoordinates) {
            if (if (chessPiece.pieceColor == PieceColor.WHITE) isWhiteKingInCheck() != null else isBlackKingInCheck() != null) return false
            queenSideRook?.let { rook ->
                val isRookValid = rook.pieceType == PieceType.ROOK && !rook.wasPieceMoved
                val isKingValid = !chessPiece.wasPieceMoved
                val isPathToCastleEmpty = isSomePieceAlreadyOnCoordinates(arrayListOf(3, currentCoordinatesY)) == null &&
                        isSomePieceAlreadyOnCoordinates(arrayListOf(2, currentCoordinatesY)) == null &&
                        isSomePieceAlreadyOnCoordinates(arrayListOf(1, currentCoordinatesY)) == null

                if (isKingValid && isRookValid && isPathToCastleEmpty) {
                    if (isValidMoveToBlockCheck(arrayListOf(3, currentCoordinatesY), chessPiece) &&
                        isValidMoveToBlockCheck(arrayListOf(2, currentCoordinatesY), chessPiece)
                    ) {
                        updateMovedPiece(arrayListOf(3, currentCoordinatesY), rook)
                        return true
                    }
                }

            }
        }
        return false
    }
}