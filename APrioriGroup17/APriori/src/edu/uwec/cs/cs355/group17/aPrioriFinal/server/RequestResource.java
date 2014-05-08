package edu.uwec.cs.cs355.group17.aPrioriFinal.server;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

import edu.uwec.cs.cs355.group17.aPrioriFinal.client.Request;

public interface RequestResource {

	@Get
	public Request retrieve();
	
	@Put
	public void store (Request request);
}
