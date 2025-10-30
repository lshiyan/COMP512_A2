package comp512st.paxos;

import java.io.Serializable;

abstract class PaxosMessage implements Serializable {
    protected int m_moveNum;
    protected int m_playerNum;

    PaxosMessage(int p_moveNum, int p_playerNum) {
        m_moveNum = p_moveNum;
        m_playerNum = p_playerNum;
    }

    int getMoveNum() {
        return m_moveNum;
    }

    int getPlayerNum() {
        return m_playerNum;
    }
}

class ProposeMessage extends PaxosMessage {
    private float m_ballot;

    ProposeMessage(int p_moveNum, int p_playerNum, float p_ballotID) {
        super(p_moveNum, p_playerNum);
        m_ballot = p_ballotID;
    }

    float getBallotID() {
        return m_ballot;
    }
}

class PromiseMessage extends PaxosMessage {
    private float m_ballot;
    private float m_acceptedBallot;
    private PaxosMove m_acceptedMove;
    private int m_acceptedPlayer;

    PromiseMessage(int p_moveNum, int p_playerNum, float p_ballotID, int p_acceptedPlayer, PaxosMove p_acceptedMove, float p_acceptedBallot) {
        super(p_moveNum, p_playerNum);
        m_ballot = p_ballotID;
        m_acceptedBallot = p_acceptedBallot;
        m_acceptedMove = p_acceptedMove;
        m_acceptedPlayer = p_acceptedPlayer;
    }

    float getBallotID() {
        return m_ballot;
    }

    float getAcceptedBallot() {
        return m_acceptedBallot;
    }

    PaxosMove getAcceptedMove() {
        return m_acceptedMove;
    }

    int getLastAcceptedPlayer(){
        return m_acceptedPlayer;
    }
}

class RefuseMessage extends PaxosMessage {
    private float m_highestBallot;

    RefuseMessage(int p_moveNum, int p_playerNum, float p_highestBallot) {
        super(p_moveNum, p_playerNum);
        m_highestBallot = p_highestBallot;
    }

    float getBallotID() {
        return m_highestBallot;
    }
}

class AcceptMessage extends PaxosMessage {
    private float m_ballot;
    private PaxosMove m_move;

    AcceptMessage(int p_moveNum, int p_playerNum, float p_ballotID, PaxosMove p_move) {
        super(p_moveNum, p_playerNum);
        m_ballot = p_ballotID;
        m_move = p_move;
    }

    PaxosMove getMove() {
        return m_move;
    }

    float getBallotID() {
        return m_ballot;
    }
}

class AckMessage extends PaxosMessage {
    private float m_ballot;

    AckMessage(int p_moveNum, int p_playerNum, float p_ballotID) {
        super(p_moveNum, p_playerNum);
        m_ballot = p_ballotID;
    }

    float getBallotID() {
        return m_ballot;
    }
}

class RejectMessage extends PaxosMessage {
    private float m_highestBallot;

    RejectMessage(int p_moveNum, int p_playerNum, float p_highestBallot) {
        super(p_moveNum, p_playerNum);
        m_highestBallot = p_highestBallot;
    }

    float getBallotID() {
        return m_highestBallot;
    }
}

class ConfirmMessage extends PaxosMessage {
    private PaxosMove m_confirmedMove;

    ConfirmMessage(int p_moveNum, int p_playerNum, PaxosMove p_move) {
        super(p_moveNum, p_playerNum);
        m_confirmedMove = p_move;
    }

    PaxosMove getConfirmedMove() {
        return m_confirmedMove;
    }
}

class ShutdownMessage extends PaxosMessage {

    ShutdownMessage(int p_playerNum){
        super(-1, p_playerNum);
    }
    
}
