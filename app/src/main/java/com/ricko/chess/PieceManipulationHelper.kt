package com.ricko.chess

import android.app.Dialog
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.ricko.chess.MainActivity.Companion.activityLayout
import com.ricko.chess.MainActivity.Companion.chessBoard
import com.ricko.chess.MainActivity.Companion.context
import com.ricko.chess.ValidMoves.isValidMoveForPawn
import kotlinx.android.synthetic.main.game_over_dialog.*
import kotlinx.android.synthetic.main.promotion_dialog.*
import java.util.*
import kotlin.collections.ArrayList

object PieceManipulationHelper {

    val allChessPieces: ArrayList<ChessPiece> = arrayListOf()
    var captureBlackPawnEnPassantOnLeft = false
    var captureBlackPawnEnPassantOnRight = false
    var captureWhitePawnEnPassantOnLeft = false
    var captureWhitePawnEnPassantOnRight = false
    private const val pieceDimensionDivider = 9

    fun ConstraintLayout.startNewGame() {
        if (allChessPieces.isNotEmpty()) {
            removeAllPiecesFromBoard()
            getViewById(R.id.txtNextOnMove).visibility = View.GONE
        }
        createPawns()
        createKings()
        createQueens()
        createBishops()
        createKnights()
        createRooks()
    }

