package comp512st.paxos;

enum PaxosMessageType {
    PROPOSE, REFUSE, PROMISE, ACCEPT, ACCEPTACK, ACCEPTNACK, CONFIRM;
}
