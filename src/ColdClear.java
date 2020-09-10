import tetris.Piece;
import tetris.TSpinStatus;

public final class ColdClear {
    private final long ccPtr;

    private native long ccLaunchAsync(long options_ptr, long weights_ptr);

    private native long ccLaunchWithBoardAsync(long options_ptr, long weights_ptr, long field_ptr, int bag_remain, int hold, boolean b2b, int combo);

    private native void ccDestroyAsync(long bot_ptr);

    private native void ccResetAsync(long bot_ptr, long field_ptr, boolean b2b, int combo);

    private native void ccAddNextPieceAsync(long bot_ptr, int piece);

    private native void ccRequestNextMove(long bot_ptr, int incoming);

    private native int ccPollNextMove(long bot_ptr);

    private native int ccBlockNextMove(long bot_ptr);

    private native long ccDefaultOptions();

    private native long ccDefaultWeights();

    private native long ccFastWeights();

    private int pieceToCCPiece(Piece piece) {
        return switch (piece) {
            case I -> 1;
            case O -> 2;
            case T -> 3;
            case L -> 4;
            case J -> 5;
            case S -> 6;
            case Z -> 7;
        };
    }

    private Piece ccPieceToPiece(int piece) {
        return switch (piece) {
            case 0 -> Piece.I;
            case 1 -> Piece.O;
            case 2 -> Piece.T;
            case 3 -> Piece.L;
            case 4 -> Piece.J;
            case 5 -> Piece.S;
            case 6 -> Piece.Z;
            default -> throw new IllegalStateException("Unexpected value: " + piece);
        };
    }

    private int tSpinStatusToCCTspinStatus(TSpinStatus status) {
        return switch (status) {
            case None -> 1;
            case Mini -> 2;
            case Full -> 3;
        };
    }

    private TSpinStatus ccTspinStatusToTSpinStatus(int status) {
        return switch (status) {
            case 0 -> TSpinStatus.None;
            case 1 -> TSpinStatus.Mini;
            case 2 -> TSpinStatus.Full;
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }

    protected final void destroy() {
        if (this.ccPtr != 0L) {
            this.ccDestroyAsync(this.ccPtr);
        }
    }

    public ColdClear() {
        long default_options_ptr = this.ccDefaultOptions();
        long default_weights_ptr = this.ccDefaultWeights();
        this.ccPtr = this.ccLaunchAsync(default_options_ptr, default_weights_ptr);
    }

    static {
        System.loadLibrary("cold_clear_java");
    }
}