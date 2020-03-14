import gui.Window


const val FPS = 60.0
const val CELL_SIZE = 40
fun main() {
    val game = Game()
    Window(game)
    game.run()
}