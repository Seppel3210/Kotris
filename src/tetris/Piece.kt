package tetris

import CELL_SIZE
import GHOST_PIECE_OPACITY
import java.awt.Color
import java.awt.Graphics

enum class Piece(private val cellOffsets: Array<Array<Pair<Int, Int>>>) {
    I(
        arrayOf(
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(1, 0), Pair(2, 0)),
            arrayOf(Pair(0, -1), Pair(0, 0), Pair(0, 1), Pair(0, 2)),
            arrayOf(Pair(-2, 0), Pair(-1, 0), Pair(0, 0), Pair(1, 0)),
            arrayOf(Pair(0, -2), Pair(0, -1), Pair(0, 0), Pair(0, 1))
        )
    ),
    O(
        arrayOf(
            arrayOf(Pair(0, -1), Pair(1, -1), Pair(1, 0), Pair(0, 0)),
            arrayOf(Pair(0, 0), Pair(1, 0), Pair(1, 1), Pair(0, 1)),
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(0, 1), Pair(-1, 1)),
            arrayOf(Pair(-1, -1), Pair(0, -1), Pair(0, 0), Pair(-1, 0))
        )
    ),
    J(
        arrayOf(
            arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(0, 0), Pair(1, 0)),
            arrayOf(Pair(0, 1), Pair(0, 0), Pair(0, -1), Pair(1, -1)),
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(1, 0), Pair(1, 1)),
            arrayOf(Pair(-1, 1), Pair(0, 1), Pair(0, 0), Pair(0, -1))
        )
    ),
    L(
        arrayOf(
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(1, 0), Pair(1, -1)),
            arrayOf(Pair(0, -1), Pair(0, 0), Pair(0, 1), Pair(1, 1)),
            arrayOf(Pair(-1, 1), Pair(-1, 0), Pair(0, 0), Pair(1, 0)),
            arrayOf(Pair(-1, -1), Pair(0, -1), Pair(0, 0), Pair(0, 1))
        )
    ),
    S(
        arrayOf(
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(0, -1), Pair(1, -1)),
            arrayOf(Pair(0, -1), Pair(0, 0), Pair(1, 0), Pair(1, 1)),
            arrayOf(Pair(-1, 1), Pair(0, 1), Pair(0, 0), Pair(1, 0)),
            arrayOf(Pair(-1, -1), Pair(-1, 0), Pair(0, 0), Pair(0, 1))
        )
    ),
    Z(
        arrayOf(
            arrayOf(Pair(-1, -1), Pair(0, -1), Pair(0, 0), Pair(1, 0)),
            arrayOf(Pair(1, -1), Pair(1, 0), Pair(0, 0), Pair(0, 1)),
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(0, 1), Pair(1, 1)),
            arrayOf(Pair(0, -1), Pair(0, 0), Pair(-1, 0), Pair(-1, 1))
        )
    ),
    T(
        arrayOf(
            arrayOf(Pair(-1, 0), Pair(0, -1), Pair(1, 0), Pair(0, 0)),
            arrayOf(Pair(0, -1), Pair(1, 0), Pair(0, 1), Pair(0, 0)),
            arrayOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, 0)),
            arrayOf(Pair(0, 1), Pair(-1, 0), Pair(0, -1), Pair(0, 0))
        )
    );

    fun getCells(rotationState: RotationState): Array<Pair<Int, Int>> {
        val index = when (rotationState) {
            RotationState.North -> 0
            RotationState.East -> 1
            RotationState.South -> 2
            RotationState.West -> 3
        }
        return cellOffsets[index]
    }

    val color
        get() = when (this) {
            I -> CellColor.I.color
            O -> CellColor.O.color
            J -> CellColor.J.color
            L -> CellColor.L.color
            S -> CellColor.S.color
            Z -> CellColor.Z.color
            T -> CellColor.T.color
        }
}

class FallingPiece(val kind: Piece) {
    constructor(piece: FallingPiece) : this(piece.kind) {
        rotationState = piece.rotationState
        x = piece.x
        y = piece.y
    }

    var rotationState = RotationState.North

    private var x = 4
    private var y = 17
    private var yoff = 0.0

    fun render(g: Graphics) {
        g.color = kind.color
        drawPiece(g)
    }

    private fun drawPiece(g: Graphics) {
        for (cell in cells)
            g.fillRect(
                cell.first * CELL_SIZE,
                (cell.second - 20) * CELL_SIZE,
                CELL_SIZE,
                CELL_SIZE
            )
    }

    @Suppress("unused")
    fun drawCenter(g: Graphics) {
        g.fillRect(
            x * CELL_SIZE,
            (y - 20) * CELL_SIZE,
            CELL_SIZE,
            CELL_SIZE
        )
    }

