package comp512st.paxos;

enum PaxosMove {
    LEFT, RIGHT, UP, DOWN, SHUTDOWN;

    @Override
    public String toString() {
        return name();
    }

    public static PaxosMove fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        switch (value.toLowerCase()) {
            case "left":
                return LEFT;
            case "right":
                return RIGHT;
            case "up":
                return UP;
            case "down":
                return DOWN;
            case "shutdown":
                return SHUTDOWN;
            default:
                throw new IllegalArgumentException("Unknown PaxosMove: " + value);
        }
    }
}
