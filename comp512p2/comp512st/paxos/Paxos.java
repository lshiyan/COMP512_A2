package comp512st.paxos;

// Access to the GCL layer
import comp512.gcl.*;

import comp512.utils.*;

// Any other imports that you may need.
import java.io.*;
import java.util.logging.*;
import java.net.UnknownHostException;
import java.lang.Math.*;

// ANY OTHER classes, etc., that you add must be private to this package and not visible to the application layer.

// extend / implement whatever interface, etc. as required.
// NO OTHER public members / methods allowed. broadcastTOMsg, acceptTOMsg, and shutdownPaxos must be the only visible methods to the application layer.
//		You should also not change the signature of these methods (arguments and return value) other aspects maybe changed with reasonable design needs.
public class Paxos
{
	GCL m_gcl;
	FailCheck m_failCheck;
	public int m_moveNum;
	public int
	public final int m_numProcesses;
	public final double m_majorityNum;

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
		this.m_numProcesses = p_allGroupProcesses.length + 1;
		this.m_majorityNum = Math.ceil(m_numProcesses / 2);
	}

	// This is what the application layer is going to call to send a message/value, such as the player and the move
	public void broadcastTOMsg(Object val)
	{
		// This is just a place holder.
		// Extend this to build whatever Paxos logic you need to make sure the messaging system is total order.
		// Here you will have to ensure that the CALL BLOCKS, and is returned ONLY when a majority (and immediately upon majority) of processes have accepted the value.
		gcl.broadcastMsg(val);
	}

	// This is what the application layer is calling to figure out what is the next message in the total order.
	// Messages delivered in ALL the processes in the group should deliver this in the same order.
	public Object acceptTOMsg() throws InterruptedException
	{
		// This is just a place holder.
		GCMessage gcmsg = gcl.readGCMessage();
		return gcmsg.val;
	}

	// Add any of your own shutdown code into this method.
	public void shutdownPaxos()
	{
		gcl.shutdownGCL();
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
}

