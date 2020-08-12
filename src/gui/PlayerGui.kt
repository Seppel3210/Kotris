package gui

import Game
import java.awt.BorderLayout
import javax.swing.JPanel

class PlayerGui(game: Game) : JPanel(BorderLayout()) {
    init {
        add(game)
        add(NextQueue(game.board), BorderLayout.EAST)
        add(HoldPiece(game.board), BorderLayout.WEST)
    }
}