package comp512st.paxos;

// Access to the GCL layer
import comp512.gcl.*;

import comp512.utils.*;

// Any other imports that you may need.
import java.io.*;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.*;
import java.net.UnknownHostException;
import java.sql.Time;
import java.lang.Math.*;

// ANY OTHER classes, etc., that you add must be private to this package and not visible to the application layer.

// extend / implement whatever interface, etc. as required.
// NO OTHER public members / methods allowed. broadcastTOMsg, acceptTOMsg, and shutdownPaxos must be the only visible methods to the application layer.
//		You should also not change the signature of these methods (arguments and return value) other aspects maybe changed with reasonable design needs.
public class Paxos
{
	FailCheck m_failCheck;

	private ConcurrentLinkedQueue<PaxosMove> m_moveQueue; //Queue that stores the moves in total order.

	private final int m_numProcesses; //Number of total player processes.
	private final double m_majorityNum; //Number of processed required to meet majority.
	private final long m_maxTimeout = 10000; //10 seconds.
	private final String m_processString; //Process name of our current process.
	private int m_moveNum; //Current move we're proposing for

	//Fields for the proposer code.
	private float m_lastProposedBallotID; //Last ballot ID proposed.

	//Fields for the acceptor code.
	private PaxosMove m_previousAcceptedMove = null;

	GCL m_gcl;

	public Paxos(String p_myProcess, String[] p_allGroupProcesses, Logger p_logger, FailCheck p_failCheck) throws IOException, UnknownHostException, IllegalArgumentException
	{
		// Rember to call the failCheck.checkFailure(..) with appropriate arguments throughout your Paxos code to force fail points if necessary.
		this.m_failCheck = p_failCheck;

		if (!(validateProcessString(p_myProcess))){
			throw new IllegalArgumentException("Invalid process string: " + p_myProcess);
		}

		for (String group_process : p_allGroupProcesses){
			if (!(validateProcessString(group_process))){
				throw new IllegalArgumentException("Invalid process string: " + group_process);
			}
		}

		// Initialize the GCL communication system as well as anything else you need to.
		this.m_gcl = new GCL(p_myProcess, p_allGroupProcesses, null, p_logger);

		this.m_moveNum = 1;
		m_numProcesses = p_allGroupProcesses.length + 1;
		m_majorityNum = Math.ceil(m_numProcesses / 2);
		m_processString = p_myProcess;
		startListenerThread();
	}

	// This is what the application layer is going to call to send a message/value, such as the player and the move
	public void broadcastTOMsg(Object val)
	{
		// This is just a place holder.
		// Extend this to build whatever Paxos logic you need to make sure the messaging system is total order.
		// Here you will have to ensure that the CALL BLOCKS, and is returned ONLY when a majority (and immediately upon majority) of processes have accepted the value.
		//gcl.broadcastMsg(val);
		m_proposer.propose();
	}

	// This is what the application layer is calling to figure out what is the next message in the total order.
	// Messages delivered in ALL the processes in the group should deliver this in the same order.
	public Object acceptTOMsg() throws InterruptedException
	{
		while (m_moveQueue.isEmpty()){
			Thread.sleep(500);
		}

		PaxosMove nextMove = m_moveQueue.poll();
		m_moveNum += 1;
		return nextMove;
	}

	// Add any of your own shutdown code into this method.
	public void shutdownPaxos()
	{
		gcl.shutdownGCL();
	}

