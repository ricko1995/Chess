package com.ricko.chess

import com.ricko.chess.PieceManipulationHelper.isSomePieceAlreadyOnCoordinates

object ValidMoves {

    fun isValidMoveForPawn(
        futureCoordinates: ArrayList<Int>,
        chessPiece: ChessPiece
    ): Boolean {
        val pastCoordinates = chessPiece.chessCoordinates
        println(isSomePieceAlreadyOnCoordinates(
            arrayListOf(
                futureCoordinates[0] + 1,
                futureCoordinates[0]
            )
        ) )
        if (chessPiece.pieceType != PieceType.PAWN) return false
        when (chessPiece.pieceColor) {
            PieceColor.WHITE -> {
                if (futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    futureCoordinates[0] == pastCoordinates[0] &&
                    isSomePieceAlreadyOnCoordinates(futureCoordinates) == null
                ) return true

                if (futureCoordinates[1] == 4 &&
                    futureCoordinates[1] == pastCoordinates[1] - 2 &&
                    futureCoordinates[0] == pastCoordinates[0] &&
                    isSomePieceAlreadyOnCoordinates(futureCoordinates) == null
                ) return true

                if (futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    futureCoordinates[0] == pastCoordinates[0] + 1 &&
                    isSomePieceAlreadyOnCoordinates(
                        arrayListOf(
                            futureCoordinates[0] + 1,
                            futureCoordinates[0]
                        )
                    ) != null &&
                    isSomePieceAlreadyOnCoordinates(
                        arrayListOf(
                            futureCoordinates[0] + 1,
                            futureCoordinates[0]
                        )
                    )!!.pieceColor == PieceColor.BLACK
                ) {

                    return true
                }

                if (futureCoordinates[1] == pastCoordinates[1] - 1 &&
                    futureCoordinates[0] == pastCoordinates[0] - 1 &&
                    isSomePieceAlreadyOnCoordinates(
                        arrayListOf(
                            futureCoordinates[0] - 1,
                            futureCoordinates[0]
                        )
                    ) != null &&
                    isSomePieceAlreadyOnCoordinates(
                        arrayListOf(
                            futureCoordinates[0] - 1,
                            futureCoordinates[0]
                        )
                    )!!.pieceColor == PieceColor.BLACK
                ) return true
            }
            PieceColor.BLACK -> {
                if (futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    futureCoordinates[0] == pastCoordinates[0] &&
                    isSomePieceAlreadyOnCoordinates(futureCoordinates) == null
                ) return true

                if (futureCoordinates[1] == 3 &&
                    futureCoordinates[1] == pastCoordinates[1] + 2 &&
                    futureCoordinates[0] == pastCoordinates[0] &&
                    isSomePieceAlreadyOnCoordinates(futureCoordinates) == null
                ) return true

                if (futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    futureCoordinates[0] == pastCoordinates[0] + 1 &&
                    isSomePieceAlreadyOnCoordinates(
                        arrayListOf(
                            futureCoordinates[0] + 1,
                            futureCoordinates[0]
                        )
                    ) != null
                ) return true

                if (futureCoordinates[1] == pastCoordinates[1] + 1 &&
                    futureCoordinates[0] == pastCoordinates[0] - 1 &&
                    isSomePieceAlreadyOnCoordinates(
                        arrayListOf(
                            futureCoordinates[0] - 1,
                            futureCoordinates[0]
                        )
                    ) != null
                ) return true
            }
        }

        return false
    }
}