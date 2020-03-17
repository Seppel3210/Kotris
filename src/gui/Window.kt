package gui

import java.awt.Component
import javax.swing.JFrame
import javax.swing.WindowConstants

class Window(component: Component) : JFrame("Kotris") {
    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        add(component)
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}