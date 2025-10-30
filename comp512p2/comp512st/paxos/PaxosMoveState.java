package comp512st.paxos;

import java.util.ArrayList;
import java.util.List;

class PaxosMoveState {

    //Acceptor state.
    private float m_maxBallotSeen;
    private float m_acceptedBallot;
    private PaxosMove m_acceptedMove;
    private PaxosMove m_decidedMove;
    private int m_acceptedPlayer;

    //Proposer state
    private float m_proposedBallot;
    private float m_maxAcceptedBallot;
    private List<PromiseMessage> m_promiseMessageList = new ArrayList<>();
    private List<AckMessage> m_acceptAckMessageList = new ArrayList<>();

    private boolean m_refuseMessageReceived;
    private boolean m_rejectMessageReceived;

    PaxosMoveState(){
        m_maxBallotSeen = -1;
        m_acceptedBallot = -1;
        m_acceptedMove = null;
        m_decidedMove = null;
        m_acceptedPlayer = -1;

        m_proposedBallot = -1;
        m_maxAcceptedBallot = -1;
        m_refuseMessageReceived = false;
        m_rejectMessageReceived = false;
    }

    synchronized float getMaxBallotSeen() {
        return m_maxBallotSeen;
    }

    synchronized float getAcceptedBallot() {
        return m_acceptedBallot;
    }

    synchronized PaxosMove getAcceptedMove() {
        return m_acceptedMove;
    }

    synchronized PaxosMove getDecidedMove() {
        return m_decidedMove;
    }

    synchronized float getProposedBallot() {
        return m_proposedBallot;
    }

    synchronized float getMaxAcceptedBallot() {
        return m_maxAcceptedBallot;
    }

    synchronized List<PromiseMessage> getPromiseMessageList() {
        return m_promiseMessageList;
    }

    synchronized List<AckMessage> getAcceptAckMessageList() {
        return m_acceptAckMessageList;
    }

    synchronized boolean getRefuseMessageReceived(){
        return m_refuseMessageReceived;
    }

    synchronized boolean getRejectMessageReceived(){
        return m_rejectMessageReceived;
    }

    synchronized int getLastAcceptedPlayer(){
        return m_acceptedPlayer;
    }

    synchronized void setMaxBallotSeen(float maxBallotSeen) {
        this.m_maxBallotSeen = maxBallotSeen;
    }

    synchronized void setAcceptedBallot(float acceptedBallot) {
        this.m_acceptedBallot = acceptedBallot;
    }

    synchronized void setAcceptedMove(PaxosMove acceptedMove) {
        this.m_acceptedMove = acceptedMove;
    }

    synchronized void setDecidedMove(PaxosMove decidedMove) {
        this.m_decidedMove = decidedMove;
    }

    synchronized void setProposedBallot(float proposedBallot) {
        this.m_proposedBallot = proposedBallot;
    }

    synchronized void setMaxAcceptedBallot(float maxAcceptedBallot) {
        this.m_maxAcceptedBallot = maxAcceptedBallot;
    }

    synchronized void setRefuseMessageReceived(boolean refuseMessageReceived){
        this.m_refuseMessageReceived = refuseMessageReceived;
    }

    synchronized void setRejectMessageReceived(boolean rejectMessageReceived){
        this.m_rejectMessageReceived = rejectMessageReceived;
    }

    synchronized void setLastAcceptedPlayer(int lastAcceptedPlayer){
        this.m_acceptedPlayer = lastAcceptedPlayer;
    }

    synchronized void reset() {
        this.m_refuseMessageReceived = false;
        this.m_rejectMessageReceived = false;
        this.m_promiseMessageList.clear();
        this.m_acceptAckMessageList.clear();
    }
}