    fun drawGhostPiece(g: Graphics, board: Board) {
        val ghostPiece = FallingPiece(this)
        ghostPiece.drop(40.0, board)
        val col = when (kind) {
            Piece.I -> CellColor.I.color
            Piece.O -> CellColor.O.color
            Piece.J -> CellColor.J.color
            Piece.L -> CellColor.L.color
            Piece.S -> CellColor.S.color
            Piece.Z -> CellColor.Z.color
            Piece.T -> CellColor.T.color
        }

        g.color = Color(col.red, col.green, col.blue, GHOST_PIECE_OPACITY)

        ghostPiece.drawPiece(g)
    }

    fun update(gravity: Double, board: Board) {
        drop(gravity, board)
    }

    fun move(xoff: Int, yoff: Int, board: Board) {
        x += xoff
        y += yoff
        if (board.obstructed(this)) {
            x -= xoff
            y -= yoff
        }
    }

    fun isTouchingGround(board: Board): Boolean {
        y++
        val touchingGround = board.obstructed(this)
        y--
        return touchingGround
    }

    fun drop(gravity: Double, board: Board) {
        yoff += gravity
        while (yoff >= 1.0) {
            move(0, 1, board)
            yoff -= 1.0
        }
    }

    val cells: Array<Pair<Int, Int>>
        get() {
            return Array(4) {
                Pair(
                    kind.getCells(rotationState)[it].first + x,
                    kind.getCells(rotationState)[it].second + y
                )
            }
        }

    fun cw(board: Board) {
        rotate(rotationState, rotationState.cw, board)
    }

    fun ccw(board: Board) {
        rotate(rotationState, rotationState.ccw, board)
    }

    fun r180(board: Board) {
        rotate(rotationState, rotationState.r180, board)
    }

    private fun rotate(currentState: RotationState, desiredState: RotationState, board: Board) {
        val currentX = x
        val currentY = y

        val currentI = when (currentState) {
            RotationState.North -> 0
            RotationState.East -> 1
            RotationState.South -> 2
            RotationState.West -> 3
        }
        val desiredI = when (desiredState) {
            RotationState.North -> 0
            RotationState.East -> 1
            RotationState.South -> 2
            RotationState.West -> 3
        }
        val O_OFFSET_DATA = arrayOf(
            arrayOf(Pair(0, 0)),
            arrayOf(Pair(0, +1)),
            arrayOf(Pair(-1, +1)),
            arrayOf(Pair(-1, 0))
        )
        val I_OFFSET_DATA = arrayOf(
            arrayOf(Pair(0, 0), Pair(-1, 0), Pair(+2, 0), Pair(-1, 0), Pair(+2, 0)),
            arrayOf(Pair(-1, 0), Pair(0, 0), Pair(0, 0), Pair(0, -1), Pair(0, +2)),
            arrayOf(Pair(-1, -1), Pair(+1, -1), Pair(-2, -1), Pair(+1, 0), Pair(-2, 0)),
            arrayOf(Pair(0, -1), Pair(0, -1), Pair(0, -1), Pair(0, +1), Pair(0, -2))
        )
        val OFFSET_DATA = arrayOf(
            arrayOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0)),
            arrayOf(Pair(0, 0), Pair(+1, 0), Pair(+1, +1), Pair(0, -2), Pair(+1, -2)),
            arrayOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0)),
            arrayOf(Pair(0, 0), Pair(-1, 0), Pair(-1, +1), Pair(0, -2), Pair(-1, -2))
        )

        val data = when (kind) {
            Piece.I -> I_OFFSET_DATA
            Piece.O -> O_OFFSET_DATA
            else -> OFFSET_DATA
        }
        rotationState = desiredState
        for (i in 0..4) {
            val xoff = data[currentI][i].first - data[desiredI][i].first
            val yoff = data[currentI][i].second - data[desiredI][i].second

            x += xoff
            y += yoff
            if (board.obstructed(this)) {
                x -= xoff
                y -= yoff
            } else {
                return
            }
        }

        x = currentX
        y = currentY
        rotationState = currentState
    }
}

enum class RotationState {
    North, East, South, West;

    val cw: RotationState
        get() {
            return when (this) {
                North -> East
                East -> South
                South -> West
                West -> North
            }
        }

    val ccw: RotationState
        get() {
            return when (this) {
                North -> West
                West -> South
                South -> East
                East -> North
            }
        }

    val r180: RotationState
        get() {
            return when (this) {
                North -> South
                West -> East
                South -> North
                East -> West
            }
        }
}