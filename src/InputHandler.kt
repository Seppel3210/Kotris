import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

const val LEFT = KeyEvent.VK_LEFT
const val RIGHT = KeyEvent.VK_RIGHT
const val SOFT_DROP = KeyEvent.VK_DOWN
const val HARD_DROP = KeyEvent.VK_SPACE
const val HOLD = KeyEvent.VK_UP
const val CW = KeyEvent.VK_E
const val CCW = KeyEvent.VK_W
const val R180 = KeyEvent.VK_Q

const val RESET = KeyEvent.VK_F4

class InputHandler : KeyAdapter() {
    var cw = false
        private set
    var ccw = false
        private set
    var r180 = false
        private set
    var left = false
        private set
    var right = false
        private set
    var softDrop = false
        private set
    var hardDrop = false
        private set
    var hold = false
        private set

    var reset = false

    override fun keyPressed(e: KeyEvent) {
        updateKeyState(e.keyCode, true)
    }

    override fun keyReleased(e: KeyEvent) {
        updateKeyState(e.keyCode, false)
    }

    private fun updateKeyState(keyCode: Int, pressed: Boolean) {
        when (keyCode) {
            LEFT -> left = pressed
            RIGHT -> right = pressed
            CW -> cw = pressed
            CCW -> ccw = pressed
            SOFT_DROP -> softDrop = pressed
            HARD_DROP -> hardDrop = pressed
            HOLD -> hold = pressed
            R180 -> r180 = pressed

            RESET -> reset = pressed
        }
    }
}