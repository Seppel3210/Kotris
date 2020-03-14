package gui

import CELL_SIZE
import NEXT_QUEUE_SIZE
import tetris.Piece
import tetris.RotationState
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.util.*
import javax.swing.JPanel

class NextQueue(private val nextQueue: ArrayDeque<Piece>) : JPanel() {
    init {
        preferredSize = Dimension(CELL_SIZE * 3, 0)
        background = Color.black
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
                g.fillRect((x + xoff) * CELL_SIZE / 2, (y * 3 + yoff + 3) * CELL_SIZE / 2, CELL_SIZE / 2, CELL_SIZE / 2)
            }
        }
    }
}