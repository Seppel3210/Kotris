package gui

import CELL_SIZE
import MINI_PIECE_CELL_SIZE
import NEXT_QUEUE_SIZE
import tetris.Board
import tetris.RotationState
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel

class NextQueue(board: Board) : JPanel() {
    private var nextQueue = board.nextQueue

    init {
        preferredSize = Dimension(CELL_SIZE * 3, 0)
    }

    override fun paint(g: Graphics) {
        g.color = Color.black
        g.fillRect(0, 0, size.width, size.height)
        val x = 2
        nextQueue.withIndex().forEach {
            if (it.index >= NEXT_QUEUE_SIZE) {
                return
            }
            val (y, piece) = it
            g.color = piece.color

            for ((xoff, yoff) in piece.getCells(RotationState.North)) {
                g.fillRect(
                    (x + xoff) * MINI_PIECE_CELL_SIZE,
                    (y * 3 + yoff + 3) * MINI_PIECE_CELL_SIZE,
                    MINI_PIECE_CELL_SIZE,
                    MINI_PIECE_CELL_SIZE
                )
            }
        }
    }
}