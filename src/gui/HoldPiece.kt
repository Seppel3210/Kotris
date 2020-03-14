package gui

import CELL_SIZE
import MINI_PIECE_CELL_SIZE
import tetris.Board
import tetris.RotationState
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

class HoldPiece(private val board: Board) : JPanel() {

    init {
        preferredSize = Dimension(CELL_SIZE * 3, 0)
    }

    override fun paint(g: Graphics) {
        g.color = Color.black
        g.fillRect(0, 0, size.width, size.height)
        if (board.holdPiece == null) {
            return
        }
        val piece = board.holdPiece!!
        g.color = piece.color
        for ((xoff, yoff) in piece.getCells(RotationState.North)) {
            g.fillRect(
                (x + xoff + 2) * MINI_PIECE_CELL_SIZE,
                (y * 3 + yoff + 3) * MINI_PIECE_CELL_SIZE,
                MINI_PIECE_CELL_SIZE,
                MINI_PIECE_CELL_SIZE
            )
        }
    }
}