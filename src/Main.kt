import gui.NextQueue
import gui.Window
import java.awt.BorderLayout
import javax.swing.JPanel


const val FPS = 60.0
const val NUM_BUFFERS = 2

const val CELL_SIZE = 40
const val GHOST_PIECE_OPACITY = 150

const val GRAVITY = 0.05
const val SOFT_DROP_MULTIPLIER = 40

// Delay is in Frames
const val DAS = 5
const val NEXT_QUEUE_SIZE = 5

fun main() {
    val game = Game()
    val nextQueue = NextQueue(game.board.nextQueue)
    val windowContent = JPanel(BorderLayout())
    windowContent.add(game)
    windowContent.add(nextQueue, BorderLayout.EAST)
    Window(windowContent)
    game.run()
}