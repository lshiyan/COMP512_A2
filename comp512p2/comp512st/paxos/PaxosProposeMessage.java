package comp512st.paxos;

import java.io.Serializable;

public class PaxosProposeMessage implements Serializable{
    private float m_ballotID;
    private int m_moveNum;

    public PaxosProposeMessage(float m_ballotID, int p_moveNum){
        m_ballotID = m_ballotID;
        m_moveNum = p_moveNum;
    }
}
