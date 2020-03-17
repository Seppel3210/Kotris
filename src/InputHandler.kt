import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class InputHandler : KeyAdapter() {
    var cw = false
    var ccw = false
    var left = false
    var right = false
    var softDrop = false
    var hardDrop = false
    var hold = false
    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_LEFT -> left = true
            KeyEvent.VK_RIGHT -> right = true
            KeyEvent.VK_S -> cw = true
            KeyEvent.VK_A -> ccw = true
            KeyEvent.VK_DOWN -> softDrop = true
            KeyEvent.VK_SPACE -> hardDrop = true
            KeyEvent.VK_UP -> hold = true

        }
    }

    override fun keyReleased(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_LEFT -> left = false
            KeyEvent.VK_RIGHT -> right = false
            KeyEvent.VK_S -> cw = false
            KeyEvent.VK_A -> ccw = false
            KeyEvent.VK_DOWN -> softDrop = false
            KeyEvent.VK_SPACE -> hardDrop = false
            KeyEvent.VK_UP -> hold = false

        }
    }
}