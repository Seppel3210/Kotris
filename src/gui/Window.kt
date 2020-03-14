package gui

import java.awt.Component
import javax.swing.JFrame

class Window(component: Component) : JFrame("Kotris") {
    init {
        add(component)
        pack()
        setLocationRelativeTo(null)
        isVisible = true

        component.requestFocus()
    }
}