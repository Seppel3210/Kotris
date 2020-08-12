import gui.PlayerGui
import gui.Window

//config constants
const val FPS = 60.0
const val NUM_BUFFERS = 2

const val CELL_SIZE = 40
const val MINI_PIECE_CELL_SIZE = CELL_SIZE * 2 / 3
const val GHOST_PIECE_OPACITY = 150

const val GRAVITY = 0.05
const val SOFT_DROP_MULTIPLIER = 40
const val NEXT_QUEUE_SIZE = 20

// Delay is in Frames
const val DAS = 5
const val LOCK_DELAY = 20
const val FORCED_LOCK_DELAY = 20 * 60

//constants for input handling in InputHandler.kt

fun main() {

    val game = Game()
    Window(PlayerGui(game))
    do {
        game.reset()
        game.run()
    } while (game.inputHandler.reset)
}