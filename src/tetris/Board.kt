package tetris

import CELL_SIZE
import java.awt.Color
import java.awt.Graphics
import java.util.*
import kotlin.collections.ArrayList

class Board(private val nextQueueSize: Int) {
    private var cells: LinkedList<Row> = LinkedList(MutableList(40) { Row.EMPTY })

    val nextQueue = ArrayDeque<Piece>()

    var holdPiece: Piece? = null
        private set

    fun hold(piece: Piece): Piece {
        return if (holdPiece == null) {
            holdPiece = piece
            nextPiece
        } else {
            val tmp = holdPiece!!
            holdPiece = piece
            tmp
        }
    }

    private val nextBag: Collection<Piece>
        get() {
            val bag = mutableListOf(
                Piece.I,
                Piece.J,
                Piece.L,
                Piece.O,
                Piece.S,
                Piece.T,
                Piece.Z
            )
            bag.shuffle()
            return bag
        }

    val nextPiece: Piece
        get() {
            while (nextQueue.size <= nextQueueSize) {
                nextQueue.addAll(nextBag)
            }
            return nextQueue.removeFirst()
        }

    fun render(g: Graphics) {
        for ((y, row) in cells.withIndex()) {
            for ((x, cell) in row.cells.withIndex()) {
                g.color = cell.color
                g.fillRect(x * CELL_SIZE, (y - 20) * CELL_SIZE, CELL_SIZE, CELL_SIZE)
            }
        }

    }

    private fun occupied(x: Int, y: Int): Boolean {
        return x < 0 || y < 0 || x >= 10 || y >= 40 || cells[y].cells[x] != CellColor.Empty
    }

    fun obstructed(piece: FallingPiece): Boolean {
        piece.cells.iterator().forEach {
            if (occupied(it.first, it.second))
                return true
        }
        return false
    }

    fun lock(piece: FallingPiece) {
        val cellColor = when (piece.kind) {
            Piece.I -> CellColor.I
            Piece.O -> CellColor.O
            Piece.J -> CellColor.J
            Piece.L -> CellColor.L
            Piece.S -> CellColor.S
            Piece.Z -> CellColor.Z
            Piece.T -> CellColor.T
        }
        for ((x, y) in piece.cells) {
            cells[y].cells[x] = cellColor
        }
        val toRemove = ArrayList<Row>()
        for (row in cells) {
            if (row.isfull) {
                toRemove.add(row)
            }
        }
        repeat(toRemove.size) { cells.addFirst(Row.EMPTY) }
        cells.removeAll(toRemove)
    }
}

private class Row private constructor(val cells: Array<CellColor>) {

    val isfull: Boolean
        get() {
            for (cell in cells) {
                if (cell == CellColor.Empty || cell == CellColor.Unclearable)
                    return false
            }
            return true
        }

    companion object {
        val EMPTY
            get() = Row(Array(10) { CellColor.Empty })
        val SOLID = Row(Array(10) { CellColor.Unclearable })
    }
}

enum class CellColor(val color: Color) {
    I(Color(0x00ffff)),
    O(Color(0xffff00)),
    T(Color(0xBA00BA)),
    S(Color(0x00ff00)),
    Z(Color(0xff0000)),
    J(Color(0x0000ff)),
    L(Color(0xffa500)),
    Garbage(Color.gray),
    Unclearable(Color.gray),
    Empty(Color(0, true))
}