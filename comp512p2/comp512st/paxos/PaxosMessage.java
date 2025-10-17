package comp512st.paxos;

import java.io.Serializable;
import java.util.Vector;

class PaxosMessage implements Serializable{

    private PaxosMessageType m_messageType;
    private Vector<String> m_args;

    public PaxosMessage(PaxosMessageType p_messageType, Vector<String> p_messageArgs) throws IllegalArgumentException {
        m_messageType = p_messageType;
        m_args = p_messageArgs;

        if (!validateArgLength()){
            throw new IllegalArgumentException("Incorrect number of arguments for given message type.");
        }

    }

    public PaxosMessageType getMessageType(){
        return m_messageType;
    }

    public Vector<String> getArgs(){
        return m_args;
    }

     public boolean validateArgLength(){
        switch (m_messageType){
            case PaxosMessageType.PROPOSE: //[Proposer process, moveNum, ballotID]
                return m_args.size() == 3;
            case PaxosMessageType.REFUSE: //[Proposer process, moveNum, ballotID]
                return m_args.size() == 3;
            case PaxosMessageType.PROMISE: //[Proposer process, moveNum, ballotID, <lastAcceptedBallotID>, <lastAcceptedMove>]
                return m_args.size() == 3 || m_args.size() == 5; 
            case PaxosMessageType.ACCEPT: //[Proposer process, moveNum, ballotID, moveToAccept]
                return m_args.size() == 4; 
            case PaxosMessageType.ACCEPTACK: //[Proposer process, moveNum, ballotID, moveToAccept]
                return m_args.size() == 4;
            case PaxosMessageType.ACCEPTNACK: //[Proposer process, moveNum, ballotID, moveToAccept]
                return m_args.size() == 4;
            case PaxosMessageType.CONFIRM: //[Proposer process, moveNum, ballotID]
                return m_args.size() == 3;
            default:
                return false;
        }
    }
}
