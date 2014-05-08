package edu.uwec.cs.cs355.group17.aPrioriFinal.server;

import org.restlet.resource.ServerResource;

import edu.uwec.cs.cs355.group17.aPrioriFinal.client.Parser;
import edu.uwec.cs.cs355.group17.aPrioriFinal.client.Request;
import edu.uwec.cs.cs355.group17.aPrioriFinal.client.Timer;

public class RequestServerResource extends ServerResource implements RequestResource{

	/***************
	 * VARIABLES *
	 ***************/
	private static Request request = new Request();
	
	/******************
	 * CONSTRUCTORS *
	 ******************/
	public RequestServerResource () {
		//default constructor
	}

	/*************
	 * METHODS *
	 *************/
	
	@Override
	public Request retrieve() {
		// starting timer
		Timer timer = new Timer();
		timer.startTimer();
		// generating rules
		APrioriAlgorithm generator = new APrioriAlgorithm(RequestServerResource.request.getMinSupportLevel(), RequestServerResource.request.getMinConfidenceLevel(), RequestServerResource.request.getFilepath());
		// stopping timer
		timer.stopTimer();
		// creating DAO and uploading to database
		Parser parser = new Parser(RequestServerResource.request.getFilepath());
		DataAccessObject dao = new DataAccessObject();
		dao.uploadTransaction(parser.getStore(), parser.getStartDate(), parser.getEndDate(), generator.transactions);
		dao.uploadAssociations(parser.getStartDate(), generator.associationRules);
		// Return the association rules
		return new Request(generator.getAssociationRules(), generator.getBadSets(), timer.getTotal());
	} // end of retrieve
	
	@Override
	public void store (Request request) {
		RequestServerResource.request = new Request(request);
	} // end of store
	
} // end of RequestServerResource