	public void startListenerThread(){
		new Thread(() -> {
			try{
				while (true){
					PaxosMessage paxosMessage = (PaxosMessage) m_gcl.readGCMessage().val;

					switch(paxosMessage.getMessageType()){
						case PROPOSE:
							handleProposeMessage(paxosMessage);
						case PROMISE:
							handlePromiseMessage(paxosMessage);
						case ACCEPT:
							handleAcceptMessage(paxosMessage);
						case ACCEPTACK:
							handleAcceptAckMessage(paxosMessage);
						case CONFIRM:
							handleConfirmMessage(paxosMessage);
					}
				}
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}).start();
	}

	public boolean validatePaxosMessage(PaxosMessage)
	public void handleProposeMessage(PaxosMessage p_proposeMessage){
		if (validatePaxosMessage(p_proposeMessage){

		}
	}

	public void handlePromiseMessage(PaxosMessage p_promiseMessage){

	}

	public void handleAcceptMessage(PaxosMessage p_acceptMessage){

	}

	public void handleAcceptAckMessage(PaxosMessage p_acceptAckMessage){

	}

	public void handleConfirmMessage(PaxosMessage p_confirmMessage){

	}

	private boolean validateProcessString(String p_processString) throws NumberFormatException{
		if (p_processString == null || p_processString.isEmpty()) {
			return false;
		}

		String[] parts = p_processString.split(":");
		if (parts.length != 2) {
			return false;
		}

		String hostname = parts[0].trim();
		String portStr = parts[1].trim();

		if (hostname.isEmpty()) {
			return false;
		}

		try {
			int port = Integer.parseInt(portStr);
			if (port < 1 || port > 65535) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	//Validates each paxos message. Does not check anything about ballotID.
	private boolean validatePaxosMessage(PaxosMessage p_paxosMessage){
		PaxosMessageType type = p_paxosMessage.getMessageType();

		Vector<String> args = p_paxosMessage.getArgs();

		if (!(p_paxosMessage.validateArgLength())){
			return false;
		}

		String processString = args.get(0);
		int moveNum = Integer.valueOf(args.get(1));

		if (!(processString.equals(m_processString)) || moveNum != m_moveNum){
			return false;
		}

		switch(type){

			case PROMISE:
				float ballotID = Float.valueOf(args.get(2));

				if (!ballotID == m_lastProposedBallotID){

				}

		}

		return true;
	}
	//Generates a random ballotID based on the current moveNum from a U(0,1) distribution, rounded to 2 decimal places. I.e. if m_moveNum = 2, then this will output 2.31, 2.93, etc.
	private float generateBallotID(){
		Random rand = new Random();

		float fractional = rand.nextFloat();

		return m_moveNum + fractional;
	}

	// Generates a propose message with a random ballotID.
	private PaxosMessage generateProposeMessage(float candidateID){
		Vector<String> args = new Vector<>();
		args.add(m_processString);
		args.add(String.valueOf(m_moveNum));
		args.add(String.valueOf(candidateID));

		PaxosMessage candidateProposeMessage = new PaxosMessage(PaxosMessageType.PROPOSE, args);

		return candidateProposeMessage;
	}

	/*class PaxosProposer {

		

		public PaxosProposer(int p_numProcesses, double p_majorityNum, GCL p_gcl, String p_processString){
			m_numProcesses = p_numProcesses;
			m_majorityNum = p_majorityNum;
			m_moveNum = 1;
			m_gcl = p_gcl;
			m_processString = p_processString;
		}

		//Generates a random ballotID based on the current moveNum from a U(0,1) distribution, rounded to 2 decimal places. I.e. if m_moveNum = 2, then this will output 2.31, 2.93, etc.
		private float generateBallotID(){
			Random rand = new Random();

			float fractional = rand.nextFloat();

			return m_moveNum + fractional;
		}

		// Generates a propose message with a random ballotID.
		private PaxosMessage generateProposeMessage(float candidateID){
			Vector<String> args = new Vector<>();
			args.add(m_processString);
			args.add(String.valueOf(m_moveNum));
			args.add(String.valueOf(candidateID));

			PaxosMessage candidateProposeMessage = new PaxosMessage(PaxosMessageType.PROPOSE, args);

			return candidateProposeMessage;
		}

		//Validates that a promise message is indeed for the specific move number and ballotID that was sent.
		private boolean validatePromiseMessage(PaxosMessage p_promiseMessage, float p_candidateID){
			PaxosMessageType type = p_promiseMessage.getMessageType();

			if (!(type.equals(PaxosMessageType.PROMISE))){
				return false;
			}

			Vector<String> args = p_promiseMessage.getArgs();

			if (!(p_promiseMessage.validateArgLength())){
				return false;
			}

			String processString = args.get(0);
			int moveNum = Integer.valueOf(args.get(1));
			float promisedID = Float.valueOf(args.get(1));

			if (!(processString.equals(m_processString)) || moveNum != m_moveNum || promisedID != p_candidateID){
				return false;
			}

			return true;
		}

		//Broadcasts proposal message, returns true if proposal succeeded.
		public boolean propose(){
			float candidateID = generateBallotID();
			PaxosMessage proposeMessage = generateProposeMessage(candidateID);
			m_lastProposedBallotID = candidateID;

			m_gcl.broadcastMsg(proposeMessage);
			boolean proposalSucceeded = listenForPromises();

			return proposalSucceeded;
		}

		public boolean listenForPromises(){

			int counter = 0;
			boolean proposalSucceeded = false;
			long startTime = System.currentTimeMillis();

			while (true){

				if (System.currentTimeMillis() - startTime > m_maxTimeout) {
					break;
				}

				PaxosMessage promiseMessage = (PaxosMessage) m_gcl.readGCMessage().val;

				if (validatePromiseMessage(promiseMessage, m_lastProposedBallotID)){
					counter += 1;
					if (counter == m_majorityNum){
						proposalSucceeded = true;
					}
				}
			}

			return proposalSucceeded;
		}
	}

	class PaxosAcceptor {
 
		private float m_maxBallotID;
		private float m_maxAcceptID;
		private PaxosMove m_acceptedMove;
		private PaxosMove m_confirmedMove;
		private int m_moveNum;
		private GCL m_gcl;

		public PaxosAcceptor(GCL p_gcl){
			m_maxBallotID = -1;
			m_maxAcceptID = -1;
			m_acceptedMove = null;
			m_gcl = p_gcl;
			m_moveNum = 1;
		}

		//Validates whether a recieved message is a valid Paxos message that corresponds to this move number. DOES NOT check if the ballotID is valid.
		private boolean validatePaxosMessage(PaxosMessage p_paxosMessage){
			PaxosMessageType type = p_paxosMessage.getMessageType();

			Vector<String> args = p_paxosMessage.getArgs();

			if (!(p_paxosMessage.validateArgLength())){
				return false;
			}

			Vector<String> messageArgs = p_paxosMessage.getArgs();
			int moveNum = Integer.valueOf(messageArgs.get(1));

			if (moveNum != m_moveNum){
				return false;
			}

			return true;
		}

		//Starts a thread that continually listens for messages and handles accordingly.
		public void startListenerThread(){
			new Thread(()->{

				while (true){
					PaxosMessage paxosMessage = m_gcl.readGCMessage().val;

					if (validatePaxosMessage(paxosMessage)){

						Vector<String> args = paxosMessage.getArgs();
						String proposerProcess = args.get(0);
						float candidateID = Float.valueOf(args.get(2));

						switch(paxosMessage.getMessageType()){

							case PaxosMessageType.PROMISE:

								if (!(candidateID > m_maxBallotID)){
									Vector<String> refuseMessageArgs = new Vector<>(args);
									PaxosMessage refuseMessage = new PaxosMessage(PaxosMessageType.REFUSE, refuseMessageArgs);
									m_gcl.sendMsg(refuseMessage, proposerProcess);
								}
								else{
									Vector<String> promiseMessageArgs = new Vector<>(args);
									if (!(m_acceptedMove.equals(null))){
										promiseMessageArgs.add(String.valueOf(m_maxAcceptID));
										promiseMessageArgs.add(m_acceptedMove.toString());
									}
									PaxosMessage promiseMessage = new PaxosMessage(PaxosMessageType.PROMISE, promiseMessageArgs);
									m_gcl.sendMsg(promiseMessage, proposerProcess);
									m_maxBallotID = candidateID;
								}
						
							case PaxosMessageType.ACCEPT:
								
								if (candidateID == m_maxBallotID){
									m_acceptedMove = PaxosMove.fromString(args.get(3));
									m_maxAcceptID = candidateID;

									Vector<String> acceptAckMessageArgs = new Vector<>(args);
									PaxosMessage acceptAckMessage = new PaxosMessage(PaxosMessageType.ACCEPTACK, acceptAckMessageArgs);
									m_gcl.sendMsg(acceptAckMessage, proposerProcess);
								}
								else{
									Vector<String> acceptAckMessageArgs = new Vector<>(args);
									PaxosMessage acceptNackMessage = new PaxosMessage(PaxosMessageType.ACCEPTNACK, acceptAckMessageArgs);
									m_gcl.sendMsg(acceptNackMessage, proposerProcess);
								}

							case PaxosMessageType.CONFIRM:

								if (candidateID == m_maxAcceptID){
									m_confirmedMove = PaxosMove.fromString(args.get(3));
									m_maxAcceptID = candidateID;

									Vector<String> acceptAckMessageArgs = new Vector<>(args);
									PaxosMessage acceptAckMessage = new PaxosMessage(PaxosMessageType.ACCEPTACK, acceptAckMessageArgs);
									m_gcl.sendMsg(proposerProcess);
								}
								else{
									Vector<String> acceptAckMessageArgs = new Vector<>(args);
									PaxosMessage acceptNackMessage = new PaxosMessage(PaxosMessageType.ACCEPTNACK, acceptAckMessageArgs);
									m_gcl.sendMsg(proposerProcess);
								}
						}
					}
				}
			}).start();
		}
	}*/
}

