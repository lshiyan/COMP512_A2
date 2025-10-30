package comp512st.paxos;

enum PaxosMove {
    LEFT, RIGHT, UP, DOWN, SHUTDOWN;

    static PaxosMove fromChar(Character value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        switch (value) {
            case 'L':
                return LEFT;
            case 'R':
                return RIGHT;
            case 'U':
                return UP;
            case 'D':
                return DOWN;
            case 'E':
                return SHUTDOWN;
            default:
                throw new IllegalArgumentException("Unknown PaxosMove: " + value);
        }
    }

    char getChar() {
        switch (this) {
            case LEFT:
                return 'L';
            case RIGHT:
                return 'R';
            case UP:
                return 'U';
            case DOWN:
                return 'D';
            case SHUTDOWN:
                return 'E';
            default:
                throw new IllegalStateException("Unexpected PaxosMove: " + this);
        }
    }
}
