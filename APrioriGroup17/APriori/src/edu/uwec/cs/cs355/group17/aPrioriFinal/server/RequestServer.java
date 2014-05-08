package edu.uwec.cs.cs355.group17.aPrioriFinal.server;

import org.restlet.Server;
import org.restlet.data.Protocol;

public class RequestServer {

	public static void main (String [] args) throws Exception {
		Server contactServer = new Server(Protocol.HTTP, 8111, RequestServerResource.class);
		contactServer.start();
	}
	
}