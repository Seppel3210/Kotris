import tetris.Board
import tetris.FallingPiece
import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferStrategy

class Game : Canvas() {
    private val inputHandler = InputHandler()
    private lateinit var bs: BufferStrategy

    private var running = true

    private var board = Board(NEXT_QUEUE_SIZE)
    private var fallingPiece = FallingPiece(board.nextPiece)
    private var canHold = true
    private var lockTimer = 0
    private var forcedLockTimer = 0

    init {
        val size = Dimension(CELL_SIZE * 10, CELL_SIZE * 20)
        addKeyListener(inputHandler)
        preferredSize = size
        maximumSize = size
        minimumSize = size
    }

    fun run() {
        requestFocus()
        //Setup
        var lastTime = System.nanoTime()
        val amountOfTicks = FPS
        val ns = 1000000000 / amountOfTicks
        var delta = 0.0
        var timer = System.currentTimeMillis()
        var updates = 0
        var frames = 0

        createBufferStrategy(NUM_BUFFERS)
        bs = bufferStrategy

        //Game Loop
        while (running) {
            val now = System.nanoTime()
            delta += (now - lastTime) / ns
            lastTime = now
            while (delta >= 1) {
                tick()
                updates++
                delta--
            }
            render()
            frames++
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000
                frames = 0
                updates = 0
            }
        }
    }

    private fun reset() {
        board = Board(NEXT_QUEUE_SIZE)
    }

    private fun tick() {
        if (inputHandler.reset) {
            reset()
        }

        val gravity = GRAVITY * if (inputHandler.softDrop) SOFT_DROP_MULTIPLIER else 1
        fallingPiece.update(gravity, board)

        forcedLockTimer++
        if (fallingPiece.isTouchingGround(board)) {
            lockTimer++
            if (lockTimer > LOCK_DELAY || forcedLockTimer > FORCED_LOCK_DELAY) {
                lockPiece()
            }
        } else {
            lockTimer = 0
        }

        handleInput()
    }

    private fun lockPiece() {
        forcedLockTimer = 0
        lockTimer = 0

        fallingPiece.drop(40.0, board)
        board.lock(fallingPiece)
        fallingPiece = FallingPiece(board.nextPiece)
        canHold = true

        //update nextQueue visually
        parent.repaint()
    }

    private var prevLeft = false
    private var prevRight = false
    private var prevCw = false
    private var prevCcw = false
    private var prevHardDrop = false
    private var prevHold = false
    private var prevR180 = false

    private var das = 0

    private fun handleInput() {
        if (inputHandler.hardDrop && !prevHardDrop) {
            lockPiece()
        }

        if (canHold && inputHandler.hold && !prevHold) {
            fallingPiece = FallingPiece(board.hold(fallingPiece.kind))
            canHold = false

            //update hold and nextQueue visually
            parent.repaint()
        }

        if (inputHandler.left || inputHandler.right) {
            das++
        }

        if (!prevLeft && inputHandler.left) {
            das = 0
        }
        if (!prevRight && inputHandler.right) {
            das = 0
        }

        if (inputHandler.right && (das == 0 || das > DAS)) {
            fallingPiece.move(1, 0, board)
            lockTimer = 0
        }
        if (inputHandler.left && (das == 0 || das > DAS)) {
            fallingPiece.move(-1, 0, board)
            lockTimer = 0
        }

        if (inputHandler.cw && !prevCw) {
            fallingPiece.cw(board)
            lockTimer = 0
        }

        if (inputHandler.ccw && !prevCcw) {
            fallingPiece.ccw(board)
            lockTimer = 0
        }

        if (inputHandler.r180 && !prevR180) {
            fallingPiece.r180(board)
            lockTimer = 0
        }

        prevLeft = inputHandler.left
        prevRight = inputHandler.right
        prevCw = inputHandler.cw
        prevCcw = inputHandler.ccw
        prevHardDrop = inputHandler.hardDrop
        prevHold = inputHandler.hold
        prevR180 = inputHandler.r180
    }

    private fun render() {
        val g = bs.drawGraphics
        g.color = Color.black
        g.fillRect(0, 0, CELL_SIZE * 10, CELL_SIZE * 20)
        drawGrid(g)
        board.render(g)
        fallingPiece.render(g)
        fallingPiece.drawGhostPiece(g, board)
        bs.show()
    }

    private fun drawGrid(g: Graphics) {
        g.color = Color.white
        for (y in 1..19) {
            g.drawLine(0, y * CELL_SIZE, 10 * CELL_SIZE, y * CELL_SIZE)
        }

        for (x in 1..9) {
            g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, 20 * CELL_SIZE)
        }
    }
}