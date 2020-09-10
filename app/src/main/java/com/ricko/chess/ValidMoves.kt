package com.ricko.chess

import com.ricko.chess.PieceManipulationHelper.isSomePieceAlreadyOnCoordinates

object ValidMoves {

    fun isValidMoveForPawn(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        val pastCoordinates = chessPiece.chessCoordinates
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureCoordinates)
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
                ) return true
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
                ) return true
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

            }
        }
        return false
    }

    fun isValidMoveForRook(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {


        return false
    }

    fun isValidMoveForKnight(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {


        return false
    }

    fun isValidMoveForBishop(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {


        return false
    }

    fun isValidMoveForQueen(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {


        return false
    }

    fun isValidMoveForKing(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {


        return false
    }
}