    private fun ConstraintLayout.positionPeaceOnBoardForNewGame(
        chessPiece: ChessPiece
    ) {
        addView(chessPiece.pieceView)
        val boardLayoutParams = ConstraintLayout.LayoutParams(
            chessBoard.width / pieceDimensionDivider,
            chessBoard.height / pieceDimensionDivider
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

    private fun ConstraintLayout.createPawns() {
        val numberOfPieces = 16
        repeat(numberOfPieces) {
            val pawnView = ImageView(context)
            pawnView.id = View.generateViewId()
            pawnView.setImageResource(if (it < numberOfPieces / 2) R.drawable.ic_white_pawn else R.drawable.ic_black_pawn)
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

    private fun ConstraintLayout.createKings() {
        val numberOfPieces = 2
        repeat(numberOfPieces) {
            val kingView = ImageView(context)
            kingView.id = View.generateViewId()
            kingView.setImageResource(if (it < numberOfPieces / 2) R.drawable.ic_white_king else R.drawable.ic_black_king)
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

    private fun ConstraintLayout.createQueens() {
        val numberOfPieces = 2
        repeat(numberOfPieces) {
            val queenView = ImageView(context)
            queenView.id = View.generateViewId()
            queenView.setImageResource(if (it < numberOfPieces / 2) R.drawable.ic_white_queen else R.drawable.ic_black_queen)
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

    private fun ConstraintLayout.createBishops() {
        val numberOfPieces = 4
        val bishopsCoordinate: ArrayList<ArrayList<Int>> =
            arrayListOf(arrayListOf(2, 7), arrayListOf(5, 7), arrayListOf(2, 0), arrayListOf(5, 0))
        repeat(numberOfPieces) {
            val bishopView = ImageView(context)
            bishopView.id = View.generateViewId()
            bishopView.setImageResource(if (it < numberOfPieces / 2) R.drawable.ic_white_bishop else R.drawable.ic_black_bishop)
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

    private fun ConstraintLayout.createKnights() {
        val numberOfPieces = 4
        val knightsCoordinate: ArrayList<ArrayList<Int>> =
            arrayListOf(arrayListOf(1, 7), arrayListOf(6, 7), arrayListOf(1, 0), arrayListOf(6, 0))
        repeat(numberOfPieces) {
            val knightView = ImageView(context)
            knightView.id = View.generateViewId()
            knightView.setImageResource(if (it < numberOfPieces / 2) R.drawable.ic_white_knight else R.drawable.ic_black_knight)
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

    private fun ConstraintLayout.createRooks() {
        val numberOfPieces = 4
        val rooksCoordinate: ArrayList<ArrayList<Int>> =
            arrayListOf(arrayListOf(0, 7), arrayListOf(7, 7), arrayListOf(0, 0), arrayListOf(7, 0))
        repeat(numberOfPieces) {
            val rookView = ImageView(context)
            rookView.id = View.generateViewId()
            rookView.setImageResource(if (it < numberOfPieces / 2) R.drawable.ic_white_rook else R.drawable.ic_black_rook)
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

    private fun ConstraintLayout.removeAllPiecesFromBoard() {
        for (piece in allChessPieces) {
            removeView(piece.pieceView)
        }
        allChessPieces.clear()
    }

    fun isBlackKingInCheck(): ChessPiece? {
        val king = allChessPieces.filter { it.pieceType == PieceType.KING }.find { it.pieceColor == PieceColor.BLACK }
        val whitePieces = allChessPieces.filter { it.pieceColor == PieceColor.WHITE }

        for (piece in whitePieces) {
            if (piece.isValidMove(king!!.chessCoordinates, false)) {
                checkIfCheckMat(PieceColor.BLACK)
                return king
            }
        }
        return null
    }

    fun isWhiteKingInCheck(): ChessPiece? {
        val king = allChessPieces.filter { it.pieceType == PieceType.KING }.find { it.pieceColor == PieceColor.WHITE }
        val blackPieces = allChessPieces.filter { it.pieceColor == PieceColor.BLACK }

        for (piece in blackPieces) {
            if (piece.isValidMove(king!!.chessCoordinates, false)) {
                checkIfCheckMat(PieceColor.WHITE)
                return king
            }
        }
        return null
    }

    private fun checkIfCheckMat(color: PieceColor) {
        val pieces = allChessPieces.filter { it.pieceColor == color }
        for (piece in pieces) {
            for (i in 0..7) {
                for (j in 0..7) {
                    if (piece.isValidMove(arrayListOf(i, j), false)) {
                        if (isValidMoveToBlockCheck(arrayListOf(i, j), piece)) {
                            return
                        }
                    }
                }
            }
        }
        openDialogForGameOver(if (color == PieceColor.BLACK) PieceColor.WHITE else PieceColor.BLACK)
    }

    private fun openDialogForGameOver(victoryColor: PieceColor) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.game_over_dialog)
        dialog.btnResetBoard.setOnClickListener {
            activityLayout.startNewGame()
            dialog.cancel()
        }
        val color = victoryColor.name.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT)
        val msgToWinner = "$color wins the game!"
        dialog.txtMessageToWinner.text = msgToWinner
        dialog.show()
    }

    fun isValidMoveToBlockCheck(futureChessCoordinates: ArrayList<Int>, chessPieceToMove: ChessPiece): Boolean {
        val king = allChessPieces.filter { it.pieceType == PieceType.KING }.find { it.pieceColor == chessPieceToMove.pieceColor }
        val dummyPiece = ChessPiece(chessPieceToMove.pieceColor, futureChessCoordinates, chessPieceToMove.pieceType, null, futureChessCoordinates)
        val isPieceToMoveKing = king == chessPieceToMove
        val isItCapture = isSomePieceAlreadyOnCoordinates(futureChessCoordinates)
        isItCapture?.let {
            allChessPieces.remove(it)
        }
        allChessPieces.add(dummyPiece)
        allChessPieces.remove(chessPieceToMove)
        var bool = true

        for (piece in allChessPieces.filter { it.pieceColor != chessPieceToMove.pieceColor }) {
            king?.let {
                if (piece.isValidMove(if (isPieceToMoveKing) dummyPiece.chessCoordinates else king.chessCoordinates, false)) {
                    bool = false
                }
            }
        }
        allChessPieces.remove(dummyPiece)
        allChessPieces.add(chessPieceToMove)
        isItCapture?.let {
            allChessPieces.add(it)
        }
        return bool
    }

    fun updateMovedPiece(
        futureChessCoordinates: ArrayList<Int>,
        chessPieceToMove: ChessPiece
    ) {

        if (!isValidMoveToBlockCheck(futureChessCoordinates, chessPieceToMove)) {
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
        chessPieceToMove.wasPieceMoved = true
        for (piece in allChessPieces) {
            if (piece.wasLastMoved) {
                piece.pieceView?.background = ContextCompat.getDrawable(context, R.drawable.circle_blue)
            } else piece.pieceView?.background = ContextCompat.getDrawable(context, R.drawable.no_background)

        }

        if (chessPieceToMove.pieceType == PieceType.PAWN && (chessPieceToMove.chessCoordinates[1] == 0 || chessPieceToMove.chessCoordinates[1] == 7)) {
            openDialogForPiecePromotion(chessPieceToMove)
        }
    }

    private fun openDialogForPiecePromotion(chessPieceToMove: ChessPiece) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.promotion_dialog)
        dialog.btnQueen.setOnClickListener {
            chessPieceToMove.pieceView?.setImageResource(
                if (chessPieceToMove.pieceColor == PieceColor.WHITE) R.drawable.ic_white_queen else R.drawable.ic_black_queen
            )
            chessPieceToMove.pieceType = PieceType.QUEEN
            dialog.cancel()
        }
        dialog.btnRook.setOnClickListener {
            chessPieceToMove.pieceView?.setImageResource(
                if (chessPieceToMove.pieceColor == PieceColor.WHITE) R.drawable.ic_white_rook else R.drawable.ic_black_rook
            )
            chessPieceToMove.pieceType = PieceType.ROOK
            dialog.cancel()
        }
        dialog.btnBishop.setOnClickListener {
            chessPieceToMove.pieceView?.setImageResource(
                if (chessPieceToMove.pieceColor == PieceColor.WHITE) R.drawable.ic_white_bishop else R.drawable.ic_black_bishop
            )
            chessPieceToMove.pieceType = PieceType.BISHOP
            dialog.cancel()
        }
        dialog.btnKnight.setOnClickListener {
            chessPieceToMove.pieceView?.setImageResource(
                if (chessPieceToMove.pieceColor == PieceColor.WHITE) R.drawable.ic_white_knight else R.drawable.ic_black_knight
            )
            chessPieceToMove.pieceType = PieceType.KNIGHT
            dialog.cancel()
        }
        dialog.show()
    }

    fun movePieceToCoordinates(
        futurePixelCoordinates: ArrayList<Int>,
        chessPieceToMove: ChessPiece
    ) {
        val futureChessCoordinates = fromPixelCoordinatesToChessCoordinates(futurePixelCoordinates)
        val anyPieceOnFutureCoordinates = isSomePieceAlreadyOnCoordinates(futureChessCoordinates)
        val isValidMoveForPawn = isValidMoveForPawn(futureChessCoordinates, chessPieceToMove)
        val isValidMove = chessPieceToMove.isValidMove(futureChessCoordinates)

        anyPieceOnFutureCoordinates?.let {
            if (it.pieceColor != chessPieceToMove.pieceColor && isValidMove) {
                updateMovedPiece(futureChessCoordinates, chessPieceToMove)
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
        if (isValidMove) {
            updateMovedPiece(futureChessCoordinates, chessPieceToMove)
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
        val pieceDimension = chessBoard.height / pieceDimensionDivider
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